package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.mapper.toMessage
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import org.jetbrains.exposed.sql.SortOrder

class MessageRepository : IMessageRepository {
    override suspend fun getMessages(
        user: User,
        chat: IChat,
        limit: Int,
        startFrom: Long?,
    ): List<Message> =
        dbQuery {
            MessageEntity.find { MessageTable.idChat eq chat.id }
                .orderBy(MessageTable.id to SortOrder.DESC).limit(100)
                .map(MessageEntity::toMessage)
        }

    override suspend fun sendMessage(
        user: User,
        chat: IChat,
        message: MessageForm,
    ): Message =
        dbQuery {
            MessageEntity.new {
                chatEntity = ChatEntity[chat.id]
                this.message = message.message
                account = AccountEntity.find { AccountTable.username eq user.username }.single()
            }.toMessage()
        }
}
