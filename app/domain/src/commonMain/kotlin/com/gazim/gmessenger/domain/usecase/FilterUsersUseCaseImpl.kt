package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.service.GMessengerService

class FilterUsersUseCaseImpl(private val gMessengerRepository: GMessengerService) : FilterUsersUseCase {
    override suspend fun invoke(query: String): List<User> = gMessengerRepository.filterUsers(query)
}
