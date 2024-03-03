package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IMessagingService

class SendMessageUseCase(private val messagingService: IMessagingService) : ISendMessageUseCase {
    override suspend fun invoke(
        user: User,
        chat: IChat,
        message: MessageForm,
    ) = messagingService.sendMessage(user, chat, message)
}
