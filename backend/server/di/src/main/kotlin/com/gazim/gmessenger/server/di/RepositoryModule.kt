package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.data.repository.*
import com.gazim.gmessenger.server.domain.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::ChatRepository) bind IChatRepository::class
        singleOf(::LoginRegisterRepository) bind ILoginRegisterRepository::class
        singleOf(::MessageRepository) bind IMessageRepository::class
        singleOf(::UserRepository) bind IUserRepository::class
        singleOf(::FileRepositoryImpl) bind FileRepository::class
    }
