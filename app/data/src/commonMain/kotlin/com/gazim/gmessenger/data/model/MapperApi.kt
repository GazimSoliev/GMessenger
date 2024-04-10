package com.gazim.gmessenger.data.model

import com.gazim.gmessenger.api.IChatWebSocket
import com.gazim.gmessenger.api.INotificationSocket
import com.gazim.gmessenger.api.model.*
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.model.MessagePage
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.model.ProfileForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.gazim.gmessenger.api.model.Image as ImageAPI
import com.gazim.gmessenger.api.model.MessagePageKey as MessagePageKeyAPI
import com.gazim.gmessenger.api.model.MyMessagePage as MessagePageAPI
import com.gazim.gmessenger.api.model.ProfileForm as ProfileFormAPI

fun MessagePageAPI.toDomain() =
    MessagePage(
        data = data.map { it.toDomain() },
        next = next?.toDomain(),
        prev = prev?.toDomain(),
    )

fun MessagePageKeyAPI.toDomain() =
    MessagePageKey(
        start = start,
        end = end,
    )

fun MessagePageKey.toAPI() = MessagePageKeyAPI(start = start, end = end)

fun User.toDomain() = UserModel(id = id, nickname = nickname, username = username, photo = photo?.toDomain())

fun ImageAPI.toDomain() = Image(id = id, type = type)

fun IUserModel.toAPI() = User(id = id, nickname = nickname, username = username, photo = null)

fun IChat.toDomain() =
    if (this is PrivateChat) {
        PrivateChatModel(id = id, title = title, user = user.toDomain())
    } else {
        ChatModel(id = id, title = title)
    }

fun IChatModel.toAPI() =
    if (this is IPrivateChatModel) {
        PrivateChat(id = id, title = title, user = user.toAPI())
    } else {
        Chat(id = id, title = title)
    }

fun IMessage.toDomain() =
    if (this is MyMessage) {
        YourMessageModel(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toDomain(),
        )
    } else {
        MessageModel(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toDomain(),
        )
    }

fun ISentMessageModel.toAPI() = MessageForm(message = message)

fun ProfileForm.toAPI() =
    ProfileFormAPI(
        nickname = nickname,
        username = username,
    )

fun IChatWebSocket.toChatWebSocketModel(chatName: String) =
    object : IChatWebSocketModel {
        override val chatName: String = chatName

        override val messages: Flow<IMessageModel> =
            this@toChatWebSocketModel.messages.map { it.toDomain() }

        override suspend fun sendMessage(msg: ISentMessageModel) = this@toChatWebSocketModel.sendMessage(msg.toAPI())

        override suspend fun openConnection() = this@toChatWebSocketModel.openConnection()

        override fun close() = this@toChatWebSocketModel.close()
    }

fun INotificationSocket.toNotificationWebSocketModel() =
    object : INotificationWebSocketModel {
        override val notifications: Flow<INotificationModel> =
            this@toNotificationWebSocketModel.notifications.map(
                MessageNotification::toNotificationModel,
            )

        override suspend fun openConnection() = this@toNotificationWebSocketModel.openConnection()

        override suspend fun closeConnection() = this@toNotificationWebSocketModel.closeConnection()
    }

@Suppress("USELESS_IS_CHECK", "UNREACHABLE_CODE")
fun MessageNotification.toNotificationModel() =
    when (this) {
        is MessageNotification ->
            NotificationMessageModel(
                id = TODO("Fix it later"),
                message = message,
                sentAt = sentAt,
                user = user.toDomain(),
                chatName = chatName,
            )

        else -> null!!
    }
