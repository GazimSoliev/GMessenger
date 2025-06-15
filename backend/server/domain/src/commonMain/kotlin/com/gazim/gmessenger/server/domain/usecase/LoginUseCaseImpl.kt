package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.service.AuthorizationService

class LoginUseCaseImpl(
    private val authorizationService: AuthorizationService,
) : LoginUseCase {
    override suspend fun invoke(loginPassword: AuthenticationForm) = authorizationService.login(loginPassword)
}
