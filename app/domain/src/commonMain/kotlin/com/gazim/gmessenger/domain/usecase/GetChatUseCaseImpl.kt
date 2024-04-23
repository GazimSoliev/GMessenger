package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.service.GMessengerService

class GetChatUseCaseImpl(
    private val gMessengerService: GMessengerService,
) : GetChatUseCase {
    override suspend fun invoke(chat: IChat): IChatWebSocketModel = gMessengerService.getChat(chat)
}
