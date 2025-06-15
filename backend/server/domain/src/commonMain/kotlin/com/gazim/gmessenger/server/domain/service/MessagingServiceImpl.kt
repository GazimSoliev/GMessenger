@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.repository.ChatRepository
import com.gazim.gmessenger.server.domain.repository.DatabaseTransaction
import com.gazim.gmessenger.server.domain.repository.MessageRepository
import com.gazim.gmessenger.server.domain.repository.invoke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MessagingServiceImpl(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val databaseTransaction: DatabaseTransaction,
) : MessagingService {
    private val chatsFlow = mutableMapOf<Uuid, MutableSharedFlow<Message>>()

    override suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        messageForm: MessageForm,
    ) {
        val sentAt = Clock.System.now()
        val message =
            databaseTransaction {
                messageRepository.sendMessage(
                    userId = userId,
                    chatId = chatId,
                    message = messageForm.message,
                    sentAt = sentAt,
                )
            }
        chatsFlow[chatId]?.emit(message)
    }

    override suspend fun getMessages(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage =
        databaseTransaction {
            val existInChat =
                chatRepository.existInChat(
                    userId = userId,
                    chatId = chatId,
                )
            if (!existInChat) return@databaseTransaction MessagePage(emptyList())

            val start: Instant
            val end: Instant?
            if (key != null) {
                start = key.start
                end = key.end
            } else {
                start = Clock.System.now()
                end = messageRepository.nextPage(chatId, 63, start)
            }
            val prev = messageRepository.prevPage(chatId, 64, start)
            if (end == null) {
                return@databaseTransaction MessagePage(
                    data = emptyList(),
                    prev = prev?.let { MessagePageKey(start = it, start) },
                )
            }
            val next = messageRepository.nextPage(chatId, 64, end)
            val messages = messageRepository.getMessages(chatId, start, end)
            MessagePage(
                data = messages,
                next = next?.let { MessagePageKey(end, it) },
                prev = prev?.let { MessagePageKey(start = it, start) },
            )
        }

    override suspend fun getMessageFlow(
        userId: Uuid,
        chatId: Uuid,
    ): Flow<Message>? {
        val existInChat =
            databaseTransaction {
                chatRepository.existInChat(
                    userId = userId,
                    chatId = chatId,
                )
            }
        if (!existInChat) return null
        val flow = chatsFlow[chatId] ?: MutableSharedFlow<Message>().also { chatsFlow[chatId] = it }
        return flow.asSharedFlow()
    }
}
