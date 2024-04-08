package com.gazim.gmessenger.server.data.mapper

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.ImageTable
import com.gazim.gmessenger.server.domain.model.*
import org.jetbrains.exposed.sql.SortOrder

fun AccountEntity.toUser() =
    User(
        id = id.value,
        nickname = nickname,
        username = username,
        photo = photos
            .orderBy(ImageTable.createdAt to SortOrder.DESC)
            .limit(1)
            .singleOrNull()
            ?.toImage(),
    )

fun User.toAccountEntity() = AccountEntity[id]

fun Image.toImageEntity() = ImageEntity[id]

fun ChatEntity.toChat(): IChat = Chat(id = id.value, title = title)

fun ChatEntity.toPrivateChat(partner: AccountEntity): PrivateChat =
    PrivateChat(id = id.value, title = title, user = partner.toUser())

fun ChatEntity.toChat(currentUser: AccountEntity): IChat =
    when (members.count().toInt()) {
        1 -> toPrivateChat(currentUser)
        2 -> toPrivateChat(members.single { it != currentUser })
        else -> toChat()
    }

fun MessageEntity.toMessage() =
    Message(
        id = id.value,
        message = message,
        user = account.toUser(),
        sentAt = sentAt,
    )

fun ImageEntity.toImage() =
    Image(
        id = id.value,
        type = type,
    )
