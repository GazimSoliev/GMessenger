package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.IChatService

class GetChatNameUseCase(private val chatRepository: IChatService) : IGetChatNameUseCase {
    override suspend fun invoke(): String = chatRepository.getChatName()
}
