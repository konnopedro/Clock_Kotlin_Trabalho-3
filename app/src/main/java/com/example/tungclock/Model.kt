package com.example.tungclock

data class Alarme(
    val id: Int,
    val horario: String,
    val nome: String,
    val dias: String,
    var ativo: Boolean
)

data class FusoHorario(
    val id: Int,
    val cidade: String,
    val pais: String,
    val zona: String,
    val offset: Int
)
