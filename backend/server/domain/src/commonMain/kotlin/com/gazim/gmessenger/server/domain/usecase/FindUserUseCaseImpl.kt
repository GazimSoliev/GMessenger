package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IUserService

class FindUserUseCaseImpl(
    private val userService: IUserService,
) : FindUserUseCase {
    override suspend fun invoke(username: String): List<User> = userService.findUser(username)
}
