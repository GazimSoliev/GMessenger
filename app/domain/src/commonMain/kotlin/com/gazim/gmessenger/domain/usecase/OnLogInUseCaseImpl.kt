package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.service.GMessengerAuthService
import com.gazim.gmessenger.domain.service.SessionService

class OnLogInUseCaseImpl(
    private val gMessengerAuthRepository: GMessengerAuthService,
    private val sessionService: SessionService,
) : OnLogInUseCase {
    override suspend fun invoke(loginPassword: AuthenticationForm): Boolean {
        val token = gMessengerAuthRepository.login(loginPassword)
        if (token == null || token == "null" || token.isEmpty()) return false
        sessionService.setSession(token)
        return true
    }
}
