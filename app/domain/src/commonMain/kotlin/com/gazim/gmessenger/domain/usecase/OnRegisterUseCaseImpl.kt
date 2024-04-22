package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.AccountModel
import com.gazim.gmessenger.domain.service.GMessengerAuthService

class OnRegisterUseCaseImpl(private val gMessengerAuthRepository: GMessengerAuthService) : OnRegisterUseCase {
    override suspend fun invoke(account: AccountModel): Boolean = gMessengerAuthRepository.register(account)
}
