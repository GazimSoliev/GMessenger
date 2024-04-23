package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.service.GMessengerService

class GetOwnAccountUseCaseImpl(private val gMessengerRepository: GMessengerService) : GetOwnAccountUseCase {
    override suspend fun invoke(): User = gMessengerRepository.getMyOwnAccount()
}
