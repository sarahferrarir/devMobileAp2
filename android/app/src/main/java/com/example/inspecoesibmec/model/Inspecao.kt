package com.example.inspecoesibmec.model

data class Inspecao(
    val id: Int = 0,
    val dataInspecao: String,
    val diaSemana: String,
    val turno: String,
    val inspetorId: Int,
    val finalizada: Boolean
)