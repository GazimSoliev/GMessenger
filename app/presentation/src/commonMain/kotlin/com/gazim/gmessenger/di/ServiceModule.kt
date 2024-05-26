package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.service.*
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::SessionServiceImpl) bind SessionService::class
        singleOf(::GMessengerConnectionServiceImpl) bind GMessengerConnectionService::class
    }

fun ScopeDSL.authServiceModule() {
    scopedOf(::GMessengerAuthServiceImpl) bind GMessengerAuthService::class
}

fun ScopeDSL.accountServiceModule() {
    scopedOf(::GMessengerServiceImpl) bind GMessengerService::class
}
