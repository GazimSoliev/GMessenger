package com.gazim.gmessenger

import android.app.Application
import com.gazim.gmessenger.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(factoryModule, apiModule, serviceModule, useCaseModule, viewModelModule)
        }
    }
}
