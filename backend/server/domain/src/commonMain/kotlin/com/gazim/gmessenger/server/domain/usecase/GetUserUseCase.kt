package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IUserService

class GetUserUseCase(
    private val userService: IUserService
) : IGetUserUseCase {
    override suspend fun invoke(tokenId: Int): IUser =
        userService.getUser(tokenId)
}