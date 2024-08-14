package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.service.IMessagingService
import java.util.*

class SendMessageUseCaseImpl(
    private val messagingService: IMessagingService,
) : SendMessageUseCase {
    override suspend fun invoke(
        userId: UUID,
        chatId: UUID,
        message: MessageForm,
    ) = messagingService.sendMessage(
        userId = userId,
        chatId = chatId,
        messageForm = message,
    )
}
