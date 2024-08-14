package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatAccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.mapper.toChat
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class ChatRepository : IChatRepository {
    override suspend fun getChats(userId: UUID): List<IChat> =
        dbQuery {
            val accountEntity = AccountEntity[userId]
            accountEntity.chats.map { it.toChat(accountEntity) }
        }

    override suspend fun getMembers(
        userId: UUID,
        chatId: UUID,
    ): List<User> =
        dbQuery {
            getEntityChat(
                userId = userId,
                chatId = chatId,
            )?.members?.map(AccountEntity::toUser) ?: emptyList()
        }

    override suspend fun createChat(userIds: List<UUID>): IChat? =
        dbQuery {
            val accounts =
                AccountEntity.find {
                    AccountTable.id.inList(userIds)
                }
            if (accounts.count().toInt() != userIds.count()) return@dbQuery null
            val chat =
                ChatEntity.new {
                    title = userIds.joinToString { AccountEntity[it].username }
                    createdAt = LocalDateTime.now(ZoneOffset.UTC)
                }
            accounts.forEach {
                ChatAccountEntity.new {
                    account = it
                    chatEntity = chat
                    createdAt = LocalDateTime.now(ZoneOffset.UTC)
                }
            }
            return@dbQuery chat.toChat()
        }

    override suspend fun getChat(
        userId: UUID,
        chatId: UUID,
    ): IChat? =
        dbQuery {
            val account = AccountEntity[userId]
            getEntityChat(
                userId = userId,
                chatId = chatId,
            )?.toChat(account)
        }

    override suspend fun existInChat(
        userId: UUID,
        chatId: UUID,
    ): Boolean =
        dbQuery {
            !getChatAccountEntity(
                userId = userId,
                chatId = chatId,
            ).empty()
        }

    private fun getEntityChat(
        userId: UUID,
        chatId: UUID,
    ): ChatEntity? =
        getChatAccountEntity(
            userId = userId,
            chatId = chatId,
        ).singleOrNull()?.chatEntity

    private fun getChatAccountEntity(
        userId: UUID,
        chatId: UUID,
    ): SizedIterable<ChatAccountEntity> {
        val account = AccountEntity[userId]
        return ChatAccountEntity.find {
            (ChatAccountTable.idAccount eq account.id) and (ChatAccountTable.idChat eq chatId)
        }
    }
}
