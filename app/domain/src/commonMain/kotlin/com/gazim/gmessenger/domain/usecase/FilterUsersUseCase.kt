package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.service.IGMessengerService

class FilterUsersUseCase(private val gMessengerRepository: IGMessengerService) : IFilterUsersUseCase {
    override suspend fun invoke(query: String): List<IUserModel> = gMessengerRepository.filterUsers(query)
}
