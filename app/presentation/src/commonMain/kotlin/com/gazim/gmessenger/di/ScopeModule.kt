package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.service.GMessengerConnectionService
import com.gazim.gmessenger.domain.service.SessionService
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val hostNamed = named("host")
val tokenNamed = named("token")

fun Scope.getHost() = get<String>(hostNamed)

fun Scope.getToken() = get<String>(tokenNamed)

val scopeModule =
    module {
        factory(hostNamed) {
            val connectionAPI = get<GMessengerConnectionService>()
            val url = connectionAPI.getCurrentServer()?.url!!
            url
        }
        factory(tokenNamed) {
            val sessionService = get<SessionService>()
            val token = sessionService.currentToken()!!
            token
        }
        scope<AccountScope> {
            accountApiModule()
            accountServiceModule()
            accountUseCaseModule()
            accountViewModelModule()
        }
        scope<AuthScope> {
            authApiModule()
            authServiceModule()
            authUseCaseModule()
            authViewModelModule()
        }
    }

class AccountScope

class AuthScope