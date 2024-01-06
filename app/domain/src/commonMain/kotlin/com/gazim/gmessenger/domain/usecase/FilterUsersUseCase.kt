package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class FilterUsersUseCase(private val gMessengerRepository: IGMessengerRepository) : IFilterUsersUseCase {
    override suspend fun invoke(query: String): List<IUserModel> = gMessengerRepository.filterUsers(query)
}
