package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.service.MessagingService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetMessagesUseCaseImpl(
    private val messagingService: MessagingService,
) : GetMessagesUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage = messagingService.getMessages(userId, chatId, key)
}
