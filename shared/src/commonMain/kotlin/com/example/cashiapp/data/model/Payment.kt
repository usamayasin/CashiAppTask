package com.example.cashiapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val recipientEmail: String = "",
    val amount: Double = 0.0,
    val currency: String = "",
    val timestamp: Long = 0L
)