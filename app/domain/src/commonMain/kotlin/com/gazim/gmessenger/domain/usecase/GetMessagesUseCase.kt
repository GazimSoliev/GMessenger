package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.repository.IChatRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val chatRepository: IChatRepository) : IGetMessagesUseCase {
    override suspend fun invoke(): Flow<IMessageModel> = chatRepository.messages
}
