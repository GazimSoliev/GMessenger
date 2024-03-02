package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.service.GMessengerAuthService
import com.gazim.gmessenger.data.service.SessionService
import com.gazim.gmessenger.domain.service.IGMessengerAuthService
import com.gazim.gmessenger.domain.service.IGMessengerService
import com.gazim.gmessenger.domain.service.INotificationService
import com.gazim.gmessenger.domain.service.ISessionService
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::SessionService) bind ISessionService::class
        singleOf(::GMessengerAuthService) bind IGMessengerAuthService::class
        factory<IGMessengerService> {
            getCurrentAccountScope().get()
        }
        factory<INotificationService> {
            getCurrentAccountScope().get()
        }
    }

fun Scope.getCurrentSession() = get<ISessionService>().currentSession()!!

fun Scope.getCurrentToken() = get<ISessionService>().currentToken()!!

fun Scope.getCurrentAccountScope() = getScope(getCurrentSession())
