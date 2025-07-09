package com.example.cashiapp.validation


object PaymentValidator {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailRegex.matches(email)
    }

    fun isValidAmount(amount: Double): Boolean {
        return amount > 0.0
    }

    fun isValidCurrency(currency: String): Boolean {
        return listOf("USD", "EUR").contains(currency.uppercase())
    }
}