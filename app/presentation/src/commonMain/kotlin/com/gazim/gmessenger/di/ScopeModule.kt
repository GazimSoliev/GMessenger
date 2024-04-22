package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.api.GMessengerAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.service.GMessengerService
import com.gazim.gmessenger.domain.service.GMessengerServiceImpl
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scopeModule =
    module {
        scope<AccountScope> {
            scoped<GMessengerAPI> { GMessengerAPIImpl(getCurrentToken()) }
            scopedOf(::GMessengerServiceImpl) bind GMessengerService::class
        }
    }

class AccountScope
