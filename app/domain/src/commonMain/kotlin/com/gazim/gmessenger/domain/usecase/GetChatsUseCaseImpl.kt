package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetChatsUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetChatsUseCase {
    override suspend fun invoke(): List<IChat> = gMessengerSessionService.getChats()
}
