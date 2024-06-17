package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetChatUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetChatUseCase {
    override suspend fun invoke(chat: IChat): IChatWebSocketModel = gMessengerSessionService.getChat(chat)
}
