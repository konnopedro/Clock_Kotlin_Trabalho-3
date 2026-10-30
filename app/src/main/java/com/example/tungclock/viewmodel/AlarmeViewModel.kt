package com.example.tungclock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tungclock.data.Alarme
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

    fun reiniciarFormulario() {
        _uiState.update { it.copy(
            horarioFormulario = "07:00", nomeFormulario = "",
            diasFormulario = setOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"),
            erro = null
        ) }
    }

    fun alterarHorarioFormulario(valor: String) {
        _uiState.update { it.copy(horarioFormulario = valor) }
    }

    fun alterarNomeFormulario(valor: String) {
        _uiState.update { it.copy(nomeFormulario = valor) }
    }

    fun alterarDiaFormulario(dia: String) {
        _uiState.update {
            it.copy(diasFormulario = if (dia in it.diasFormulario) it.diasFormulario - dia else it.diasFormulario + dia)
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
