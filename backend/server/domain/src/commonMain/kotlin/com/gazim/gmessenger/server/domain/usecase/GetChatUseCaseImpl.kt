package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IChatService
import java.util.*

class GetChatUseCaseImpl(
    private val chatService: IChatService,
) : GetChatUseCase {
    override suspend fun invoke(
        user: User,
        chatId: UUID,
    ): IChat? = chatService.getChat(user, chatId)
}
