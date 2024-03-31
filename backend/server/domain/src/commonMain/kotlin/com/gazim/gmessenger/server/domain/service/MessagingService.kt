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
        user: User,
        chat: IChat,
        messageForm: MessageForm,
    ) {
        val message = messageRepository.sendMessage(user, chat, messageForm)
        chatsFlow[chat.id]?.emit(message)
    }

    override suspend fun getMessages(
        user: User,
        chat: IChat,
        key: MessagePageKey?,
    ): MessagePage {
        if (chatRepository.existInChat(user, chat)) return MessagePage(emptyList())
        if (!chatRepository.existInChat(user, chat)) return MessagePage(emptyList())
        val start: LocalDateTime
        val end: LocalDateTime?
        if (key != null) {
            start = key.start
            end = key.end
        } else {
            start = LocalDateTime.now(ZoneOffset.UTC)
            end = messageRepository.nextPage(chat, 63, start)
        }
        val prev = messageRepository.prevPage(chat, 64, start)
        if (end == null) {
            return MessagePage(
                data = emptyList(),
                prev = prev?.let { MessagePageKey(start = it, start) },
            )
        }
        val next = messageRepository.nextPage(chat, 64, end)
        val messages = messageRepository.getMessages(chat, start, end)
        return MessagePage(
            data = messages,
            next = next?.let { MessagePageKey(end, it) },
            prev = prev?.let { MessagePageKey(start = it, start) },
        )
    }

    override suspend fun getMessageFlow(
        user: User,
        chat: IChat,
    ): Flow<Message>? {
        if (!chatRepository.existInChat(user, chat)) return null
        val flow = chatsFlow[chat.id] ?: MutableSharedFlow<Message>().also { chatsFlow[chat.id] = it }
        return flow.asSharedFlow()
    }
}
