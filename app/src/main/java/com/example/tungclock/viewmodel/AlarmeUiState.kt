package com.example.tungclock.viewmodel

import com.example.tungclock.data.Alarme

data class AlarmeUiState(
    val alarmes: List<Alarme> = emptyList(),
    val carregando: Boolean = true,
    val erro: String? = null,
    val horarioFormulario: String = "07:00",
    val nomeFormulario: String = "",
    val diasFormulario: Set<String> = setOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
)
