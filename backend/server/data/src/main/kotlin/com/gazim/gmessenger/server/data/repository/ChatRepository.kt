package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatAccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.mapper.toAccountEntity
import com.gazim.gmessenger.server.data.mapper.toChat
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import java.util.*

class ChatRepository : IChatRepository {
    override suspend fun getChats(user: User): List<IChat> =
        dbQuery {
            val accountEntity = user.toAccountEntity()
            accountEntity.chats.map { it.toChat(accountEntity) }
        }

    override suspend fun getMembers(
        user: User,
        chat: IChat,
    ): List<User> =
        dbQuery {
            getEntityChat(user, chat.id)?.members?.map(AccountEntity::toUser) ?: emptyList()
        }

    override suspend fun createChat(users: List<User>): IChat? =
        dbQuery {
            val accounts =
                AccountEntity.find {
                    AccountTable.username.inList(users.map(User::username))
                }
            if (accounts.count().toInt() != users.count()) return@dbQuery null
            val chat =
                ChatEntity.new {
                    title = users.joinToString(transform = User::nickname)
                }
            accounts.forEach {
                ChatAccountEntity.new {
                    account = it
                    chatEntity = chat
                }
            }
            return@dbQuery chat.toChat()
        }

    override suspend fun getChat(
        user: User,
        id: UUID,
    ): IChat? =
        dbQuery {
            val account = user.toAccountEntity()
            getEntityChat(user, id)?.toChat(account)
        }

    override suspend fun existInChat(
        user: User,
        chat: IChat,
    ): Boolean =
        dbQuery {
            !getChatAccountEntity(user, chat.id).empty()
        }

    private fun getEntityChat(
        user: User,
        id: UUID,
    ): ChatEntity? = getChatAccountEntity(user, id).singleOrNull()?.chatEntity

    private fun getChatAccountEntity(
        user: User,
        id: UUID,
    ): SizedIterable<ChatAccountEntity> {
        val account = user.toAccountEntity()
        return ChatAccountEntity.find {
            (ChatAccountTable.idAccount eq account.id) and (ChatAccountTable.idChat eq id)
        }
    }
}
