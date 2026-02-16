@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.mapper

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.ImageTable
import com.gazim.gmessenger.server.domain.model.*
import org.jetbrains.exposed.v1.core.SortOrder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

internal fun AccountEntity.toUser(): User =
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

internal fun User.toAccountEntity(): AccountEntity = AccountEntity[id.toJavaUuid()]

internal fun Image.toImageEntity(): ImageEntity = ImageEntity[id.toJavaUuid()]

internal fun ChatEntity.toChat(lastMessage: MessageEntity?): IChat =
    Chat(id = id.value.toKotlinUuid(), title = title, lastMessage = lastMessage?.toMessage())

internal fun ChatEntity.toPrivateChat(
    partner: AccountEntity,
    lastMessage: MessageEntity?,
): PrivateChat =
    PrivateChat(
        id = id.value.toKotlinUuid(),
        title = title,
        user = partner.toUser(),
        lastMessage = lastMessage?.toMessage(),
    )

internal fun ChatEntity.toChat(
    currentUser: AccountEntity,
    lastMessage: MessageEntity?,
): IChat =
    when (members.count()) {
        1L -> toPrivateChat(partner = currentUser, lastMessage = lastMessage)
        2L -> toPrivateChat(partner = members.single { it != currentUser }, lastMessage = lastMessage)
        else -> toChat(lastMessage = lastMessage)
    }

internal fun MessageEntity.toMessage(): Message =
    Message(
        id = id.value.toKotlinUuid(),
        message = message,
        user = account.toUser(),
        sentAt = sentAt,
    )

internal fun ImageEntity.toImage(): Image =
    Image(
        id = id.value.toKotlinUuid(),
        type = type,
    )
