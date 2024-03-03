package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import java.util.*

class UserService(
    private val userRepository: IUserRepository,
) : IUserService {
    override suspend fun findUser(username: String): List<User> = userRepository.findByUsername(username, 50)

    override suspend fun getUser(tokenId: UUID): User = userRepository.getUser(tokenId)
}
