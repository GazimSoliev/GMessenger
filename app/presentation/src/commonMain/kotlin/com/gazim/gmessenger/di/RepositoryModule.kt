package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.service.*
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::SessionServiceImpl) bind SessionService::class
        singleOf(::GMessengerAuthServiceImpl) bind GMessengerAuthService::class
        factory<GMessengerService> { getCurrentAccountScope().get() }
    }

fun Scope.getCurrentSession() = get<SessionService>().currentSession()!!

fun Scope.getCurrentToken() = get<SessionService>().currentToken()!!

fun Scope.getCurrentAccountScope() = getScope(getCurrentSession())
