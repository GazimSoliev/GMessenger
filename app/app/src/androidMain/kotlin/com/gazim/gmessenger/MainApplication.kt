package com.gazim.gmessenger

import android.app.Application
import com.gazim.gmessenger.di.repositoryModule
import com.gazim.gmessenger.di.scopeModule
import com.gazim.gmessenger.di.useCaseModule
import com.gazim.gmessenger.di.viewModelModule
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
            modules(repositoryModule, useCaseModule, viewModelModule, scopeModule)
        }
    }
}
