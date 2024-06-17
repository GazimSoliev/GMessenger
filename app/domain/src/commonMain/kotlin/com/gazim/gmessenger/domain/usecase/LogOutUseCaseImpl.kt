package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService
import com.gazim.gmessenger.domain.service.SessionService

class LogOutUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
    private val sessionService: SessionService,
) : LogOutUseCase {
    override suspend fun invoke() {
        gMessengerSessionService.closeCurrentSession()
        sessionService.clearSession()
    }
}
