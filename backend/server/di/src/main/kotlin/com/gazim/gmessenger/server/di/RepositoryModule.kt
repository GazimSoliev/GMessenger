package com.gazim.utils.com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.data.repository.ChatRepository
import com.gazim.gmessenger.server.data.repository.LoginRegisterRepository
import com.gazim.gmessenger.server.data.repository.MessageRepository
import com.gazim.gmessenger.server.data.repository.UserRepository
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import com.gazim.gmessenger.server.domain.repository.ILoginRegisterRepository
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::ChatRepository) bind IChatRepository::class
        singleOf(::LoginRegisterRepository) bind ILoginRegisterRepository::class
        singleOf(::MessageRepository) bind IMessageRepository::class
        singleOf(::UserRepository) bind IUserRepository::class
    }
