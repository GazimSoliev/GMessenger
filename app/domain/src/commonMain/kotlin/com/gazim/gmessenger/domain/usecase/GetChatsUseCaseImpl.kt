package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.service.GMessengerService

class GetChatsUseCaseImpl(private val gMessengerRepository: GMessengerService) : GetChatsUseCase {
    override suspend fun invoke(): List<IChatModel> = gMessengerRepository.getChats()
}
