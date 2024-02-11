package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.ILoginPassword
import com.gazim.gmessenger.server.domain.service.IAuthorizationService
import kotlinx.datetime.LocalDateTime

class LoginUseCase(private val authorizationService: IAuthorizationService) : ILoginUseCase {
    override suspend fun invoke(
        loginPassword: ILoginPassword,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int? = authorizationService.login(loginPassword, createdAt, expiredAt)
}
