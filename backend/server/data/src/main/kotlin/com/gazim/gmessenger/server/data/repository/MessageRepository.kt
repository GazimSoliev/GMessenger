package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.mapper.toAccountEntity
import com.gazim.gmessenger.server.data.mapper.toMessage
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime

class MessageRepository : IMessageRepository {
    override suspend fun sendMessage(
        user: User,
        chat: IChat,
        message: MessageForm,
    ): Message =
        dbQuery {
            MessageEntity.new {
                chatEntity = ChatEntity[chat.id]
                this.message = message.message
                account = user.toAccountEntity()
            }.toMessage()
        }

    override suspend fun getMessages(
        chat: IChat,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message> =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chat.id) and
                        (MessageTable.createdAt greaterEq start) and
                        (MessageTable.createdAt lessEq end)
                        (MessageTable.createdAt lessEq start) and
                        (MessageTable.createdAt greaterEq end)
                }
                .orderBy(MessageTable.createdAt to SortOrder.DESC)
                .map(MessageEntity::toMessage)
        }

    override suspend fun nextPage(
        chat: IChat,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chat.id) and
                        (MessageTable.createdAt less start)
                }
                .orderBy(MessageTable.createdAt to SortOrder.DESC)
                .limit(offset.toInt())
                .firstOrNull()
                ?.sentAt
        }

    override suspend fun prevPage(
        chat: IChat,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime? =
        dbQuery {
            MessageEntity
                .find {
                    (MessageTable.idChat eq chat.id) and
                        (MessageTable.createdAt greaterEq end)
                }
                .orderBy(MessageTable.createdAt to SortOrder.ASC)
                .limit(1, offset)
                .singleOrNull()
                ?.sentAt
        }
}
