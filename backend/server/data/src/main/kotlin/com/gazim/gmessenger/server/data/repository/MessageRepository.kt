package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.mapper.toMessage
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class MessageRepository : IMessageRepository {
    override suspend fun sendMessage(
        userId: UUID,
        chatId: UUID,
        message: MessageForm,
    ): Message =
        dbQuery {
            MessageEntity
                .new {
                    chatEntity = ChatEntity[chatId]
                    this.message = message.message
                    account = AccountEntity[userId]
                    sentAt = LocalDateTime.now(ZoneOffset.UTC)
                }.toMessage()
        }

    override suspend fun getMessages(
        chatId: UUID,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message> =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId) and
                        (MessageTable.createdAt less start) and
                        (MessageTable.createdAt greaterEq end)
                }.orderBy(MessageTable.createdAt to SortOrder.DESC)
                .map(MessageEntity::toMessage)
        }

    override suspend fun nextPage(
        chatId: UUID,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId) and
                        (MessageTable.createdAt less start)
                }.orderBy(MessageTable.createdAt to SortOrder.ASC)
                .limit(offset.toInt())
                .firstOrNull()
                ?.sentAt
        }

    override suspend fun prevPage(
        chatId: UUID,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId) and
                        (MessageTable.createdAt greaterEq end)
                }.orderBy(MessageTable.createdAt to SortOrder.ASC)
                .limit(1, offset)
                .singleOrNull()
                ?.sentAt
        }
}
