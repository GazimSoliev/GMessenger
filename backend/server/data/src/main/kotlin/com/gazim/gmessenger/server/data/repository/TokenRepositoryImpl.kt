package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.repository.TokenRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
class TokenRepositoryImpl : TokenRepository {
    override suspend fun insertAndGetToken(
        createdAt: Instant,
        expiredAt: Instant,
        userId: Uuid
    ): Token {
        val tokenEntity = TokenEntity.new {
            this.createdAt = createdAt.toLocalDateTime(TimeZone.UTC)
            this.expiredAt = expiredAt.toLocalDateTime(TimeZone.UTC)
            this.account = AccountEntity[userId]
        }
        return Token(
            id = tokenEntity.id.value.toKotlinUuid(),
            expiredAt = tokenEntity.expiredAt.toInstant(TimeZone.UTC),
        )
    }
}