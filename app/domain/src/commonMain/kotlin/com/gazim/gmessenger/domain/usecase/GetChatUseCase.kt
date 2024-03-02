package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class GetChatUseCase(
    private val gMessengerService: IGMessengerService,
) : IGetChatUseCase {
    override suspend fun invoke(chat: IChatModel): IChatWebSocketModel = gMessengerService.getChat(chat)
}
