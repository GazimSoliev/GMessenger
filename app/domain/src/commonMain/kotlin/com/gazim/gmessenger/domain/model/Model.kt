package com.gazim.gmessenger.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

sealed interface ILoginModel {
    val login: String
}

sealed interface IPasswordModel {
    val password: String
}

sealed interface ILoginPasswordModel : ILoginModel, IPasswordModel

sealed interface IMessage {
    val id: String
    val message: String
    val user: User
    val sentAt: LocalDateTime
}

sealed interface Notification

sealed interface IChatModel {
    val id: String
    val title: String
}

sealed interface IPrivateChatModel : IChatModel {
    val user: User
}

interface IChatWebSocketModel {
    val chatName: String
    val messages: Flow<IMessage>

    suspend fun sendMessage(msg: SentMessage)

    suspend fun openConnection()

    fun close()
}

interface INotificationWebSocketModel {
    val notifications: Flow<Notification>

    suspend fun openConnection()

    suspend fun closeConnection()
}
