package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ISentMessageModel
import com.gazim.gmessenger.domain.repository.IChatRepository

class SendMessageUseCase(private val chatRepository: IChatRepository) : ISendMessageUseCase {
    override suspend fun invoke(message: ISentMessageModel) = chatRepository.sendMessage(message)
}
