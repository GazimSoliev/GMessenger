package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class GetChatsUseCase(private val gMessengerRepository: IGMessengerRepository) : IGetChatsUseCase {
    override suspend fun invoke(): List<IChatModel> = gMessengerRepository.getChats()
}
