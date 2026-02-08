@file:OptIn(ExperimentalUuidApi::class, ExperimentalUuidApi::class)

package com.gazim.gmessenger.data.model

import com.gazim.gmessenger.core.ChatWebSocket
import com.gazim.gmessenger.core.NotificationSocket
import com.gazim.gmessenger.core.model.MessageForm
import com.gazim.gmessenger.core.model.MessageNotification
import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import com.gazim.gmessenger.core.model.GMessengerServer as GMessengerServerAPI
import com.gazim.gmessenger.core.model.IChat as IChatAPI
import com.gazim.gmessenger.core.model.IMessage as IMessageAPI
import com.gazim.gmessenger.core.model.Image as ImageAPI
import com.gazim.gmessenger.core.model.MessagePageKey as MessagePageKeyAPI
import com.gazim.gmessenger.core.model.MyMessage as MyMessageAPI
import com.gazim.gmessenger.core.model.MyMessagePage as MessagePageAPI
import com.gazim.gmessenger.core.model.PrivateChat as PrivateChatAPI
import com.gazim.gmessenger.core.model.ProfileForm as ProfileFormAPI
import com.gazim.gmessenger.core.model.User as UserAPI

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

fun IChatAPI.toDomain() =
    if (this is PrivateChatAPI) {
        PrivateChat(
            id = id,
            title = title,
            user = user.toDomain(),
            lastMessage = lastMessage?.toDomain(),
        )
    } else {
        Chat(
            id = id,
            title = title,
            lastMessage = lastMessage?.toDomain(),
        )
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

fun ChatWebSocket.toChatWebSocketModel() =
    object : IChatWebSocketModel {
        override val messages: Flow<IMessage> =
            this@toChatWebSocketModel.messages.map { it.toDomain() }

        override suspend fun sendMessage(msg: SentMessage) = this@toChatWebSocketModel.sendMessage(msg.toAPI())

        override suspend fun openConnection() = this@toChatWebSocketModel.openConnection()

        override fun close() = this@toChatWebSocketModel.close()
    }

fun NotificationSocket.toNotificationWebSocketModel() =
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

fun GMessengerServerAPI.toDomain() = GMessengerServer(host = host, isSecure = isSecure, title = title)
