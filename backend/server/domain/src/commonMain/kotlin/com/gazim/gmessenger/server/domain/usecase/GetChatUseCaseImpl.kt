package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.ChatService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class GetChatUseCaseImpl(
    private val chatService: ChatService,
) : GetChatUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
    ): IChat = chatService.getChat(userId, chatId)
}
