package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IUserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetUserUseCaseImpl(
    private val userService: IUserService,
) : GetUserUseCase {
    override suspend fun invoke(tokenId: Uuid): User = userService.getUser(tokenId)
}
