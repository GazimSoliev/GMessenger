package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.service.IMessagingService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetMessagesUseCaseImpl(
    private val messagingService: IMessagingService,
) : GetMessagesUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage = messagingService.getMessages(userId, chatId, key)
}
