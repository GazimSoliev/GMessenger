package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class MessagingService(
    private val messageRepository: IMessageRepository,
    private val chatRepository: IChatRepository,
) : IMessagingService {
    private val chatsFlow = mutableMapOf<UUID, MutableSharedFlow<Message>>()

    override suspend fun sendMessage(
        userId: UUID,
        chatId: UUID,
        messageForm: MessageForm,
    ) {
        val message = messageRepository.sendMessage(
            userId = userId,
            chatId = chatId,
            message = messageForm
        )
        chatsFlow[chatId]?.emit(message)
    }

    override suspend fun getMessages(
        userId: UUID,
        chatId: UUID,
        key: MessagePageKey?
    ): MessagePage {
        if (!chatRepository.existInChat(userId, chatId)) return MessagePage(emptyList())
        val start: LocalDateTime
        val end: LocalDateTime?
        if (key != null) {
            start = key.start
            end = key.end
        } else {
            start = LocalDateTime.now(ZoneOffset.UTC)
            end = messageRepository.nextPage(chatId, 63, start)
        }
        val prev = messageRepository.prevPage(chatId, 64, start)
        if (end == null) {
            return MessagePage(
                data = emptyList(),
                prev = prev?.let { MessagePageKey(start = it, start) },
            )
        }
        val next = messageRepository.nextPage(chatId, 64, end)
        val messages = messageRepository.getMessages(chatId, start, end)
        return MessagePage(
            data = messages,
            next = next?.let { MessagePageKey(end, it) },
            prev = prev?.let { MessagePageKey(start = it, start) },
        )
    }

    override suspend fun getMessageFlow(
        userId: UUID,
        chatId: UUID
    ): Flow<Message>? {
        if (
            !chatRepository.existInChat(
                userId = userId,
                chatId = chatId

            )
        ) return null
        val flow = chatsFlow[chatId] ?: MutableSharedFlow<Message>().also { chatsFlow[chatId] = it }
        return flow.asSharedFlow()
    }
}
