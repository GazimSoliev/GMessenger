package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.api.GMessengerAPIImpl
import com.gazim.gmessenger.data.api.GMessengerAuthAPIImpl
import com.gazim.gmessenger.data.api.GMessengerConnectionAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModule =
    module {
        factoryOf(::GMessengerConnectionAPIImpl) bind GMessengerConnectionAPI::class
    }

fun ScopeDSL.authApiModule() {
    scoped<GMessengerAuthAPI> {
        val url = getHost()
        GMessengerAuthAPIImpl(url)
    }
}


fun ScopeDSL.accountApiModule() {
    scoped<GMessengerAPI> {
        val url = getHost()
        val token = getToken()
        GMessengerAPIImpl(url, token)
    }
}
