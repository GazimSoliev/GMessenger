package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.MessagePage
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.service.GMessengerService

class GetMessagesUseCaseImpl(
    private val gMessengerService: GMessengerService,
) : GetMessagesUseCase {
    override suspend fun invoke(
        chat: IChatModel,
        key: MessagePageKey?,
    ): MessagePage = gMessengerService.getMessages(chat, key)
}
