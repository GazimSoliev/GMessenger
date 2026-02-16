package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class GetUserUseCaseImpl(
    private val userService: UserService,
) : GetUserUseCase {
    override suspend fun invoke(tokenId: Uuid): User = userService.getUser(tokenId)
}
