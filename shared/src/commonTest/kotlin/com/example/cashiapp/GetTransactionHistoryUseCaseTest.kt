package com.example.cashiapp

import com.example.cashiapp.mock.FakePaymentRepository
import com.example.cashiapp.usecase.GetTransactionHistoryUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTransactionHistoryUseCaseTest {

    private val repository = FakePaymentRepository(true)
    private val useCase = GetTransactionHistoryUseCase(repository)

    @Test
    fun `should return transactions sorted by descending timestamp`() = runTest {
        val result = useCase.execute()

        assertEquals(3, result.size)
        assertEquals("b@example.com", result[0].recipientEmail)
        assertEquals("c@example.com", result[1].recipientEmail)
        assertEquals("a@example.com", result[2].recipientEmail)
    }
}



