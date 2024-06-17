package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.service.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::GMessengerAuthServiceImpl) bind GMessengerAuthService::class
        singleOf(::GMessengerServiceImpl) bind GMessengerService::class
        singleOf(::GMessengerSessionServiceImpl) bind GMessengerSessionService::class
        singleOf(::SessionServiceImpl) bind SessionService::class
    }
