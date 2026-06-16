package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inspecoesibmec.adapter.AulaAdapter
import com.example.inspecoesibmec.model.Aula
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAulasActivity : AppCompatActivity() {

    private lateinit var txtTituloLista: TextView
    private lateinit var txtDataInspecao: TextView
    private lateinit var recyclerAulas: RecyclerView
    private lateinit var btnFinalizarInspecao: Button

    private var inspecaoId: Int = 0
    private var usuarioId: Int = 0
    private var nomeUsuario: String = ""
    private var dataInspecao: String = ""
    private var diaSemana: String = ""
    private var turno: String = ""

    private val aulasInspecionadas = mutableSetOf<Int>()
    private var aulas: List<Aula> = emptyList()

    private val registroLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val aulaId = result.data?.getIntExtra("aulaId", 0) ?: 0

                if (aulaId != 0) {
                    aulasInspecionadas.add(aulaId)
                    recyclerAulas.adapter?.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_aulas)

        txtTituloLista = findViewById(R.id.txtTituloLista)
        txtDataInspecao = findViewById(R.id.txtDataInspecao)
        recyclerAulas = findViewById(R.id.recyclerAulas)
        btnFinalizarInspecao = findViewById(R.id.btnFinalizarInspecao)

        inspecaoId = intent.getIntExtra("inspecaoId", 0)
        usuarioId = intent.getIntExtra("usuarioId", 0)
        nomeUsuario = intent.getStringExtra("nomeUsuario") ?: ""
        dataInspecao = intent.getStringExtra("dataInspecao") ?: ""
        diaSemana = intent.getStringExtra("diaSemana") ?: ""
        turno = intent.getStringExtra("turno") ?: ""

        txtTituloLista.text = "$diaSemana • $turno"
        txtDataInspecao.text = dataInspecao

        recyclerAulas.layoutManager = LinearLayoutManager(this)

        carregarAulas()

        btnFinalizarInspecao.setOnClickListener {
            val intent = Intent(this, ResumoInspecaoActivity::class.java)

            intent.putExtra("inspecaoId", inspecaoId)
            intent.putExtra("dataInspecao", dataInspecao)
            intent.putExtra("diaSemana", diaSemana)
            intent.putExtra("turno", turno)
            intent.putExtra("nomeUsuario", nomeUsuario)

            startActivity(intent)
        }
    }

    private fun carregarAulas() {
        ApiClient.apiService.listarAulas(diaSemana, turno)
            .enqueue(object : Callback<List<Aula>> {
                override fun onResponse(
                    call: Call<List<Aula>>,
                    response: Response<List<Aula>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        aulas = response.body()!!

                        recyclerAulas.adapter = AulaAdapter(
                            aulas,
                            aulasInspecionadas
                        ) { aula ->
                            val intent = Intent(
                                this@ListaAulasActivity,
                                RegistroPresencaActivity::class.java
                            )

                            intent.putExtra("inspecaoId", inspecaoId)
                            intent.putExtra("aulaId", aula.id)
                            intent.putExtra("disciplina", aula.disciplina)
                            intent.putExtra("professor", aula.professor)
                            intent.putExtra("sala", aula.sala)
                            intent.putExtra("turmaId", aula.turmaId)

                            registroLauncher.launch(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@ListaAulasActivity,
                            "Erro ao carregar aulas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Aula>>, t: Throwable) {
                    Toast.makeText(
                        this@ListaAulasActivity,
                        "Erro de conexão: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}