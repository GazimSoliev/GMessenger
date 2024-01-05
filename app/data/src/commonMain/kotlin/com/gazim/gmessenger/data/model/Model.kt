package com.gazim.gmessenger.data.model

import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

sealed interface IUserData {
    val nickname: String
    val username: String
}

sealed interface ILoginData {
    val login: String
}

sealed interface IPasswordData {
    val password: String
}

sealed interface ILoginPasswordData : ILoginData, IPasswordData

sealed interface IAccountData : IUserData, ILoginPasswordData

sealed interface ISentMessageData {
    val message: String
}

sealed interface IMessageData : ISentMessageData {
    val sentAt: LocalDateTime
    val user: IUserData
}

sealed interface IYourMessageData : IMessageData

sealed interface IChatData {
    val identifier: String
    val title: String
}

sealed interface IPrivateChatData : IChatData {
    val user: IUserData
}

// todo: Review
interface IChatWebSocketData : Closeable {
    val messages: Flow<IMessageData>

    suspend fun sendMessage(msg: ISentMessageData)

    suspend fun openConnection()
}
