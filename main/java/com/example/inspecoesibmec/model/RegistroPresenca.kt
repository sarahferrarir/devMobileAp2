package com.example.inspecoesibmec.model

data class RegistroPresenca(
    val id: Int? = null,
    val inspecaoId: Int,
    val aulaId: Int,
    val status: String,
    val observacao: String?
)