package com.example.cashiapp.mock

import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.domain.repository.PaymentRepository

class FakePaymentRepository(
    private val shouldSucceed: Boolean,
    private val throwException: Boolean = false
) : PaymentRepository {

    override suspend fun getTransactionHistory(): List<Payment> {
        return listOf(
            Payment("a@example.com", 50.0, "USD", timestamp = 1000),
            Payment("b@example.com", 75.0, "USD", timestamp = 3000),
            Payment("c@example.com", 25.0, "EUR", timestamp = 2000)
        )
    }

    override suspend fun sendPayment(payment: Payment): Boolean {
        if (throwException) throw Exception("Network failure")
        return shouldSucceed
    }
}