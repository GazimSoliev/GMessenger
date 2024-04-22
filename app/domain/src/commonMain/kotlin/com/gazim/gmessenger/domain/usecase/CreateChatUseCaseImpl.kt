package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.GMessengerService

class CreateChatUseCaseImpl(private val gMessengerRepository: GMessengerService) : CreateChatUseCase {
    override suspend fun invoke(user: IUserModel) {
        gMessengerRepository.createChat(user)
    }
}
