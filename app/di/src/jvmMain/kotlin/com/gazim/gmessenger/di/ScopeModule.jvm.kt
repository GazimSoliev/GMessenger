package com.gazim.gmessenger.di

import com.gazim.gmessenger.presentation.service.INotificationService
import com.gazim.gmessenger.presentation.service.NotificationService
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind

actual fun ScopeDSL.scopeNotification() {
    scopedOf(::NotificationService) bind INotificationService::class
}
