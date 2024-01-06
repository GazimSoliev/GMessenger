package com.gazim.gmessenger.di

import com.gazim.gmessenger.app.service.INotificationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module =
    module {
        factory<INotificationService> {
            getCurrentAccountScope().get()
        }
    }
