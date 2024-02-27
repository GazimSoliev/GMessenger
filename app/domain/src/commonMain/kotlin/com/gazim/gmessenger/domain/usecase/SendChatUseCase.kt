package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.service.IChatSessionService

class SendChatUseCase(private val chatSessionRepository: IChatSessionService) : ISendChatUseCase {
    override suspend fun invoke(chat: IChatModel) {
        chatSessionRepository.currentChat = chat
    }
}
