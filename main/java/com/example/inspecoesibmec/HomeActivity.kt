package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var txtBemVindo: TextView
    private lateinit var txtTipoUsuario: TextView
    private lateinit var btnRealizarInspecao: Button
    private lateinit var btnBaixarExcel: Button

    private var usuarioId: Int = 0
    private var nomeUsuario: String = ""
    private var tipoUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txtBemVindo = findViewById(R.id.txtBemVindo)
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario)

        btnRealizarInspecao = findViewById(R.id.btnRealizarInspecao)
        btnBaixarExcel = findViewById(R.id.btnBaixarExcel)

        usuarioId = intent.getIntExtra("usuarioId", 0)
        nomeUsuario = intent.getStringExtra("nomeUsuario") ?: ""
        tipoUsuario = intent.getStringExtra("tipoUsuario") ?: ""

        txtBemVindo.text = "Olá, $nomeUsuario"
        txtTipoUsuario.text = tipoUsuario

        btnRealizarInspecao.setOnClickListener {

            val intent = Intent(this, SelecaoTurnoActivity::class.java)

            intent.putExtra("usuarioId", usuarioId)
            intent.putExtra("nomeUsuario", nomeUsuario)

            startActivity(intent)
        }

        btnBaixarExcel.setOnClickListener {

            // Intent implícita faremos depois

        }
    }
}