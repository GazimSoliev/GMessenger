@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.mapper

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.ImageTable
import com.gazim.gmessenger.server.domain.model.Chat
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.PrivateChat
import com.gazim.gmessenger.server.domain.model.User
import org.jetbrains.exposed.sql.SortOrder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

fun AccountEntity.toUser() =
    User(
        id = id.value.toKotlinUuid(),
        nickname = nickname,
        username = username,
        photo =
            photos
                .orderBy(ImageTable.createdAt to SortOrder.DESC)
                .limit(1)
                .singleOrNull()
                ?.toImage(),
    )

fun User.toAccountEntity() = AccountEntity[id.toJavaUuid()]

fun Image.toImageEntity() = ImageEntity[id.toJavaUuid()]

fun ChatEntity.toChat(lastMessage: MessageEntity?): IChat =
    Chat(id = id.value.toKotlinUuid(), title = title, lastMessage = lastMessage?.toMessage())

fun ChatEntity.toPrivateChat(partner: AccountEntity, lastMessage: MessageEntity?): PrivateChat =
    PrivateChat(
        id = id.value.toKotlinUuid(),
        title = title,
        user = partner.toUser(),
        lastMessage = lastMessage?.toMessage()
    )

fun ChatEntity.toChat(currentUser: AccountEntity, lastMessage: MessageEntity?): IChat =
    when (members.count()) {
        1L -> toPrivateChat(partner = currentUser, lastMessage = lastMessage)
        2L -> toPrivateChat(partner = members.single { it != currentUser }, lastMessage = lastMessage)
        else -> toChat(lastMessage = lastMessage)
    }

fun MessageEntity.toMessage() =
    Message(
        id = id.value.toKotlinUuid(),
        message = message,
        user = account.toUser(),
        sentAt = sentAt,
    )

fun ImageEntity.toImage() =
    Image(
        id = id.value.toKotlinUuid(),
        type = type,
    )
