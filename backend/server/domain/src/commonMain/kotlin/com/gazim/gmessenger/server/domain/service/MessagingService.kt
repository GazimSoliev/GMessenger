package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.extensions.nowInUTC
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MessagingService(
    private val messageRepository: IMessageRepository,
    private val chatRepository: IChatRepository,
) : IMessagingService {
    private val chatsFlow = mutableMapOf<Uuid, MutableSharedFlow<Message>>()

    override suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        messageForm: MessageForm,
    ) {
        val message =
            messageRepository.sendMessage(
                userId = userId,
                chatId = chatId,
                message = messageForm,
            )
        chatsFlow[chatId]?.emit(message)
    }

    override suspend fun getMessages(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage {
        if (!chatRepository.existInChat(userId, chatId)) return MessagePage(emptyList())
        val start: LocalDateTime
        val end: LocalDateTime?
        if (key != null) {
            start = key.start
            end = key.end
        } else {
            start = nowInUTC()
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
        userId: Uuid,
        chatId: Uuid,
    ): Flow<Message>? {
        if (
            !chatRepository.existInChat(
                userId = userId,
                chatId = chatId,
            )
        ) {
            return null
        }
        val flow = chatsFlow[chatId] ?: MutableSharedFlow<Message>().also { chatsFlow[chatId] = it }
        return flow.asSharedFlow()
    }
}
