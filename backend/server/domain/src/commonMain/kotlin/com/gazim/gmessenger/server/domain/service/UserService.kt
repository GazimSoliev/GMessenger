package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.FileRepository
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import java.util.*

class UserService(
    private val userRepository: IUserRepository,
    private val fileRepository: FileRepository,
) : IUserService {
    override suspend fun findUser(username: String): List<User> = userRepository.findByUsername(username, 50)

    override suspend fun getUser(tokenId: UUID): User = userRepository.getUser(tokenId)

    override suspend fun editProfile(
        user: User,
        profileForm: ProfileForm,
    ) = userRepository.editProfile(user, profileForm)

    override suspend fun uploadProfilePhoto(user: User, type: String, content: ByteArray): Image {
        val image = fileRepository.uploadImage(user, type, content)
        userRepository.setProfilePhoto(user, image)
        return image
    }
}
