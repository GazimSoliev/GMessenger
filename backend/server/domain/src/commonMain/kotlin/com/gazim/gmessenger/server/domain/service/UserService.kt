package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.repository.IUserRepository

class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    override suspend fun findUser(username: String): List<IUser> =
        userRepository.findByUsername(username, 50)

    override suspend fun getUser(idToken: Int): IUser =
        userRepository.getUser(idToken)
}