package com.gazim.gmessenger.data.model

import com.gazim.api.gmessenger.model.*
import com.gazim.api.gmessenger.repository.IChatWebSocket
import com.gazim.api.gmessenger.repository.INotificationSocket
import com.gazim.gmessenger.client.pc.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun IUser.toUserModel(): IUserModel = UserModel(nickname = nickname, username = username)

fun IUserModel.toUser(): IUser = User(nickname = nickname, username = username)

fun IChat.toChatModel(): IChatModel =
    if (this is IPrivateChat) {
        PrivateChatModel(identifier = identifier, title = title, user = user.toUserModel())
    } else {
        ChatModel(identifier = identifier, title = title)
    }

fun IChatModel.toChat(): IChat =
    if (this is IPrivateChatModel) {
        PrivateChat(identifier = identifier, title = title, user = user.toUser())
    } else {
        Chat(identifier = identifier, title = title)
    }

fun IMessage.toMessageModel() =
    if (this is IYourMessage) {
        YourMessageModel(message = message, sentAt = sentAt, user = user.toUserModel())
    } else {
        MessageModel(message = message, sentAt = sentAt, user = user.toUserModel())
    }

fun ISentMessageModel.toSentMessage() = SentMessage(message = message)

fun IChatWebSocket.toChatWebSocketModel(chatName: String) =
    object : IChatWebSocketModel {
        override val chatName: String = chatName

        override val messages: Flow<List<IMessageModel>> =
            this@toChatWebSocketModel.messages.map { it.map(IMessage::toMessageModel) }

        override suspend fun sendMessage(msg: ISentMessageModel) = this@toChatWebSocketModel.sendMessage(msg.toSentMessage())

        override suspend fun openConnection() = this@toChatWebSocketModel.openConnection()

        override fun close() = this@toChatWebSocketModel.close()
    }

fun INotificationSocket.toNotificationWebSocketModel() =
    object : INotificationWebSocketModel {
        override val notifications: Flow<INotificationModel> =
            this@toNotificationWebSocketModel.notifications.map(
                INotification::toNotificationModel,
            )

        override suspend fun openConnection() = this@toNotificationWebSocketModel.openConnection()

        override suspend fun closeConnection() = this@toNotificationWebSocketModel.closeConnection()
    }

fun INotification.toNotificationModel() =
    when (this) {
        is INotificationMessage ->
            NotificationMessageModel(
                message = message,
                sentAt = sentAt,
                user = user.toUserModel(),
                chatName = chatName,
            )

        else -> null!!
    }
