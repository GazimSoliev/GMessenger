package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.data.repository.*
import com.gazim.gmessenger.server.domain.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::DatabaseTransactionImpl) bind DatabaseTransaction::class
        singleOf(::ChatRepositoryImpl) bind ChatRepository::class
        singleOf(::MessageRepositoryImpl) bind MessageRepository::class
        singleOf(::FileRepositoryImpl) bind FileRepository::class
        singleOf(::LoginRepositoryImpl) bind LoginRepository::class
        singleOf(::PasswordRepositoryImpl) bind PasswordRepository::class
        singleOf(::TokenRepositoryImpl) bind TokenRepository::class
        singleOf(::UserRepositoryImpl) bind UserRepository::class
    }
