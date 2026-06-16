package com.example.inspecoesibmec.model

data class ResumoInspecao(
    val total: Int,
    val presentes: Int,
    val ausentesComJustificativa: Int,
    val ausentesSemJustificativa: Int,
    val percentualFaltas: Double,
    val percentualFaltasSemJustificativa: Double
)