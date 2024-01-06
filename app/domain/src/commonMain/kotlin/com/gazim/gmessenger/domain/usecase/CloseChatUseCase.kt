package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.IChatRepository

class CloseChatUseCase(private val chatRepository: IChatRepository) : ICloseChatUseCase {
    override suspend fun invoke() = chatRepository.close()
}
