package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.api.GMessengerAuthAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModule =
    module {
        factoryOf(::GMessengerAuthAPIImpl) bind GMessengerAuthAPI::class
    }
