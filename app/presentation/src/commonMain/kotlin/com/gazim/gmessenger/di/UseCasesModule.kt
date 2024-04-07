package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule =
    module {
        factoryOf(::PassAuthUseCase) bind IPassAuthUseCase::class
        factoryOf(::OnLogInUseCase) bind IOnLogInUseCase::class
        factoryOf(::OnRegisterUseCase) bind IOnRegisterUseCase::class
        factoryOf(::GetChatsUseCase) bind IGetChatsUseCase::class
        factoryOf(::GetOwnUser) bind IGetOwnUser::class
        factoryOf(::FilterUsersUseCase) bind IFilterUsersUseCase::class
        factoryOf(::CreateChatUseCase) bind ICreateChatUseCase::class
        factoryOf(::ValidateLogin) bind IValidateLogin::class
        factoryOf(::ValidatePassword) bind IValidatePassword::class
        factoryOf(::ValidateNickname) bind IValidateNickname::class
        factoryOf(::ValidateUsername) bind IValidateUsername::class
        factoryOf(::GetSessionUseCase) bind IGetSessionUseCase::class
        factoryOf(::GetChatUseCase) bind IGetChatUseCase::class
        factoryOf(::GetMessagesUseCase) bind IGetMessagesUseCase::class
        factoryOf(::EditProfileFormUseCaseImpl) bind EditProfileFormUseCase::class
//        factoryOf(::OpenNotificationUseCase) bind IOpenNotificationUseCase::class
//        factoryOf(::CloseNotificationUseCase) bind ICloseNotificationUseCase::class
//        factoryOf(::GetNotificationsUseCase) bind IGetNotificationsUseCase::class
    }
