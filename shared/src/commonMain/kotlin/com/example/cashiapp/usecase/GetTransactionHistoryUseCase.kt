package com.example.cashiapp.usecase

import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.domain.repository.PaymentRepository

class GetTransactionHistoryUseCase(
    private val repository: PaymentRepository
) {
    suspend fun execute(): List<Payment> {
        return repository.getTransactionHistory().sortedByDescending { it.timestamp }
    }
}