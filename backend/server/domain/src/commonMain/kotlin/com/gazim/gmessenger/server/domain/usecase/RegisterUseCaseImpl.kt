package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.service.IAuthorizationService

class RegisterUseCaseImpl(
    private val authorizationService: IAuthorizationService,
) : RegisterUseCase {
    override suspend fun invoke(account: RegistrationForm): Boolean = authorizationService.register(account)
}
