package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.inspecoesibmec.model.Inspecao
import com.example.inspecoesibmec.model.ResumoInspecao
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResumoInspecaoActivity : AppCompatActivity() {

    private lateinit var txtTituloResumo: TextView
    private lateinit var txtStatusInspecao: TextView
    private lateinit var txtResumoContexto: TextView
    private lateinit var txtTaxaFaltas: TextView
    private lateinit var txtMetricasResumo: TextView
    private lateinit var btnFinalizar: Button
    private lateinit var btnCompartilhar: Button
    private lateinit var btnVoltarHome: Button

    private var inspecaoId = 0
    private var dataInspecao = ""
    private var diaSemana = ""
    private var turno = ""
    private var nomeUsuario = ""

    private var resumoAtual: ResumoInspecao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_inspecao)

        txtTituloResumo = findViewById(R.id.txtTituloResumo)
        txtStatusInspecao = findViewById(R.id.txtStatusInspecao)
        txtResumoContexto = findViewById(R.id.txtResumoContexto)
        txtTaxaFaltas = findViewById(R.id.txtTaxaFaltas)
        txtMetricasResumo = findViewById(R.id.txtMetricasResumo)

        btnFinalizar = findViewById(R.id.btnFinalizar)
        btnCompartilhar = findViewById(R.id.btnCompartilhar)
        btnVoltarHome = findViewById(R.id.btnVoltarHome)

        inspecaoId = intent.getIntExtra("inspecaoId", 0)
        dataInspecao = intent.getStringExtra("dataInspecao") ?: ""
        diaSemana = intent.getStringExtra("diaSemana") ?: ""
        turno = intent.getStringExtra("turno") ?: ""
        nomeUsuario = intent.getStringExtra("nomeUsuario") ?: ""

        txtTituloResumo.text = "Resumo da Inspeção"
        txtStatusInspecao.text = "● Em andamento"
        txtStatusInspecao.setTextColor(
            ContextCompat.getColor(this, R.color.status_amarelo)
        )

        carregarResumo()

        btnFinalizar.setOnClickListener {
            finalizarInspecao()
        }

        btnCompartilhar.setOnClickListener {
            compartilharRelatorio()
        }

        btnVoltarHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("nomeUsuario", nomeUsuario)
            startActivity(intent)
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

                        txtResumoContexto.text = """
                            $diaSemana • $turno
                            $dataInspecao
                            Inspetor: $nomeUsuario
                            Total de aulas verificadas: ${resumo.total}
                        """.trimIndent()

                        txtTaxaFaltas.text =
                            "%.2f%%".format(resumo.percentualFaltas)

                        txtMetricasResumo.text = """
                            ✓ Presentes: ${resumo.presentes}
                            ⚠ Ausentes justificadas: ${resumo.ausentesComJustificativa}
                            ✕ Ausentes sem justificativa: ${resumo.ausentesSemJustificativa}
                            📊 Sem justificativa: ${"%.2f".format(resumo.percentualFaltasSemJustificativa)}%
                        """.trimIndent()

                        val fragment = ResumoFragment()
                        val bundle = Bundle()

                        bundle.putDouble(
                            "percentualFaltas",
                            resumo.percentualFaltas
                        )

                        bundle.putDouble(
                            "percentualFaltasSemJustificativa",
                            resumo.percentualFaltasSemJustificativa
                        )

                        fragment.arguments = bundle

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.containerResumoFragment, fragment)
                            .commit()

                    } else {
                        Toast.makeText(
                            this@ResumoInspecaoActivity,
                            "Erro ao carregar resumo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResumoInspecao>, t: Throwable) {
                    Toast.makeText(
                        this@ResumoInspecaoActivity,
                        "Erro: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun finalizarInspecao() {
        ApiClient.apiService.finalizarInspecao(inspecaoId)
            .enqueue(object : Callback<Inspecao> {
                override fun onResponse(
                    call: Call<Inspecao>,
                    response: Response<Inspecao>
                ) {
                    if (response.isSuccessful) {
                        txtStatusInspecao.text = "● Finalizada"
                        txtStatusInspecao.setTextColor(
                            ContextCompat.getColor(
                                this@ResumoInspecaoActivity,
                                R.color.status_verde
                            )
                        )

                        Toast.makeText(
                            this@ResumoInspecaoActivity,
                            "Inspeção finalizada",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@ResumoInspecaoActivity,
                            "Erro ao finalizar inspeção",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Inspecao>, t: Throwable) {
                    Toast.makeText(
                        this@ResumoInspecaoActivity,
                        "Erro: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun compartilharRelatorio() {
        val resumo = resumoAtual

        if (resumo == null) {
            Toast.makeText(
                this,
                "Resumo ainda não carregado",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val texto = """
            Resumo da Inspeção

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

        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Relatório de Inspeção"
        )

        intent.putExtra(
            Intent.EXTRA_TEXT,
            texto
        )

        startActivity(
            Intent.createChooser(
                intent,
                "Compartilhar relatório"
            )
        )
    }
}