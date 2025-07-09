package com.example.cashiapp

import com.example.cashiapp.validation.PaymentValidator
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class PaymentValidatorTest {

    @Test
    fun `valid email should return true`() {
        assertTrue(PaymentValidator.isValidEmail("john.doe@example.com"))
        assertTrue(PaymentValidator.isValidEmail("a+b_c.d@example.co.uk"))
    }

    @Test
    fun `invalid email should return false`() {
        assertFalse(PaymentValidator.isValidEmail("plainaddress"))
        assertFalse(PaymentValidator.isValidEmail("missingatsign.com"))
        assertFalse(PaymentValidator.isValidEmail("bad@domain"))
        assertFalse(PaymentValidator.isValidEmail("@missinguser.com"))
    }

    @Test
    fun `valid amount should return true`() {
        assertTrue(PaymentValidator.isValidAmount(0.01))
        assertTrue(PaymentValidator.isValidAmount(100.0))
    }

    @Test
    fun `zero or negative amount should return false`() {
        assertFalse(PaymentValidator.isValidAmount(0.0))
        assertFalse(PaymentValidator.isValidAmount(-50.0))
    }

    @Test
    fun `valid currency should return true`() {
        assertTrue(PaymentValidator.isValidCurrency("USD"))
        assertTrue(PaymentValidator.isValidCurrency("usd"))
        assertTrue(PaymentValidator.isValidCurrency("EUR"))
    }

    @Test
    fun `invalid currency should return false`() {
        assertFalse(PaymentValidator.isValidCurrency("INR"))
        assertFalse(PaymentValidator.isValidCurrency("ABC"))
        assertFalse(PaymentValidator.isValidCurrency(""))
    }
}
