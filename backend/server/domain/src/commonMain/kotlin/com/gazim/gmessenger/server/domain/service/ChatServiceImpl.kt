package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.ChatRepository
import com.gazim.gmessenger.server.domain.repository.DatabaseTransaction
import com.gazim.gmessenger.server.domain.repository.invoke
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ChatServiceImpl(
    private val chatRepository: ChatRepository,
    private val databaseTransaction: DatabaseTransaction,
) : ChatService {
    override suspend fun getChats(
        userId: Uuid,
        size: Int,
        page: Int,
    ): List<IChat> =
        databaseTransaction {
            chatRepository.getChatsByUser(
                userId = userId,
                size = size,
                page = page,
            )
        }

    override suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User> =
        databaseTransaction {
            val userExistInChat =
                chatRepository.existInChat(
                    userId = userId,
                    chatId = chatId,
                )
            if (userExistInChat) {
                chatRepository.getMembers(userId)
            } else {
                emptyList()
            }
        }

    override suspend fun createChat(userIds: List<Uuid>): IChat? {
        val createdAt = Clock.System.now()
        return databaseTransaction {
            val chat =
                chatRepository.createChat(
                    title = "",
                    createdAt = createdAt,
                )
            if (chat == null) return@databaseTransaction null
            userIds.forEach { userId ->
                chatRepository.addUserInChat(
                    userId = userId,
                    chatId = chat.id,
                    createdAt = createdAt,
                )
            }
            chat
        }
    }

    override suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat? =
        databaseTransaction {
            val existInChat = chatRepository.existInChat(userId, chatId)
            if (existInChat) return@databaseTransaction null
            chatRepository.getChat(chatId)
        }
}
