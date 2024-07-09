package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.factory.GMessengerAPIFactoryImpl
import com.gazim.gmessenger.data.factory.GMessengerAuthAPIFactoryImpl
import com.gazim.gmessenger.domain.factrory.GMessengerAPIFactory
import com.gazim.gmessenger.domain.factrory.GMessengerAuthAPIFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val factoryModule =
    module {
        singleOf(::GMessengerAPIFactoryImpl) bind GMessengerAPIFactory::class
        singleOf(::GMessengerAuthAPIFactoryImpl) bind GMessengerAuthAPIFactory::class
    }
