package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetChatsUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetChatsUseCase {
    override suspend fun invoke() =
        runCatching {
            gMessengerSessionService.getChats()
        }
}
