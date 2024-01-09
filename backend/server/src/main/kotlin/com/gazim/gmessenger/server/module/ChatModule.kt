package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.ChatAccountEntity
import com.gazim.gmessenger.server.database.model.ChatEntity
import com.gazim.gmessenger.server.database.model.toAccountEntity
import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.database.table.ChatAccountTable
import com.gazim.gmessenger.server.extensions.toChat
import com.gazim.gmessenger.server.extensions.toUser
import com.gazim.gmessenger.server.model.IChat
import com.gazim.gmessenger.server.model.IUser
import org.jetbrains.exposed.sql.and

class ChatModule : IChatModule {
    override suspend fun getChats(user: IUser): List<IChat> =
        dbQuery {
            val accountEntity = user.toAccountEntity() ?: return@dbQuery emptyList()
            accountEntity.chats.map {
                it.toChat(accountEntity)
            }
        }

    override suspend fun createChat(users: List<IUser>): Boolean =
        dbQuery {
            val accounts =
                AccountEntity.find {
                    AccountTable.username.inList(users.map(IUser::username))
                }
            if (accounts.count().toInt() != users.count()) return@dbQuery false
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
            return@dbQuery true
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

    override suspend fun getChat(
        user: IUser,
        chatId: Int,
    ): IChat? =
        dbQuery {
            getRawChat(user, chatId)?.toChat()
        }

    override suspend fun getChatMembers(
        user: IUser,
        chat: IChat,
    ): List<IUser> =
        dbQuery {
            getRawChat(user, chat.identifier.toInt())?.members?.map(AccountEntity::toUser) ?: emptyList()
        }
}
