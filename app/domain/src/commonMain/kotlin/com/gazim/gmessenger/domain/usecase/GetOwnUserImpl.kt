package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.service.GMessengerService

class GetOwnUserImpl(private val repository: GMessengerService) : GetOwnUser {
    override suspend fun invoke(): User = repository.getMyOwnAccount()
}
