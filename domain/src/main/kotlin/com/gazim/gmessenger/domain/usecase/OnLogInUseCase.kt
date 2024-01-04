package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ILoginPasswordModel
import com.gazim.gmessenger.domain.repository.IGMessengerAuthRepository
import com.gazim.gmessenger.domain.repository.ISessionRepository

class OnLogInUseCase(
    private val gMessengerAuthRepository: IGMessengerAuthRepository,
    private val sessionRepository: ISessionRepository,
) : IOnLogInUseCase {
    override suspend fun invoke(loginPassword: ILoginPasswordModel): Boolean {
        val token = gMessengerAuthRepository.login(loginPassword)
        if (token == "null" || token.isEmpty()) return false
        sessionRepository.token = token
        return true
    }
}
