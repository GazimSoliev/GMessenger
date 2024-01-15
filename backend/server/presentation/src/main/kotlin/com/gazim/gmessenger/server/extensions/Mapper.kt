package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.backend.common.model.*
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.ChatEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

fun AccountEntity.toUser() =
    UserPresent(
        nickname = nickname,
        username = username,
    )

fun ChatEntity.toChat(): IChatPresent = ChatPresent(identifier = id.value.toString(), title = title)

fun ChatEntity.toPrivateChat(partner: AccountEntity): IPrivateChatPresent =
    PrivateChatPresent(identifier = id.value.toString(), title = title, user = partner.toUser())

fun ChatEntity.toChat(currentUser: AccountEntity): IChatPresent =
    when (members.count().toInt()) {
        1 -> toPrivateChat(currentUser)
        2 -> toPrivateChat(members.single { it != currentUser })
        else -> toChat()
    }

fun ISentMessagePresent.toMessage(
    user: IUserPresent,
    sentAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
): IMessagePresent = MessagePresent(message = message, sentAt = sentAt, user = user)

fun IMessagePresent.toNotificationMessage(chatName: String): INotificationMessagePresent =
    NotificationMessagePresent(
        chatName = chatName,
        user = user,
        message = message,
        sentAt = sentAt,
    )
