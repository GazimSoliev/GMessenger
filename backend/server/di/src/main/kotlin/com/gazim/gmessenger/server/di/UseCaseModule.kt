package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule =
    module {
        factoryOf(::CreateChatUseCase) bind ICreateChatUseCase::class
        factoryOf(::FindUserUseCase) bind IFindUserUseCase::class
        factoryOf(::GetUserUseCase) bind IGetUserUseCase::class
        factoryOf(::GetChatUseCase) bind IGetChatUseCase::class
        factoryOf(::GetMessageFlowUseCase) bind IGetMessageFlowUseCase::class
        factoryOf(::GetChatsUseCase) bind IGetChatsUseCase::class
        factoryOf(::GetNotifications) bind IGetNotifications::class
        factoryOf(::GetUserUseCase) bind IGetUserUseCase::class
        factoryOf(::LoginUseCase) bind ILoginUseCase::class
        factoryOf(::RegisterUseCase) bind IRegisterUseCase::class
        factoryOf(::SendMessageUseCase) bind ISendMessageUseCase::class
        factoryOf(::GetMessagesUseCase) bind IGetMessagesUseCase::class
        factoryOf(::EditProfileUseCaseImpl) bind EditProfileUseCase::class
        factoryOf(::UploadProfilePhotoUseCaseImpl) bind UploadProfilePhotoUseCase::class
        factoryOf(::GetImageContentUseCaseImpl) bind GetImageContentUseCase::class
    }
