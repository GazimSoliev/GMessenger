package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow

fun interface IOnLogInUseCase {
    suspend operator fun invoke(loginPassword: ILoginPasswordModel): Boolean
}

fun interface IOnRegisterUseCase {
    suspend operator fun invoke(account: IAccountModel): Boolean
}

fun interface IGetOwnAccountUseCase {
    suspend operator fun invoke(): IUserModel
}

fun interface IFilterUsersUseCase {
    suspend operator fun invoke(query: String): List<IUserModel>
}

fun interface IGetChatsUseCase {
    suspend operator fun invoke(): List<IChatModel>
}

fun interface IPassAuthUseCase {
    operator fun invoke(): Boolean
}

fun interface IGetOwnUser {
    suspend operator fun invoke(): IUserModel
}

fun interface IOpenChatUseCase {
    suspend operator fun invoke()
}

fun interface ICloseChatUseCase {
    suspend operator fun invoke()
}

fun interface ISendMessageUseCase {
    suspend operator fun invoke(message: ISentMessageModel)
}

fun interface IGetMessagesUseCase {
    suspend operator fun invoke(): Flow<IMessageModel>
}

fun interface IGetChatNameUseCase {
    suspend operator fun invoke(): String
}

fun interface ISendChatUseCase {
    suspend operator fun invoke(chat: IChatModel)
}

fun interface ICreateChatUseCase {
    suspend operator fun invoke(user: IUserModel)
}

fun interface IValidateLogin {
    suspend operator fun invoke(login: String): Boolean
}

fun interface IValidatePassword {
    suspend operator fun invoke(password: String): Boolean
}

fun interface IValidateNickname {
    suspend operator fun invoke(nickname: String): Boolean
}

fun interface IValidateUsername {
    suspend operator fun invoke(username: String): Boolean
}

fun interface IOpenNotificationUseCase {
    suspend operator fun invoke()
}

fun interface ICloseNotificationUseCase {
    suspend operator fun invoke()
}

fun interface IGetNotificationsUseCase {
    suspend operator fun invoke(): Flow<INotificationModel>
}

fun interface ICloseAccountScopeUseCase {
    operator fun invoke()
}

fun interface ICreateChatScopeUseCase {
    operator fun invoke()
}

fun interface ICloseChatScopeUseCase {
    operator fun invoke()
}

fun interface IGetSessionUseCase {
    operator fun invoke(): String?
}
