package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IChatService
import java.util.*

class GetChatUseCase(
    private val chatService: IChatService,
) : IGetChatUseCase {
    override suspend fun invoke(
        user: User,
        chatId: UUID,
    ): IChat? = chatService.getChat(user, chatId)
}
