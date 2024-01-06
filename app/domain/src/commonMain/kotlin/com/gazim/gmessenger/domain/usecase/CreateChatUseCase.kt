package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class CreateChatUseCase(private val gMessengerRepository: IGMessengerRepository) : ICreateChatUseCase {
    override suspend fun invoke(user: IUserModel) {
        gMessengerRepository.createChat(user)
    }
}
