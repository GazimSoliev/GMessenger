package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.service.MessagingService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SendMessageUseCaseImpl(
    private val messagingService: MessagingService,
) : SendMessageUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
        message: MessageForm,
    ) = messagingService.sendMessage(
        userId = userId,
        chatId = chatId,
        messageForm = message,
    )
}
