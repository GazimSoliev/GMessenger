package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.service.GMessengerAuthSessionService
import com.gazim.gmessenger.domain.service.GMessengerSessionService
import com.gazim.gmessenger.domain.service.SessionService

class LogInUseCaseImpl(
    private val gMessengerAuthSessionService: GMessengerAuthSessionService,
    private val gMessengerSessionService: GMessengerSessionService,
    private val sessionService: SessionService,
) : LogInUseCase {
    override suspend fun invoke(loginPassword: AuthenticationForm) =
        runCatching {
            val token = gMessengerAuthSessionService.login(loginPassword)
            if (token == null || token == "null" || token.isEmpty()) return@runCatching false
            sessionService.setSession(token)
            gMessengerSessionService.createCurrentSession()
            true
        }
}
