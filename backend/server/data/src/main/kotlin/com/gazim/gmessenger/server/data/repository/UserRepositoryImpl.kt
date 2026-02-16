@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.database.model.ProfilePhotoEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.LoginTable
import com.gazim.gmessenger.server.data.database.table.PasswordTable
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.UserRepository
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.select
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

public class UserRepositoryImpl : UserRepository {
    override suspend fun insertAndGetId(
        nickname: String,
        username: String,
        createdAt: Instant,
    ): Uuid {
        val accountEntity =
            AccountEntity.new {
                this.nickname = nickname
                this.username = username
                this.createdAt = createdAt
            }
        return accountEntity.id.value.toKotlinUuid()
    }

    override suspend fun checkUserExist(username: String): Boolean = AccountEntity.find { AccountTable.username eq username }.empty()

    override suspend fun findUserByLoginAndPassword(
        login: ByteArray,
        password: ByteArray,
    ): Uuid? = AccountTable
        .innerJoin(LoginTable)
        .innerJoin(PasswordTable)
        .select(AccountTable.id)
        .where {
            (AccountTable.id eq LoginTable.idAccount) and
                (AccountTable.id eq PasswordTable.idAccount) and
                (LoginTable.login eq login) and
                (PasswordTable.password eq password)
        }.singleOrNull()
        ?.let { resultRow ->
            resultRow[AccountTable.id].value.toKotlinUuid()
        }

    override suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User> = AccountEntity
        .find {
            AccountTable.username like "%$username%"
        }.limit(limit)
        .map(AccountEntity::toUser)

    override suspend fun editProfile(
        userId: Uuid,
        nickname: String,
        username: String,
    ) {
        val account = AccountEntity[userId]
        account.nickname = nickname
        account.username = username
    }

    override suspend fun setProfilePhoto(
        userId: Uuid,
        imageId: Uuid,
        createdAt: Instant,
    ) {
        val accountEntity = AccountEntity[userId]
        val imageEntity = ImageEntity[imageId]
        ProfilePhotoEntity.new {
            this.account = accountEntity
            this.image = imageEntity
            this.createdAt = createdAt
        }
    }

    override suspend fun getUserById(userId: Uuid): User = AccountEntity[userId].toUser()
}
