package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.service.IAuthorizationService
import java.time.LocalDateTime

class LoginUseCase(private val authorizationService: IAuthorizationService) : ILoginUseCase {
    override suspend fun invoke(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ) = authorizationService.login(loginPassword, createdAt, expiredAt)
}
