@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.data.mapper.toMessage
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.repository.MessageRepository
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class MessageRepositoryImpl : MessageRepository {
    override suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: String,
        sentAt: Instant,
    ): Message {
        val accountEntity = AccountEntity.Companion[userId]
        val chatEntity = ChatEntity.Companion[chatId]
        val messageEntity =
            MessageEntity.Companion.new {
                this.account = accountEntity
                this.chatEntity = chatEntity
                this.message = message
                this.sentAt = sentAt
            }
        return messageEntity.toMessage()
    }

    override suspend fun getMessages(
        chatId: Uuid,
        start: Instant,
        end: Instant,
    ): List<Message> =
        MessageEntity
            .find {
                (MessageTable.idChat eq chatId.toJavaUuid()) and
                    (MessageTable.createdAt less start) and
                    (MessageTable.createdAt greaterEq end)
            }.orderBy(MessageTable.createdAt to SortOrder.DESC)
            .map(MessageEntity::toMessage)

    override suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: Instant,
    ) = MessageEntity
        .find {
            (MessageTable.idChat eq chatId.toJavaUuid()) and
                (MessageTable.createdAt less start)
        }.orderBy(MessageTable.createdAt to SortOrder.ASC)
        .limit(offset.toInt())
        .firstOrNull()
        ?.sentAt

    override suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: Instant,
    ) = MessageEntity
        .find {
            (MessageTable.idChat eq chatId.toJavaUuid()) and
                (MessageTable.createdAt greaterEq end)
        }.orderBy(MessageTable.createdAt to SortOrder.ASC)
        .limit(1)
        .offset(offset)
        .singleOrNull()
        ?.sentAt
}
