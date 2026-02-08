@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.repository.TokenRepository
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

class TokenRepositoryImpl : TokenRepository {
    override suspend fun insertAndGetToken(
        createdAt: Instant,
        expiredAt: Instant,
        userId: Uuid,
    ): Token {
        val tokenEntity =
            TokenEntity.new {
                this.createdAt = createdAt
                this.expiredAt = expiredAt
                this.account = AccountEntity[userId]
            }
        return Token(
            id = tokenEntity.id.value.toKotlinUuid(),
            expiredAt = tokenEntity.expiredAt,
        )
    }

    override suspend fun getUserId(tokenId: Uuid) =
        TokenEntity[tokenId]
            .account.id.value
            .toKotlinUuid()
}
