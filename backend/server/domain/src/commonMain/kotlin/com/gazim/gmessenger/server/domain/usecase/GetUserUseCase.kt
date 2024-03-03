package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.service.IUserService
import java.util.*

class GetUserUseCase(
    private val userService: IUserService,
) : IGetUserUseCase {
    override suspend fun invoke(tokenId: UUID) = userService.getUser(tokenId)
}
