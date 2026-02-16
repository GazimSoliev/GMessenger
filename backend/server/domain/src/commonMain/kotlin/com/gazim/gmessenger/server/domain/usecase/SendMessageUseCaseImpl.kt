package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.service.MessagingService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class SendMessageUseCaseImpl(
    private val messagingService: MessagingService,
) : SendMessageUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
        message: String,
    ) {
        messagingService.sendMessage(
            userId = userId,
            chatId = chatId,
            message = message,
        )
    }
}
