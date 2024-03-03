package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IChatService

class GetChatsUseCase(
    private val chatService: IChatService,
) : IGetChatsUseCase {
    override suspend fun invoke(user: User): List<IChat> = chatService.getChats(user, 100, 0)
}
