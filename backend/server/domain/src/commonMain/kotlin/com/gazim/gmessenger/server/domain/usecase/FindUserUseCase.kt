package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IUserService

class FindUserUseCase(
    private val userService: IUserService,
) : IFindUserUseCase {
    override suspend fun invoke(username: String): List<IUser> = userService.findUser(username)
}
