package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.UserService

public class FindUserUseCaseImpl(
    private val userService: UserService,
) : FindUserUseCase {
    override suspend fun invoke(username: String): List<User> = userService.findUser(username)
}
