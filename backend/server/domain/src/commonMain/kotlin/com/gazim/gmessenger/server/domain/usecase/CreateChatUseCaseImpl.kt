package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.ChatService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class CreateChatUseCaseImpl(
    private val chatService: ChatService,
) : CreateChatUseCase {
    override suspend fun invoke(
        ownerId: Uuid,
        userIds: List<Uuid>,
    ): IChat = chatService.createChat(userIds + ownerId)
}
