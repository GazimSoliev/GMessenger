package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.GMessengerService

class FilterUsersUseCaseImpl(private val gMessengerRepository: GMessengerService) : FilterUsersUseCase {
    override suspend fun invoke(query: String): List<IUserModel> = gMessengerRepository.filterUsers(query)
}
