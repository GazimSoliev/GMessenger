package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.service.IUserService
import java.util.*

class GetUserUseCaseImpl(
    private val userService: IUserService,
) : GetUserUseCase {
    override suspend fun invoke(tokenId: UUID) = userService.getUser(tokenId)
}
