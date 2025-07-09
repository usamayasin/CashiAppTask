package com.example.cashiapp.domain.repository

import com.example.cashiapp.data.model.Payment

interface PaymentRepository {
    suspend fun sendPayment(payment: Payment): Boolean
    suspend fun getTransactionHistory(): List<Payment>
}