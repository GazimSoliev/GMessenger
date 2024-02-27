package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class GetOwnAccountUseCase(private val gMessengerRepository: IGMessengerService) : IGetOwnAccountUseCase {
    override suspend fun invoke(): IUserModel = gMessengerRepository.getMyOwnAccount()
}
