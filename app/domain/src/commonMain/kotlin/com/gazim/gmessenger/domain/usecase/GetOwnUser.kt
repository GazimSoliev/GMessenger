package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class GetOwnUser(private val repository: IGMessengerService) : IGetOwnUser {
    override suspend fun invoke(): IUserModel = repository.getMyOwnAccount()
}
