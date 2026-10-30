package com.example.tungclock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tungclock.Alarme
import com.example.tungclock.data.repository.AlarmeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlarmeViewModel(private val repository: AlarmeRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AlarmeUiState())
    val uiState: StateFlow<AlarmeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.buscarTodos().collect { alarmes ->
                    _uiState.update { it.copy(alarmes = alarmes, carregando = false, erro = null) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(carregando = false, erro = e.message ?: "Erro ao buscar alarmes") }
            }
        }
    }

    fun adicionarAlarme(alarme: Alarme) = executar { repository.inserir(alarme) }
    fun atualizarAlarme(alarme: Alarme) = executar { repository.atualizar(alarme) }
    fun removerAlarme(alarme: Alarme) = executar { repository.excluir(alarme) }

    private fun executar(operacao: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                operacao()
                _uiState.update { it.copy(erro = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(erro = e.message ?: "Erro ao salvar alarme") }
            }
        }
    }
}
