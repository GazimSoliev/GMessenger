package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService

class FilterUsersUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : FilterUsersUseCase {
    override suspend fun invoke(query: String) =
        runCatching {
            gMessengerSessionService.filterUsers(query)
        }
}
