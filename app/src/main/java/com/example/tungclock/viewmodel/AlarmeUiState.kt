package com.example.tungclock.viewmodel

import com.example.tungclock.Alarme

data class AlarmeUiState(
    val alarmes: List<Alarme> = emptyList(),
    val carregando: Boolean = true,
    val erro: String? = null
)
