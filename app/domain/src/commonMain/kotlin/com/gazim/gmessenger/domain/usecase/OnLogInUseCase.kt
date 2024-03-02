package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ILoginPasswordModel
import com.gazim.gmessenger.domain.service.IGMessengerAuthService
import com.gazim.gmessenger.domain.service.ISessionService

class OnLogInUseCase(
    private val gMessengerAuthRepository: IGMessengerAuthService,
    private val sessionService: ISessionService,
) : IOnLogInUseCase {
    override suspend fun invoke(loginPassword: ILoginPasswordModel): Boolean {
        val token = gMessengerAuthRepository.login(loginPassword)
        if (token == "null" || token.isEmpty()) return false
        sessionService.setSession(token)
        return true
    }
}
