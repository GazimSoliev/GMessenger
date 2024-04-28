package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule =
    module {
        factoryOf(::CreateChatUseCaseImpl) bind CreateChatUseCase::class
        factoryOf(::FindUserUseCaseImpl) bind FindUserUseCase::class
        factoryOf(::GetUserUseCaseImpl) bind GetUserUseCase::class
        factoryOf(::GetChatUseCaseImpl) bind GetChatUseCase::class
        factoryOf(::GetMessageFlowUseCaseImpl) bind GetMessageFlowUseCase::class
        factoryOf(::GetChatsUseCaseImpl) bind GetChatsUseCase::class
        factoryOf(::GetNotificationsImpl) bind GetNotifications::class
        factoryOf(::GetUserUseCaseImpl) bind GetUserUseCase::class
        factoryOf(::LoginUseCaseImpl) bind LoginUseCase::class
        factoryOf(::RegisterUseCaseImpl) bind RegisterUseCase::class
        factoryOf(::SendMessageUseCaseImpl) bind SendMessageUseCase::class
        factoryOf(::GetMessagesUseCaseImpl) bind GetMessagesUseCase::class
        factoryOf(::EditProfileUseCaseImpl) bind EditProfileUseCase::class
        factoryOf(::UploadProfilePhotoUseCaseImpl) bind UploadProfilePhotoUseCase::class
        factoryOf(::GetImageContentUseCaseImpl) bind GetImageContentUseCase::class
    }
