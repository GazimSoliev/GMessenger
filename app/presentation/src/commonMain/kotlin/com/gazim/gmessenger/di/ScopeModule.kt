package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.service.GMessengerService
import com.gazim.gmessenger.domain.service.IGMessengerService
import org.koin.dsl.module

val scopeModule =
    module {
        scope<AccountScope> {
            scoped<IGMessengerService> { GMessengerService(getCurrentToken()) }
//            scopeNotification()
//            scoped<INotificationRepository> { NotificationRepository(runBlocking { get<IGMessengerRepository>().getNotifications() }) }
        }
    }

class AccountScope

// expect fun ScopeDSL.scopeNotification()
