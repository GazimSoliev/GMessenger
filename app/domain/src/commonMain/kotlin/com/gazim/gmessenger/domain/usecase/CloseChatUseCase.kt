package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.IChatService

class CloseChatUseCase(private val chatRepository: IChatService) : ICloseChatUseCase {
    override suspend fun invoke() = chatRepository.close()
}
