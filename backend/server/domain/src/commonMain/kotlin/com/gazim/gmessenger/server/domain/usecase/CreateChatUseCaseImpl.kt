package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IChatService

class CreateChatUseCaseImpl(
    private val chatService: IChatService,
) : CreateChatUseCase {
    override suspend fun invoke(
        owner: User,
        users: List<User>,
    ): IChat? = chatService.createChat(users + owner)
}
