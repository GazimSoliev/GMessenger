package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetOwnAccountUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetOwnAccountUseCase {
    override suspend fun invoke() =
        runCatching {
            gMessengerSessionService.getMyOwnAccount()
        }
}
