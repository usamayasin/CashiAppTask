package com.example.cashiapp.pressentation.states

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Failure(val message: String) : UiState()
}