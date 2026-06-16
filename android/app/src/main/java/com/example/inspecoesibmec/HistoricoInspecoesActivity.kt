package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inspecoesibmec.adapter.HistoricoInspecoesAdapter
import com.example.inspecoesibmec.model.Inspecao
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoricoInspecoesActivity : AppCompatActivity() {

    private lateinit var recyclerHistorico: RecyclerView
    private lateinit var btnVoltarHomeHistorico: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_inspecoes)

        recyclerHistorico = findViewById(R.id.recyclerHistorico)
        recyclerHistorico.layoutManager = LinearLayoutManager(this)
        recyclerHistorico.setHasFixedSize(false)

        btnVoltarHomeHistorico = findViewById(R.id.btnVoltarHomeHistorico)

        btnVoltarHomeHistorico.setOnClickListener {
            finish()
        }

        carregarHistorico()
    }

    private fun carregarHistorico() {
        ApiClient.apiService.listarInspecoes()
            .enqueue(object : Callback<List<Inspecao>> {
                override fun onResponse(
                    call: Call<List<Inspecao>>,
                    response: Response<List<Inspecao>>
                ) {
                    if (response.isSuccessful && response.body() != null) {

                        val inspecoesFinalizadas = response.body()!!
                            .filter { it.finalizada }
                            .sortedByDescending { it.id }

                        recyclerHistorico.adapter =
                            HistoricoInspecoesAdapter(inspecoesFinalizadas) { inspecao ->

                                val intent = Intent(
                                    this@HistoricoInspecoesActivity,
                                    DetalheHistoricoActivity::class.java
                                )

                                intent.putExtra("inspecaoId", inspecao.id)
                                intent.putExtra("dataInspecao", inspecao.dataInspecao)
                                intent.putExtra("diaSemana", inspecao.diaSemana)
                                intent.putExtra("turno", inspecao.turno)
                                intent.putExtra("nomeUsuario", "Inspetor ${inspecao.inspetorId}")

                                startActivity(intent)
                            }

                    } else {
                        Toast.makeText(
                            this@HistoricoInspecoesActivity,
                            "Erro ao carregar histórico",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Inspecao>>, t: Throwable) {
                    Toast.makeText(
                        this@HistoricoInspecoesActivity,
                        "Erro: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}