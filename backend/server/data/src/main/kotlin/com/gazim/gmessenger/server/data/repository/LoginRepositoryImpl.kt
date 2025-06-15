package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.LoginEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.domain.repository.LoginRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LoginRepositoryImpl : LoginRepository {
    override suspend fun insert(
        login: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    ) {
        LoginEntity.new {
            this.login = login
            this.account = AccountEntity[userId]
            this.createdAt = createdAt.toLocalDateTime(TimeZone.UTC)
        }
    }
}
