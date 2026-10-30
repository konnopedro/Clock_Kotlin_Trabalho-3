package com.example.tungclock.viewmodel

import com.example.tungclock.FusoHorario

data class FusoUiState(
    val fusos: List<FusoHorario> = emptyList(),
    val carregando: Boolean = true,
    val erro: String? = null
)
