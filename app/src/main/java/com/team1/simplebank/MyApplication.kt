package com.team1.simplebank

import android.app.Application
import com.team1.simplebank.di.apiModule
import com.team1.simplebank.di.dataStoreModule
import com.team1.simplebank.di.koinModule
import com.team1.simplebank.viewmodelfactory.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val module = listOf(koinModule, dataStoreModule, apiModule, viewModelModule)
        startKoin{
            androidContext(this@MyApplication)
            modules(module)
        }
    }
}