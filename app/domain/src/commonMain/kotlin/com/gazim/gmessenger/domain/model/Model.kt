package com.gazim.gmessenger.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

sealed interface IMessage {
    val id: String
    val message: String
    val user: User
    val sentAt: LocalDateTime
}

sealed interface Notification

sealed interface IChat {
    val id: String
    val title: String
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
