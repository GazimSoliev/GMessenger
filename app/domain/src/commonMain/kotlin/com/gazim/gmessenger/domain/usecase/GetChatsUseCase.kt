package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class GetChatsUseCase(private val gMessengerRepository: IGMessengerService) : IGetChatsUseCase {
    override suspend fun invoke(): List<IChatModel> = gMessengerRepository.getChats()
}
