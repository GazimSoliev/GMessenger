package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ISentMessageModel
import com.gazim.gmessenger.domain.service.IChatService

class SendMessageUseCase(private val chatRepository: IChatService) : ISendMessageUseCase {
    override suspend fun invoke(message: ISentMessageModel) = chatRepository.sendMessage(message)
}
