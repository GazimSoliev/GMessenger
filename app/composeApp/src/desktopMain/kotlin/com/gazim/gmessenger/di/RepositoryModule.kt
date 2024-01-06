package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.repository.GMessengerAuthRepository
import com.gazim.gmessenger.data.repository.SessionRepository
import com.gazim.gmessenger.domain.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::SessionRepository) bind ISessionRepository::class
        singleOf(::GMessengerAuthRepository) bind IGMessengerAuthRepository::class
        factory<IGMessengerRepository> {
            getCurrentAccountScope().get()
        }
        factory<IChatSessionRepository> {
            getCurrentAccountScope().get()
        }
        factory<IChatRepository> {
            getCurrentChatScope().get()
        }
        factory<INotificationRepository> {
            getCurrentAccountScope().get()
        }
    }

fun Scope.getCurrentToken() = get<ISessionRepository>().token

fun Scope.getCurrentAccountScope() = getScope(getCurrentToken())

fun Scope.getCurrentChatId() = getCurrentAccountScope().get<IChatSessionRepository>().currentChat.identifier

fun Scope.getCurrentChatScope() = getScope(getCurrentChatId())
