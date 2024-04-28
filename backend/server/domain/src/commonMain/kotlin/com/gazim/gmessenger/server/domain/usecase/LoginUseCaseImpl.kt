package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.service.IAuthorizationService
import java.time.LocalDateTime

class LoginUseCaseImpl(private val authorizationService: IAuthorizationService) : LoginUseCase {
    override suspend fun invoke(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ) = authorizationService.login(loginPassword, createdAt, expiredAt)
}
