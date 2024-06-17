package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class FilterUsersUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : FilterUsersUseCase {
    override suspend fun invoke(query: String): List<User> = gMessengerSessionService.filterUsers(query)
}
