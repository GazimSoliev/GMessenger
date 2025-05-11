package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.FileRepository
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UserService(
    private val userRepository: IUserRepository,
    private val fileRepository: FileRepository,
) : IUserService {
    override suspend fun findUser(username: String): List<User> = userRepository.findByUsername(username, 50)

    override suspend fun getUser(tokenId: Uuid): User = userRepository.getUser(tokenId)

    override suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    ) = userRepository.editProfile(userId, profileForm)

    override suspend fun uploadProfilePhoto(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image {
        val image = fileRepository.uploadImage(userId, type, content)
        userRepository.setProfilePhoto(userId, image.id)
        return image
    }
}
