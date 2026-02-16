package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.service.AuthorizationService

public class LoginUseCaseImpl(
    private val authorizationService: AuthorizationService,
) : LoginUseCase {
    override suspend fun invoke(loginPassword: AuthenticationForm): Token? = authorizationService.login(loginPassword)
}
