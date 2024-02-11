package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IChatService

class GetChatUseCase(
    private val chatService: IChatService,
) : IGetChatUseCase {
    override suspend fun invoke(
        user: IUser,
        chatId: Int,
    ): IChat? = chatService.getChat(user, chatId)
}
