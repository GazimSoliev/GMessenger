package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.IChatService

class OpenChatUseCase(private val chatRepository: IChatService) : IOpenChatUseCase {
    override suspend fun invoke() = chatRepository.openConnection()
}
