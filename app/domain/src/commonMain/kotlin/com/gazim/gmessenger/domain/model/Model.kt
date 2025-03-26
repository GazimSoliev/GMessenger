@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface IMessage {
    val id: Uuid
    val message: String
    val user: User
    val sentAt: LocalDateTime
}

sealed interface Notification

sealed interface IChat {
    val id: Uuid
    val title: String
}

interface IChatWebSocketModel {
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
