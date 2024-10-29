package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.IChatService
import java.util.*

class GetChatsUseCaseImpl(
    private val chatService: IChatService,
) : GetChatsUseCase {
    override suspend fun invoke(userId: UUID): List<IChat> = chatService.getChats(userId, 100, 0)
}
