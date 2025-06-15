package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.data.service.SHA256ServiceImpl
import com.gazim.gmessenger.server.domain.service.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule =
    module {
        singleOf(::AuthorizationServiceImpl) bind AuthorizationService::class
        singleOf(::ChatServiceImpl) bind ChatService::class
        singleOf(::MessagingService) bind IMessagingService::class
        singleOf(::UserServiceImpl) bind UserService::class
        singleOf(::FileServiceImpl) bind FileService::class
        singleOf(::SHA256ServiceImpl) bind SHA256Service::class
    }
