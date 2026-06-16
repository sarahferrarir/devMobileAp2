package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inspecoesibmec.model.Inspecao
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SelecaoTurnoActivity : AppCompatActivity() {

    private lateinit var editData: EditText
    private lateinit var spinnerTurno: Spinner
    private lateinit var btnIniciarRonda: Button

    private var usuarioId: Int = 0
    private var nomeUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecao_turno)

        editData = findViewById(R.id.editData)
        spinnerTurno = findViewById(R.id.spinnerTurno)
        btnIniciarRonda = findViewById(R.id.btnIniciarRonda)

        usuarioId = intent.getIntExtra("usuarioId", 0)
        nomeUsuario = intent.getStringExtra("nomeUsuario") ?: ""

        val turnos = listOf("MANHÃ", "TARDE", "NOITE")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            turnos
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTurno.adapter = adapter

        btnIniciarRonda.setOnClickListener {
            criarInspecao()
        }
    }

    private fun criarInspecao() {
        val data = editData.text.toString()
        val turno = spinnerTurno.selectedItem.toString()
        val diaSemana = calcularDiaSemana(data)

        if (data.isBlank()) {
            Toast.makeText(this, "Informe a data", Toast.LENGTH_SHORT).show()
            return
        }

        val inspecao = Inspecao(
            dataInspecao = data,
            diaSemana = diaSemana,
            turno = turno,
            inspetorId = usuarioId,
            finalizada = false
        )

        ApiClient.apiService.criarInspecao(inspecao).enqueue(object : Callback<Inspecao> {
            override fun onResponse(call: Call<Inspecao>, response: Response<Inspecao>) {
                if (response.isSuccessful && response.body() != null) {
                    val inspecaoCriada = response.body()!!

                    Toast.makeText(
                        this@SelecaoTurnoActivity,
                        "Inspeção criada",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@SelecaoTurnoActivity, ListaAulasActivity::class.java)

                    intent.putExtra("inspecaoId", inspecaoCriada.id)
                    intent.putExtra("usuarioId", usuarioId)
                    intent.putExtra("nomeUsuario", nomeUsuario)
                    intent.putExtra("dataInspecao", data)
                    intent.putExtra("diaSemana", diaSemana)
                    intent.putExtra("turno", turno)

                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@SelecaoTurnoActivity,
                        "Erro ao criar inspeção",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Inspecao>, t: Throwable) {
                Toast.makeText(
                    this@SelecaoTurnoActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun calcularDiaSemana(data: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(data, formatter)

        return when (localDate.dayOfWeek.value) {
            1 -> "SEGUNDA"
            2 -> "TERCA"
            3 -> "QUARTA"
            4 -> "QUINTA"
            5 -> "SEXTA"
            6 -> "SABADO"
            7 -> "DOMINGO"
            else -> ""
        }
    }
}