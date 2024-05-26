package com.gazim.gmessenger.di

import com.gazim.gmessenger.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind

fun ScopeDSL.accountUseCaseModule() {
    factoryOf(::OnRegisterUseCaseImpl) bind OnRegisterUseCase::class
    factoryOf(::GetChatsUseCaseImpl) bind GetChatsUseCase::class
    factoryOf(::GetOwnUserImpl) bind GetOwnUser::class
    factoryOf(::FilterUsersUseCaseImpl) bind FilterUsersUseCase::class
    factoryOf(::CreateChatUseCaseImpl) bind CreateChatUseCase::class
    factoryOf(::ValidateLoginUseCaseImpl) bind ValidateLoginUseCase::class
    factoryOf(::ValidatePasswordUseCaseImpl) bind ValidatePasswordUseCase::class
    factoryOf(::ValidateNicknameUseCaseImpl) bind ValidateNicknameUseCase::class
    factoryOf(::ValidateUsernameUseCaseImpl) bind ValidateUsernameUseCase::class
    factoryOf(::GetSessionUseCaseImpl) bind GetSessionUseCase::class
    factoryOf(::GetChatUseCaseImpl) bind GetChatUseCase::class
    factoryOf(::GetMessagesUseCaseImpl) bind GetMessagesUseCase::class
    factoryOf(::EditProfileFormUseCaseImpl) bind EditProfileFormUseCase::class
    factoryOf(::UploadProfilePhotoUseCaseImpl) bind UploadProfilePhotoUseCase::class
    factoryOf(::GetImageContentUseCaseImpl) bind GetImageContentUseCase::class
//        factoryOf(::OpenNotificationUseCase) bind IOpenNotificationUseCase::class
//        factoryOf(::CloseNotificationUseCase) bind ICloseNotificationUseCase::class
//        factoryOf(::GetNotificationsUseCase) bind IGetNotificationsUseCase::class
}

fun ScopeDSL.authUseCaseModule() {
    factoryOf(::PassAuthUseCaseImpl) bind PassAuthUseCase::class
    factoryOf(::OnLogInUseCaseImpl) bind OnLogInUseCase::class
    factoryOf(::GetAvailableServersUseCaseImpl) bind GetAvailableServersUseCase::class
    factoryOf(::PingUseCaseImpl) bind PingUseCase::class
    factoryOf(::AddServerUseCaseImpl) bind AddServerUseCase::class
}