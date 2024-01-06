package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.repository.IChatSessionRepository

class SendChatUseCase(private val chatSessionRepository: IChatSessionRepository) : ISendChatUseCase {
    override suspend fun invoke(chat: IChatModel) {
        chatSessionRepository.currentChat = chat
    }
}
