package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.ISentMessage
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IMessagingService

class SendMessageUseCase(private val messagingService: IMessagingService) : ISendMessageUseCase {
    override suspend fun invoke(
        user: IUser,
        chat: IChat,
        message: ISentMessage,
    ) {
        messagingService.sendMessage(user, chat, message)
    }
}
