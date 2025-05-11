@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.Token
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



interface UserRepository {
    suspend fun insertAndGetId(
        nickname: String,
        username: String,
        createdAt: Instant
    ) : Uuid

    suspend fun checkUserExist(username: String): Boolean

    suspend fun findUserByLoginAndPassword(
        login: ByteArray,
        password: ByteArray
    ): Uuid?
}

interface LoginRepository {
    suspend fun insert(
        login: ByteArray,
        userId: Uuid,
        createdAt: Instant
    )
}

interface PasswordRepository {
    suspend fun insert(
        password: ByteArray,
        userId: Uuid,
        createdAt: Instant
    )
}

interface TokenRepository {
    suspend fun insertAndGetToken(
        createdAt: Instant,
        expiredAt: Instant,
        userId: Uuid,
    ) : Token
}