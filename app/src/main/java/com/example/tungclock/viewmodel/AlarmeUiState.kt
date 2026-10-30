package com.example.tungclock.viewmodel

import com.example.tungclock.data.Alarme

data class AlarmeUiState(
    val alarmes: List<Alarme> = emptyList(),
    val carregando: Boolean = true,
    val erro: String? = null,
    val horarioFormulario: String = "07:00",
    val nomeFormulario: String = "",
    val alarmeEmEdicao: Int? = null,
    val horarioEdicao: String = "",
    val nomeEdicao: String = "",
    val diasEdicao: Set<String> = emptySet(),
    val ativoEdicao: Boolean = true,
    val diasFormulario: Set<String> = setOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
)
