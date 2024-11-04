package com.gazim.gmessenger

import androidx.compose.runtime.Composable
import com.gazim.gmessenger.di.*
import com.gazim.gmessenger.presentation.App
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import org.koin.logger.SLF4JLogger

@Composable
fun Application() {
    KoinApplication(
        application = {
            logger(SLF4JLogger(level = Level.INFO))
            modules(factoryModule, apiModule, serviceModule, useCaseModule, viewModelModule)
        },
    ) {
        App()
    }
}
