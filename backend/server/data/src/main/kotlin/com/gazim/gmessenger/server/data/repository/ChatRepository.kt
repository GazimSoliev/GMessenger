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
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import org.jetbrains.exposed.sql.and

class ChatRepository : IChatRepository {
    override suspend fun getChats(user: IUser): List<IChat> =
        dbQuery {
            val accountEntity = user.toAccountEntity() ?: return@dbQuery emptyList()
            accountEntity.chats.map { it.toChat(accountEntity) }
        }

    override suspend fun getMembers(user: IUser, chat: IChat): List<IUser> =
        dbQuery {
            getRawChat(user, chat.identifier.toInt())?.members?.map(AccountEntity::toUser) ?: emptyList()
        }

    override suspend fun createChat(users: List<IUser>): IChat? =
        dbQuery {
            val accounts =
                AccountEntity.find {
                    AccountTable.username.inList(users.map(IUser::username))
                }
            if (accounts.count().toInt() != users.count()) return@dbQuery null
            val chat =
                ChatEntity.new {
                    title = users.joinToString(transform = IUser::nickname)
                }
            accounts.forEach {
                ChatAccountEntity.new {
                    account = it
                    chatEntity = chat
                }
            }
            return@dbQuery chat.toChat()
        }

    override suspend fun getChat(user: IUser, chatId: Int): IChat? {
        val account = user.toAccountEntity() ?: return null
        return ChatAccountEntity.find {
            (ChatAccountTable.idAccount eq account.id) and (ChatAccountTable.idChat eq chatId)
        }.singleOrNull()?.chatEntity
    }


    private fun getRawChat(
        user: IUser,
        chatId: Int,
    ): ChatEntity? {
        val account = user.toAccountEntity() ?: return null
        return ChatAccountEntity.find {
            (ChatAccountTable.idAccount eq account.id) and (ChatAccountTable.idChat eq chatId)
        }.singleOrNull()?.chatEntity
    }
}