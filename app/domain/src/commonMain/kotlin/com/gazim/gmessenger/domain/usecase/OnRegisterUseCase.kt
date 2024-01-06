package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IAccountModel
import com.gazim.gmessenger.domain.repository.IGMessengerAuthRepository

class OnRegisterUseCase(private val gMessengerAuthRepository: IGMessengerAuthRepository) : IOnRegisterUseCase {
    override suspend fun invoke(account: IAccountModel): Boolean = gMessengerAuthRepository.register(account)
}
