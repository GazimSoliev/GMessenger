package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.IChatRepository

class OpenChatUseCase(private val chatRepository: IChatRepository) : IOpenChatUseCase {
    override suspend fun invoke() = chatRepository.openConnection()
}
