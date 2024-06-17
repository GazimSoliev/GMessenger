package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.MessagePage
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetMessagesUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetMessagesUseCase {
    override suspend fun invoke(
        chat: IChat,
        key: MessagePageKey?,
    ): MessagePage = gMessengerSessionService.getMessages(chat, key)
}
