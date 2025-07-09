package com.example.cashiapp.android.di

import com.example.cashiapp.data.repository.PaymentRepositoryImpl
import com.example.cashiapp.domain.repository.PaymentRepository
import com.example.cashiapp.usecase.GetTransactionHistoryUseCase
import com.example.cashiapp.usecase.SendPaymentUseCase
import com.example.cashiapp.android.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Provide repository implementation
    single<PaymentRepository> { PaymentRepositoryImpl() }

    // Provide use cases
    single { SendPaymentUseCase(get()) }
    single { GetTransactionHistoryUseCase(get()) }

    // Provide ViewModel
    viewModel {
        PaymentViewModel(
            sendPaymentUseCase = get(),
            getTransactionHistoryUseCase = get()
        )
    }
}
