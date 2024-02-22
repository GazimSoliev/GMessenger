package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.domain.service.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::AuthorizationService) bind IAuthorizationService::class
        singleOf(::ChatService) bind IChatService::class
        singleOf(::MessagingService) bind IMessagingService::class
        singleOf(::UserService) bind IUserService::class
    }
