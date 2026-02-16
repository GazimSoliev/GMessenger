package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.ChatService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class GetChatsUseCaseImpl(
    private val chatService: ChatService,
) : GetChatsUseCase {
    override suspend fun invoke(userId: Uuid): List<IChat> = chatService.getChats(userId, 100, 0)
}
