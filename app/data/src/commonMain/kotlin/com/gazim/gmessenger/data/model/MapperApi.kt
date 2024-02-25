package com.gazim.gmessenger.data.model

import com.gazim.gmessenger.api.repository.IChatWebSocket
import com.gazim.gmessenger.api.repository.INotificationSocket
import com.gazim.gmessenger.backend.common.model.*
import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun IUserPresent.toUserModel(): IUserModel = UserModel(nickname = nickname, username = username)

fun IUserModel.toUser(): IUserPresent = UserPresent(nickname = nickname, username = username)

fun IChatPresent.toChatModel(): IChatModel =
    if (this is IPrivateChatPresent) {
        PrivateChatModel(identifier = identifier, title = title, user = user.toUserModel())
    } else {
        ChatModel(identifier = identifier, title = title)
    }

fun IChatModel.toChat(): IChatPresent =
    if (this is IPrivateChatModel) {
        PrivateChatPresent(identifier = identifier, title = title, user = user.toUser())
    } else {
        ChatPresent(identifier = identifier, title = title)
    }

fun IMessagePresent.toMessageModel() =
    if (this is IYourMessagePresent) {
        YourMessageModel(message = message, sentAt = sentAt, user = user.toUserModel())
    } else {
        MessageModel(message = message, sentAt = sentAt, user = user.toUserModel())
    }

fun ISentMessageModel.toSentMessage() = SentMessagePresent(message = message)

fun IChatWebSocket.toChatWebSocketModel(chatName: String) =
    object : IChatWebSocketModel {
        override val chatName: String = chatName

        override val messages: Flow<IMessageModel> =
            this@toChatWebSocketModel.messages.map { it.toMessageModel() }

        override suspend fun sendMessage(msg: ISentMessageModel) = this@toChatWebSocketModel.sendMessage(msg.toSentMessage())

        override suspend fun openConnection() = this@toChatWebSocketModel.openConnection()

        override fun close() = this@toChatWebSocketModel.close()
    }

fun INotificationSocket.toNotificationWebSocketModel() =
    object : INotificationWebSocketModel {
        override val notifications: Flow<INotificationModel> =
            this@toNotificationWebSocketModel.notifications.map(
                INotificationPresent::toNotificationModel,
            )

        override suspend fun openConnection() = this@toNotificationWebSocketModel.openConnection()

        override suspend fun closeConnection() = this@toNotificationWebSocketModel.closeConnection()
    }

fun INotificationPresent.toNotificationModel() =
    when (this) {
        is INotificationMessagePresent ->
            NotificationMessageModel(
                message = message,
                sentAt = sentAt,
                user = user.toUserModel(),
                chatName = chatName,
            )

        else -> null!!
    }
