package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.api.GMessengerConnectionAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModule =
    module {
        singleOf(::GMessengerConnectionAPIImpl) bind GMessengerConnectionAPI::class
    }
