package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IChatService

class GetChatsUseCase(
    private val chatService: IChatService,
) : IGetChatsUseCase {
    override suspend fun invoke(user: IUser): List<IChat> = chatService.getChats(user, 100, 0)
}
