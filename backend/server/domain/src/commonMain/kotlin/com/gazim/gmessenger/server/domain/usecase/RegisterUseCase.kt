package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IAccount
import com.gazim.gmessenger.server.domain.service.IAuthorizationService

class RegisterUseCase(
    private val authorizationService: IAuthorizationService,
) : IRegisterUseCase {
    override suspend fun invoke(account: IAccount): Boolean = authorizationService.register(account)
}
