package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IMessagingService

class GetMessagesUseCaseImpl(
    private val messagingService: IMessagingService,
) : GetMessagesUseCase {
    override suspend fun invoke(
        user: User,
        chat: IChat,
        key: MessagePageKey?,
    ): MessagePage = messagingService.getMessages(user, chat, key)
}
