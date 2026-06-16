package com.example.inspecoesibmec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inspecoesibmec.model.LoginRequest
import com.example.inspecoesibmec.model.Usuario
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var editUsername: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnEntrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUsername = findViewById(R.id.editUsername)
        editSenha = findViewById(R.id.editSenha)
        btnEntrar = findViewById(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            fazerLogin()
        }
    }

    private fun fazerLogin() {
        val username = editUsername.text.toString()
        val senha = editSenha.text.toString()

        if (username.isBlank() || senha.isBlank()) {
            Toast.makeText(this, "Preencha usuário e senha", Toast.LENGTH_SHORT).show()
            return
        }

        val request = LoginRequest(username, senha)

        ApiClient.apiService.login(request).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful && response.body() != null) {
                    val usuario = response.body()!!
                    Toast.makeText(
                        this@MainActivity,
                        "Login realizado: ${usuario.nome}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(
                        this@MainActivity,
                        HomeActivity::class.java
                    )

                    intent.putExtra("tipoUsuario", usuario.tipoUsuario)
                    intent.putExtra("usuarioId", usuario.id)
                    intent.putExtra("nomeUsuario", usuario.nome)

                    startActivity(intent)

                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Usuário ou senha inválidos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}