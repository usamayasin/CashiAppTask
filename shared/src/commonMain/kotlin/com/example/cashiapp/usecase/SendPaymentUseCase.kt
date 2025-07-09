package com.example.cashiapp.usecase

import com.example.cashiapp.pressentation.states.DataState
import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.domain.repository.PaymentRepository
import com.example.cashiapp.validation.PaymentValidator

class SendPaymentUseCase(
    private val repository: PaymentRepository
) {
    suspend fun execute(payment: Payment): DataState<Unit> {
        if (!PaymentValidator.isValidEmail(payment.recipientEmail)) {
            return DataState.Error("Invalid email")
        }
        if (!PaymentValidator.isValidAmount(payment.amount)) {
            return DataState.Error("Invalid amount")
        }
        if (!PaymentValidator.isValidCurrency(payment.currency)) {
            return DataState.Error("Invalid currency")
        }

        return try {
            val success = repository.sendPayment(payment)
            if (success) DataState.Success(Unit)
            else DataState.Error("Payment failed")
        } catch (e: Exception) {
            DataState.Error("Exception occurred: ${e.message}", e)
        }
    }
}
