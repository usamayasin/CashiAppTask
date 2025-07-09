package com.example.cashiapp.android

import android.app.Application
import com.example.cashiapp.android.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CashiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@CashiApplication)
            modules(appModule)
        }

//        // Inject shared module manually from Koin
//        val paymentRepository = get<PaymentRepository>(PaymentRepository::class.java)
//        SharedModule.init(paymentRepository)
    }
}
