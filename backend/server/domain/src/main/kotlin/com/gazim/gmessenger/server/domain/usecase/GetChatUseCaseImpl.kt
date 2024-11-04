package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.IChatService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetChatUseCaseImpl(
    private val chatService: IChatService,
) : GetChatUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
    ): IChat? = chatService.getChat(userId, chatId)
}
