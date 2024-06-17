package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.RegistrationForm
import com.gazim.gmessenger.domain.service.GMessengerAuthService

class RegisterUseCaseImpl(private val gMessengerAuthRepository: GMessengerAuthService) : RegisterUseCase {
    override suspend fun invoke(account: RegistrationForm): Boolean = gMessengerAuthRepository.register(account)
}
