package com.example.cashiapp.data.repository

import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.data.remote.ApiClient
import com.example.cashiapp.domain.repository.PaymentRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PaymentRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val apiClient: ApiClient = ApiClient()
) : PaymentRepository {

    override suspend fun sendPayment(payment: Payment): Boolean {
        // 1. Process via API
        val apiSuccess = apiClient.sendPayment(payment)
        if (!apiSuccess) return false

        // 2. Store in Firestore
        return try {
            firestore.collection("payments")
                .add(payment)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getTransactionHistory(): List<Payment> {
        return try {
            val snapshot = firestore.collection("payments").get().await()
            snapshot.documents.mapNotNull { it.toObject(Payment::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}