package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IChatService

class GetChatsUseCaseImpl(
    private val chatService: IChatService,
) : GetChatsUseCase {
    override suspend fun invoke(user: User): List<IChat> = chatService.getChats(user, 100, 0)
}
