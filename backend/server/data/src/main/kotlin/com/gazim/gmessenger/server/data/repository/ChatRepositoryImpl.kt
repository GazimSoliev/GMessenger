package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatAccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.data.mapper.toChat
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.ChatRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
class ChatRepositoryImpl : ChatRepository {
    override suspend fun getMembers(chatId: Uuid): List<User> {
        val chatEntity = ChatEntity[chatId]
        val members = chatEntity.members
        return members.map { accountEntity -> accountEntity.toUser() }
    }

    override suspend fun createChat(
        title: String,
        createdAt: Instant,
    ): IChat? {
        val chatEntity =
            ChatEntity.new {
                this.title = title
                this.createdAt = createdAt.toLocalDateTime(TimeZone.UTC)
            }
        return chatEntity.toChat(lastMessage = null)
    }

    override suspend fun getChat(chatId: Uuid): IChat? {
        val chatEntity = ChatEntity[chatId]
        val lastMessage = chatEntity.getLastMessage()
        return chatEntity.toChat(lastMessage = lastMessage)
    }

    override suspend fun getChatsByUser(
        userId: Uuid,
        size: Int,
        page: Int,
    ): List<IChat> {
        val accountEntity = AccountEntity[userId]
        val chats =
            accountEntity.chats.map { chatEntity ->
                val lastMessage = chatEntity.getLastMessage()
                chatEntity.toChat(currentUser = accountEntity, lastMessage = lastMessage)
            }
        return chats
    }

    override suspend fun existInChat(
        userId: Uuid,
        chatId: Uuid,
    ): Boolean {
        val chatEntities =
            ChatAccountEntity.find {
                (ChatAccountTable.idAccount eq userId.toJavaUuid()) and
                    (ChatAccountTable.idChat eq chatId.toJavaUuid())
            }
        return chatEntities.count() == 1L
    }

    override suspend fun addUserInChat(
        userId: Uuid,
        chatId: Uuid,
    ) {
        val userEntity = AccountEntity[userId]
        val chatEntity = ChatEntity[chatId]
        ChatAccountEntity.new {
            this.account = userEntity
            this.chatEntity = chatEntity
        }
    }

    private fun ChatEntity.getLastMessage(): MessageEntity? =
        MessageEntity
            .find { MessageTable.idChat eq this@getLastMessage.id }
            .orderBy(MessageTable.createdAt to SortOrder.DESC)
            .firstOrNull()
}
