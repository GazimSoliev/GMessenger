package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.module.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val module =
    module {
        singleOf(::NotificationModule) bind INotificationModule::class
        singleOf(::UserModule) bind IUserModule::class
        singleOf(::LoginModule) bind ILoginModule::class
        singleOf(::TokenModule) bind ITokenModule::class
        singleOf(::MessageModule) bind IMessageModule::class
        singleOf(::ChatModule) bind IChatModule::class
        singleOf(::RegistrationModule) bind IRegistrationModule::class
    }
