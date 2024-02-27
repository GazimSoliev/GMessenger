package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IAccountModel
import com.gazim.gmessenger.domain.service.IGMessengerAuthService

class OnRegisterUseCase(private val gMessengerAuthRepository: IGMessengerAuthService) : IOnRegisterUseCase {
    override suspend fun invoke(account: IAccountModel): Boolean = gMessengerAuthRepository.register(account)
}
