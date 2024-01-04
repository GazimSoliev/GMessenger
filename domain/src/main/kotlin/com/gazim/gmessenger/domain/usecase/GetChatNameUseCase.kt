package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.IChatRepository

class GetChatNameUseCase(private val chatRepository: IChatRepository) : IGetChatNameUseCase {
    override suspend fun invoke(): String = chatRepository.getChatName()
}
