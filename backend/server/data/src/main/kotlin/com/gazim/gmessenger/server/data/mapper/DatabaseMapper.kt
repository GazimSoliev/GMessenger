@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.mapper

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.ImageTable
import com.gazim.gmessenger.server.domain.model.*
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

fun ChatEntity.toChat(): IChat = Chat(id = id.value.toKotlinUuid(), title = title)

fun ChatEntity.toPrivateChat(partner: AccountEntity): PrivateChat = PrivateChat(id = id.value.toKotlinUuid(), title = title, user = partner.toUser())

fun ChatEntity.toChat(currentUser: AccountEntity): IChat =
    when (members.count().toInt()) {
        1 -> toPrivateChat(currentUser)
        2 -> toPrivateChat(members.single { it != currentUser })
        else -> toChat()
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
