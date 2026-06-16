package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.inspecoesibmec.model.ResumoInspecao
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalheHistoricoActivity : AppCompatActivity() {

    private lateinit var txtTitulo: TextView
    private lateinit var txtContexto: TextView
    private lateinit var txtTaxaFaltas: TextView
    private lateinit var txtMetricas: TextView
    private lateinit var btnCompartilhar: Button
    private lateinit var btnVoltarHistorico: AppCompatButton

    private var inspecaoId = 0
    private var dataInspecao = ""
    private var diaSemana = ""
    private var turno = ""
    private var nomeUsuario = ""

    private var resumoAtual: ResumoInspecao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_historico)

        txtTitulo = findViewById(R.id.txtTituloHistoricoResumo)
        txtContexto = findViewById(R.id.txtHistoricoContexto)
        txtTaxaFaltas = findViewById(R.id.txtHistoricoTaxaFaltas)
        txtMetricas = findViewById(R.id.txtHistoricoMetricas)
        btnCompartilhar = findViewById(R.id.btnCompartilharHistorico)
        btnVoltarHistorico = findViewById(R.id.btnVoltarHistorico)

        inspecaoId = intent.getIntExtra("inspecaoId", 0)
        dataInspecao = intent.getStringExtra("dataInspecao") ?: ""
        diaSemana = intent.getStringExtra("diaSemana") ?: ""
        turno = intent.getStringExtra("turno") ?: ""
        nomeUsuario = intent.getStringExtra("nomeUsuario") ?: ""

        txtTitulo.text = "Resumo da Inspeção #$inspecaoId"

        carregarResumo()

        btnCompartilhar.setOnClickListener {
            compartilharRelatorio()
        }

        btnVoltarHistorico.setOnClickListener {
            finish()
        }
    }

    private fun carregarResumo() {
        ApiClient.apiService.buscarResumo(inspecaoId)
            .enqueue(object : Callback<ResumoInspecao> {
                override fun onResponse(
                    call: Call<ResumoInspecao>,
                    response: Response<ResumoInspecao>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val resumo = response.body()!!
                        resumoAtual = resumo

                        txtContexto.text = """
                            $diaSemana • $turno
                            $dataInspecao
                            Inspetor: $nomeUsuario
                            Total de aulas verificadas: ${resumo.total}
                        """.trimIndent()

                        txtTaxaFaltas.text = "%.2f%%".format(resumo.percentualFaltas)

                        txtMetricas.text = """
                            ✓ Presentes: ${resumo.presentes}
                            ⚠ Ausentes justificadas: ${resumo.ausentesComJustificativa}
                            ✕ Ausentes sem justificativa: ${resumo.ausentesSemJustificativa}
                            📊 Sem justificativa: ${"%.2f".format(resumo.percentualFaltasSemJustificativa)}%
                        """.trimIndent()

                        val fragment = ResumoFragment()
                        val bundle = Bundle()
                        bundle.putDouble("percentualFaltas", resumo.percentualFaltas)
                        bundle.putDouble(
                            "percentualFaltasSemJustificativa",
                            resumo.percentualFaltasSemJustificativa
                        )

                        fragment.arguments = bundle

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.containerHistoricoFragment, fragment)
                            .commit()

                    } else {
                        Toast.makeText(
                            this@DetalheHistoricoActivity,
                            "Erro ao carregar resumo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResumoInspecao>, t: Throwable) {
                    Toast.makeText(
                        this@DetalheHistoricoActivity,
                        "Erro: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun compartilharRelatorio() {
        val resumo = resumoAtual

        if (resumo == null) {
            Toast.makeText(this, "Resumo ainda não carregado", Toast.LENGTH_SHORT).show()
            return
        }

        val texto = """
            Resumo da Inspeção #$inspecaoId

            Data: $dataInspecao
            Dia: $diaSemana
            Turno: $turno
            Inspetor: $nomeUsuario

            Total de aulas: ${resumo.total}
            Presentes: ${resumo.presentes}
            Ausentes justificadas: ${resumo.ausentesComJustificativa}
            Ausentes sem justificativa: ${resumo.ausentesSemJustificativa}

            Taxa de faltas: ${"%.2f".format(resumo.percentualFaltas)}%
            Taxa sem justificativa: ${"%.2f".format(resumo.percentualFaltasSemJustificativa)}%
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Relatório de Inspeção #$inspecaoId")
        intent.putExtra(Intent.EXTRA_TEXT, texto)

        startActivity(
            Intent.createChooser(
                intent,
                "Compartilhar relatório"
            )
        )
    }
}