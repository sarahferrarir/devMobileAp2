package com.example.inspecoesibmec.model

data class Aula(
    val id: Int,
    val disciplina: String,
    val professor: String,
    val turmaId: Int,
    val diaSemana: String,
    val turno: String,
    val sala: String
)