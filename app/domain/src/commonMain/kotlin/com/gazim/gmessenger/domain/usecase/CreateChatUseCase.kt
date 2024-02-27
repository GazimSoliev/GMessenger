package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class CreateChatUseCase(private val gMessengerRepository: IGMessengerService) : ICreateChatUseCase {
    override suspend fun invoke(user: IUserModel) {
        gMessengerRepository.createChat(user)
    }
}
