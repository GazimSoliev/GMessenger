package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class GetOwnUser(private val repository: IGMessengerRepository) : IGetOwnUser {
    override suspend fun invoke(): IUserModel = repository.getMyOwnAccount()
}
