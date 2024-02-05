package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IChatService

class CreateChatUseCase(
    private val chatService: IChatService
) : ICreateChatUseCase {
    override suspend fun invoke(owner: IUser, users: List<IUser>): IChat? =
        chatService.createChat(users + owner)
}