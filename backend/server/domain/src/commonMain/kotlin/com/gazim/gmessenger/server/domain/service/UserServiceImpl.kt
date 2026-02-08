package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.repository.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalEncodingApi::class)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val fileRepository: FileRepository,
    private val tokenRepository: TokenRepository,
    private val transaction: DatabaseTransaction,
) : UserService {
    override suspend fun findUser(username: String) =
        transaction {
            userRepository.findByUsername(
                username = username,
                limit = 50,
            )
        }

    override suspend fun getUser(tokenId: Uuid) =
        transaction {
            val userId = tokenRepository.getUserId(tokenId)
            userRepository.getUserById(userId)
        }

    override suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    ) = transaction {
        userRepository.editProfile(
            userId = userId,
            nickname = profileForm.nickname,
            username = profileForm.username,
        )
    }

    override suspend fun uploadProfilePhoto(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ) = transaction {
        val base64Image = Base64.encode(content)
        val createdAt = Clock.System.now()
        val image =
            fileRepository.uploadAndGetImage(
                userId = userId,
                type = type,
                base64Image = base64Image,
                createdAt = createdAt,
            )
        userRepository.setProfilePhoto(
            userId = userId,
            imageId = image.id,
            createdAt = createdAt,
        )
        image
    }
}
