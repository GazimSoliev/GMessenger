@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.PasswordEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.domain.repository.PasswordRepository
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PasswordRepositoryImpl : PasswordRepository {
    override suspend fun insert(
        password: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    ) {
        PasswordEntity.new {
            this.password = password
            this.account = AccountEntity[userId]
            this.createdAt = createdAt
        }
    }
}
