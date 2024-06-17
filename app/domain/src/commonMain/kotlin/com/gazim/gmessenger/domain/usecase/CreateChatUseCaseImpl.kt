package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class CreateChatUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : CreateChatUseCase {
    override suspend fun invoke(user: User) {
        gMessengerSessionService.createChat(user)
    }
}
