package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatAccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.model.MessageEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.MessageTable
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.domain.extensions.nowInUTC
import com.gazim.gmessenger.server.data.mapper.toChat
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
class ChatRepository : IChatRepository {
    override suspend fun getChats(userId: Uuid): List<IChat> =
        dbQuery {
            val accountEntity = AccountEntity[userId]
            accountEntity.chats.map { chatEntity ->
                val lastMessage = chatEntity.getLastMessage()
                chatEntity.toChat(currentUser = accountEntity, lastMessage = lastMessage)
            }
        }

    override suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User> =
        dbQuery {
            getEntityChat(
                userId = userId,
                chatId = chatId,
            )?.members?.map(AccountEntity::toUser) ?: emptyList()
        }

    override suspend fun createChat(userIds: List<Uuid>): IChat? =
        dbQuery {
            val accounts =
                AccountEntity.find {
                    AccountTable.id.inList(userIds.map { it.toJavaUuid() })
                }
            if (accounts.count().toInt() != userIds.count()) return@dbQuery null
            val chat =
                ChatEntity.new {
                    title = userIds.joinToString { AccountEntity[it].username }
                    createdAt = nowInUTC()
                }
            accounts.forEach {
                ChatAccountEntity.new {
                    account = it
                    chatEntity = chat
                    createdAt = nowInUTC()
                }
            }
            return@dbQuery chat.toChat(lastMessage = null)
        }

    override suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat? =
        dbQuery {
            val account = AccountEntity[userId.toJavaUuid()]
            val chatEntity = getEntityChat(
                userId = userId,
                chatId = chatId,
            )
            val lastMessage = chatEntity?.getLastMessage()
            chatEntity?.toChat(currentUser = account, lastMessage = lastMessage)
        }

    override suspend fun existInChat(
        userId: Uuid,
        chatId: Uuid,
    ): Boolean =
        dbQuery {
            !getChatAccountEntity(
                userId = userId,
                chatId = chatId,
            ).empty()
        }

    private fun ChatEntity.getLastMessage(

    ): MessageEntity? {
        return MessageEntity.find { MessageTable.idChat eq this@getLastMessage.id }
            .orderBy(MessageTable.createdAt to SortOrder.DESC).firstOrNull()
    }

    private fun getEntityChat(
        userId: Uuid,
        chatId: Uuid,
    ): ChatEntity? =
        getChatAccountEntity(
            userId = userId,
            chatId = chatId,
        ).singleOrNull()?.chatEntity

    private fun getChatAccountEntity(
        userId: Uuid,
        chatId: Uuid,
    ): SizedIterable<ChatAccountEntity> {
        val account = AccountEntity[userId.toJavaUuid()]
        return ChatAccountEntity.find {
            (ChatAccountTable.idAccount eq account.id) and (ChatAccountTable.idChat eq chatId.toJavaUuid())
        }
    }
}
