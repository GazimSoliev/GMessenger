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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
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
        sentAt: Instant
    ): Message {
        val accountEntity = AccountEntity[userId]
        val chatEntity = ChatEntity[chatId]
        val messageEntity = MessageEntity.new {
            this.account = accountEntity
            this.chatEntity = chatEntity
            this.message = message
            this.sentAt = sentAt.toLocalDateTime(TimeZone.UTC)
        }
        return messageEntity.toMessage()
    }

    override suspend fun getMessages(
        chatId: Uuid,
        start: Instant,
        end: Instant
    ): List<Message> {
        val startDateTime = start.toLocalDateTime(TimeZone.UTC)
        val endDateTime = end.toLocalDateTime(TimeZone.UTC)
        val messageEntities = MessageEntity.find {
            (MessageTable.idChat eq chatId.toJavaUuid()) and
                    (MessageTable.createdAt less startDateTime) and
                    (MessageTable.createdAt greaterEq endDateTime)
        }.orderBy(MessageTable.createdAt to SortOrder.DESC)
        return messageEntities.map(MessageEntity::toMessage)
    }

    override suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: Instant
    ): Instant? {
        val startDateTime = start.toLocalDateTime(TimeZone.UTC)
        return MessageEntity
            .find {
                (MessageTable.idChat eq chatId.toJavaUuid()) and
                        (MessageTable.createdAt less startDateTime)
            }.orderBy(MessageTable.createdAt to SortOrder.ASC)
            .limit(offset.toInt())
            .firstOrNull()
            ?.sentAt
            ?.toInstant(TimeZone.UTC)
    }

    override suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: Instant
    ): Instant? {
        val endDateTime = end.toLocalDateTime(TimeZone.UTC)
        return MessageEntity
            .find {
                (MessageTable.idChat eq chatId.toJavaUuid()) and
                        (MessageTable.createdAt greaterEq endDateTime)
            }.orderBy(MessageTable.createdAt to SortOrder.ASC)
            .limit(1)
            .offset(offset)
            .singleOrNull()
            ?.sentAt
            ?.toInstant(TimeZone.UTC)
    }
}
