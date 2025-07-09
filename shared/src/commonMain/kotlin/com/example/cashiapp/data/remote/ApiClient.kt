package com.example.cashiapp.data.remote;

import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.data.model.PaymentRequest
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


class ApiClient {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private val BASE_URL = "http://10.0.2.2:3000" // replace with actual or local JSON Server URL

    suspend fun sendPayment(payment: Payment): Boolean {
        val response = client.post("$BASE_URL/payments") {
            contentType(ContentType.Application.Json)
            setBody(PaymentRequest(payment.recipientEmail, payment.amount, payment.currency))
        }

        return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.Created
    }
}