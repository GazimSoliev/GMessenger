package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.mapper.toMessage
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IMessage
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.SortOrder

class MessageRepository : IMessageRepository {
    override suspend fun getMessages(
        user: IUser,
        chat: IChat,
        limit: Int,
        startFrom: Long?
    ): List<IMessage> =
        dbQuery {
            MessageEntity.find { MessageTable.idChat eq chat.identifier.toInt() }
                .orderBy(MessageTable.id to SortOrder.DESC).limit(100)
                .map(MessageEntity::toMessage)
        }

    override suspend fun sendMessage(user: IUser, chat: IChat, message: IMessage): Boolean {
        dbQuery {
            MessageEntity.new {
                chatEntity = ChatEntity[chat.identifier.toInt()]
                this.message = message.message
                sentAt = message.sentAt.toJavaLocalDateTime()
                account = AccountEntity.find { AccountTable.username eq message.user.username }.single()
            }
        }
        return true
    }
}