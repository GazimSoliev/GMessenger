package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetOwnUserImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetOwnUser {
    override suspend fun invoke() =
        runCatching {
            gMessengerSessionService.getMyOwnAccount()
        }
}
