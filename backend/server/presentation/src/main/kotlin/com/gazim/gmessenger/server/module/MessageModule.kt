package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.ChatEntity
import com.gazim.gmessenger.server.database.model.MessageEntity
import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.database.table.MessageTable
import com.gazim.gmessenger.server.extensions.toUser
import com.gazim.gmessenger.server.model.*
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.SortOrder

class MessageModule : IMessageModule {
    override suspend fun getMessages(
        chat: IChat,
        page: Int,
    ): Page<IMessage> =
        dbQuery {
            Page(
                next = null,
                previous = null,
                list =
                    MessageEntity.find { MessageTable.idChat eq chat.identifier.toInt() }
                        .orderBy(MessageTable.id to SortOrder.DESC).limit(100).map {
                            Message(
                                message = it.message,
                                sentAt = it.sentAt.toKotlinLocalDateTime(),
                                user = it.account.toUser(),
                            )
                        },
            )
        }

    override suspend fun sendMessage(
        chat: IChat,
        message: IMessage,
    ): Boolean =
        dbQuery {
            MessageEntity.new {
                chatEntity = ChatEntity[chat.identifier.toInt()]
                this.message = message.message
                sentAt = message.sentAt.toJavaLocalDateTime()
                account = AccountEntity.find { AccountTable.username eq message.user.username }.single()
            }
            true
        }
}
