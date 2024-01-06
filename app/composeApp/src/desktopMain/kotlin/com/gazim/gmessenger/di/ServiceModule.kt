package com.gazim.gmessenger.di

import com.gazim.gmessenger.app.service.INotificationService
import org.koin.dsl.module

val serviceModule =
    module {
        factory<INotificationService> {
            getCurrentAccountScope().get()
        }
    }
