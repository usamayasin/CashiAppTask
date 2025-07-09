package com.example.cashiapp.android.viewmodel

import com.example.cashiapp.pressentation.states.UiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.pressentation.states.DataState
import com.example.cashiapp.usecase.GetTransactionHistoryUseCase
import com.example.cashiapp.usecase.SendPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase
) : ViewModel() {

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun sendPayment(email: String, amount: Double, currency: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val payment = Payment(
                recipientEmail = email,
                amount = amount,
                currency = currency,
                timestamp = System.currentTimeMillis()
            )
            when (val result = sendPaymentUseCase.execute(payment)) {
                is DataState.Success -> {
                    loadPayments()
                    _uiState.value = UiState.Success
                }
                is DataState.Error -> {
                    _uiState.value = UiState.Failure(result.message)
                }
                DataState.Loading -> {
                    _uiState.value = UiState.Loading
                }

                else -> {}
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }

    fun loadPayments() {
        viewModelScope.launch {
            _payments.value = getTransactionHistoryUseCase.execute()
        }
    }
}
