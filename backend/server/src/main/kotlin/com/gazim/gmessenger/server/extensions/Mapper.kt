package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.backend.common.model.*
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.ChatEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

fun AccountEntity.toUser() =
    User(
        nickname = nickname,
        username = username,
    )

fun ChatEntity.toChat(): IChat = Chat(identifier = id.value.toString(), title = title)

fun ChatEntity.toPrivateChat(partner: AccountEntity): IPrivateChat =
    PrivateChat(identifier = id.value.toString(), title = title, user = partner.toUser())

fun ChatEntity.toChat(currentUser: AccountEntity): IChat =
    when (members.count().toInt()) {
        1 -> toPrivateChat(currentUser)
        2 -> toPrivateChat(members.single { it != currentUser })
        else -> toChat()
    }

fun ISentMessage.toMessage(
    user: IUser,
    sentAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
): IMessage = Message(message = message, sentAt = sentAt, user = user)

fun IMessage.toNotificationMessage(chatName: String): INotificationMessage =
    NotificationMessage(
        chatName = chatName,
        user = user,
        message = message,
        sentAt = sentAt,
    )
