package com.example.inspecoesibmec.model

data class Usuario(
    val id: Int,
    val username: String,
    val senha: String,
    val nome: String,
    val tipoUsuario: String
)