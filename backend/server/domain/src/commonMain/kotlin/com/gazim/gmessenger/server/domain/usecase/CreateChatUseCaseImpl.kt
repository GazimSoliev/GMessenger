package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.IChatService
import java.util.*

class CreateChatUseCaseImpl(
    private val chatService: IChatService,
) : CreateChatUseCase {
    override suspend fun invoke(
        ownerId: UUID,
        userIds: List<UUID>,
    ): IChat? = chatService.createChat(userIds + ownerId)
}
