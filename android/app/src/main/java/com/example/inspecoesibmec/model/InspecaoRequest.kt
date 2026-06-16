package com.example.inspecoesibmec.model

data class InspecaoRequest(
    val dataInspecao: String,
    val diaSemana: String,
    val turno: String,
    val inspetorId: Int,
    val finalizada: Boolean
)