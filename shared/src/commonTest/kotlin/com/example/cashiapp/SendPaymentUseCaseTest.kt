package com.example.cashiapp

import com.example.cashiapp.data.model.Payment
import com.example.cashiapp.mock.FakePaymentRepository
import com.example.cashiapp.pressentation.states.DataState
import com.example.cashiapp.usecase.SendPaymentUseCase
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class SendPaymentUseCaseTest {

    @Test
    fun `returns error when email is invalid`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(true))
        val payment = Payment("invalid-email", 10.0, "USD", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Error>(result)
        assertTrue((result as DataState.Error).message.contains("Invalid email"))
    }

    @Test
    fun `returns error when amount is invalid`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(true))
        val payment = Payment("valid@email.com", -5.0, "USD", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Error>(result)
        assertTrue(result.message.contains("Invalid amount"))
    }

    @Test
    fun `returns error when currency is invalid`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(true))
        val payment = Payment("valid@email.com", 100.0, "INR", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Error>(result)
        assertTrue(result.message.contains("Invalid currency"))
    }

    @Test
    fun `returns success when payment is valid and repo returns true`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(shouldSucceed = true))
        val payment = Payment("valid@email.com", 100.0, "USD", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Success<Unit>>(result)
    }

    @Test
    fun `returns error when payment fails in repository`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(shouldSucceed = false))
        val payment = Payment("valid@email.com", 100.0, "USD", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Error>(result)
        assertTrue(result.message.contains("Payment failed"))
    }

    @Test
    fun `returns error when repository throws exception`() = runTest {
        val useCase = SendPaymentUseCase(FakePaymentRepository(shouldSucceed = false, throwException = true))
        val payment = Payment("valid@email.com", 100.0, "USD", timestamp = 0)

        val result = useCase.execute(payment)

        assertIs<DataState.Error>(result)
        assertTrue(result.message.contains("Exception occurred"))
    }
}

