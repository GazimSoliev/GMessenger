package com.gazim.gmessenger.data.model

import com.gazim.gmessenger.api.IChatWebSocket
import com.gazim.gmessenger.api.INotificationSocket
import com.gazim.gmessenger.api.model.IChat
import com.gazim.gmessenger.api.model.MessageForm
import com.gazim.gmessenger.api.model.MessageNotification
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.model.IMessage
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.model.ProfileForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.gazim.gmessenger.api.model.Chat as ChatAPI
import com.gazim.gmessenger.api.model.IMessage as IMessageAPI
import com.gazim.gmessenger.api.model.Image as ImageAPI
import com.gazim.gmessenger.api.model.MessagePageKey as MessagePageKeyAPI
import com.gazim.gmessenger.api.model.MyMessage as MyMessageAPI
import com.gazim.gmessenger.api.model.MyMessagePage as MessagePageAPI
import com.gazim.gmessenger.api.model.PrivateChat as PrivateChatAPI
import com.gazim.gmessenger.api.model.ProfileForm as ProfileFormAPI
import com.gazim.gmessenger.api.model.User as UserAPI

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

fun UserAPI.toDomain() = User(id = id, nickname = nickname, username = username, photo = photo?.toDomain())

fun ImageAPI.toDomain() = Image(id = id, type = type)

fun User.toAPI() = UserAPI(id = id, nickname = nickname, username = username, photo = null)

fun IChat.toDomain() =
    if (this is PrivateChatAPI) {
        PrivateChat(id = id, title = title, user = user.toDomain())
    } else {
        Chat(id = id, title = title)
    }

fun IChatModel.toAPI() =
    if (this is IPrivateChatModel) {
        PrivateChatAPI(id = id, title = title, user = user.toAPI())
    } else {
        ChatAPI(id = id, title = title)
    }

fun IMessageAPI.toDomain() =
    if (this is MyMessageAPI) {
        YourMessage(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toDomain(),
        )
    } else {
        Message(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toDomain(),
        )
    }

fun IMessage.toAPI() = MessageForm(message = message)

fun ProfileForm.toAPI() =
    ProfileFormAPI(
        nickname = nickname,
        username = username,
    )

fun SentMessage.toAPI() = MessageForm(message = message)

fun IChatWebSocket.toChatWebSocketModel(chatName: String) =
    object : IChatWebSocketModel {
        override val chatName: String = chatName

        override val messages: Flow<IMessage> =
            this@toChatWebSocketModel.messages.map { it.toDomain() }

        override suspend fun sendMessage(msg: SentMessage) = this@toChatWebSocketModel.sendMessage(msg.toAPI())

        override suspend fun openConnection() = this@toChatWebSocketModel.openConnection()

        override fun close() = this@toChatWebSocketModel.close()
    }

fun INotificationSocket.toNotificationWebSocketModel() =
    object : INotificationWebSocketModel {
        override val notifications: Flow<Notification> =
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
            NotificationMessage(
                id = TODO("Fix it later"),
                message = message,
                sentAt = sentAt,
                user = user.toDomain(),
                chatName = chatName,
            )

        else -> null!!
    }
