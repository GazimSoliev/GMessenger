package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.service.GMessengerService

class GetChatsUseCaseImpl(private val gMessengerRepository: GMessengerService) : GetChatsUseCase {
    override suspend fun invoke(): List<IChat> = gMessengerRepository.getChats()
}
