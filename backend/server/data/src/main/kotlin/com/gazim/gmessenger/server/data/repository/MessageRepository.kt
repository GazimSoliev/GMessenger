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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
class MessageRepository : IMessageRepository {
    override suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: MessageForm,
    ): Message =
        dbQuery {
            MessageEntity
                .new {
                    chatEntity = ChatEntity[chatId.toJavaUuid()]
                    this.message = message.message
                    account = AccountEntity[userId.toJavaUuid()]
                    sentAt = LocalDateTime.now(ZoneOffset.UTC)
                }.toMessage()
        }

    override suspend fun getMessages(
        chatId: Uuid,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message> =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId.toJavaUuid()) and
                        (MessageTable.createdAt less start) and
                        (MessageTable.createdAt greaterEq end)
                }.orderBy(MessageTable.createdAt to SortOrder.DESC)
                .map(MessageEntity::toMessage)
        }

    override suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId.toJavaUuid()) and
                        (MessageTable.createdAt less start)
                }.orderBy(MessageTable.createdAt to SortOrder.ASC)
                .limit(offset.toInt())
                .firstOrNull()
                ?.sentAt
        }

    override suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chatId.toJavaUuid()) and
                        (MessageTable.createdAt greaterEq end)
                }.orderBy(MessageTable.createdAt to SortOrder.ASC)
                .limit(1)
                .offset(offset)
                .singleOrNull()
                ?.sentAt
        }
}
