package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.RegistrationForm
import com.gazim.gmessenger.domain.service.GMessengerAuthSessionService

class RegisterUseCaseImpl(
    private val gMessengerAuthSessionService: GMessengerAuthSessionService,
) : RegisterUseCase {
    override suspend fun invoke(account: RegistrationForm) = runCatching { gMessengerAuthSessionService.register(account) }
}
