package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.repository.ILoginRegisterRepository
import kotlinx.datetime.LocalDateTime

class AuthorizationService(
    private val loginPasswordRepository: ILoginRegisterRepository,
) : IAuthorizationService {
    override suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token? = loginPasswordRepository.login(loginPassword, createdAt, expiredAt)

    override suspend fun register(account: RegistrationForm): Boolean = loginPasswordRepository.register(account)
}
