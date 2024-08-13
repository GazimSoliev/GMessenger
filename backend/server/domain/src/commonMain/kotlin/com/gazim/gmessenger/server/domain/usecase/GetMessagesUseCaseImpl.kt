package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.service.IMessagingService
import java.util.*

class GetMessagesUseCaseImpl(
    private val messagingService: IMessagingService,
) : GetMessagesUseCase {
    override suspend fun invoke(userId: UUID, chatId: UUID, key: MessagePageKey?): MessagePage = messagingService.getMessages(userId, chatId, key)
}
