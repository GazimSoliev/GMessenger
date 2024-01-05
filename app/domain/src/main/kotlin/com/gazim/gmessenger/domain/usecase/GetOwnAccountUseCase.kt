package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class GetOwnAccountUseCase(private val gMessengerRepository: IGMessengerRepository) : IGetOwnAccountUseCase {
    override suspend fun invoke(): IUserModel = gMessengerRepository.getMyOwnAccount()
}
