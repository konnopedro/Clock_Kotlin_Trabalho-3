package com.example.tungclock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tungclock.data.FusoHorario
import com.example.tungclock.data.repository.FusoHorarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FusoViewModel(private val repository: FusoHorarioRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FusoUiState())
    val uiState: StateFlow<FusoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.buscarTodos().collect { fusos ->
                    _uiState.update { it.copy(fusos = fusos, carregando = false, erro = null) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(carregando = false, erro = e.message ?: "Erro ao buscar fusos") }
            }
        }
    }

    fun adicionarFuso(fuso: FusoHorario) = executar { repository.inserir(fuso) }
    fun atualizarFuso(fuso: FusoHorario) = executar { repository.atualizar(fuso) }
    fun removerFuso(fuso: FusoHorario) = executar { repository.excluir(fuso) }

    private fun executar(operacao: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                operacao()
                _uiState.update { it.copy(erro = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(erro = e.message ?: "Erro ao salvar fuso") }
            }
        }
    }
}
