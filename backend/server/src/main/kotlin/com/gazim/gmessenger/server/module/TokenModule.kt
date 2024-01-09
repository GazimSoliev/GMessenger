package com.gazim.gmessenger.server.module

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.TokenEntity
import com.gazim.gmessenger.server.plugins.audience
import com.gazim.gmessenger.server.plugins.claimTokenId
import com.gazim.gmessenger.server.plugins.issuer
import com.gazim.gmessenger.server.plugins.secret
import java.time.LocalDateTime
import java.time.ZoneId

class TokenModule : ITokenModule {
    override suspend fun generateJWT(
        tokenId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(claimTokenId, tokenId)
            .withExpiresAt(expiredAt.atZone(ZoneId.systemDefault()).toInstant())
            .sign(Algorithm.HMAC256(secret))

    override suspend fun registerToken(
        accountId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int? =
        dbQuery {
            AccountEntity.findById(accountId)?.let {
                TokenEntity.new {
                    this.createdAt = createdAt
                    this.expiredAt = expiredAt
                    this.account = it
                }
            }
        }?.id?.value

    override suspend fun registerAndGenerateToken(
        accountId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): String? {
        val tokenId = registerToken(accountId, createdAt, expiredAt)
        return tokenId?.let { generateJWT(tokenId, createdAt, expiredAt) }
    }

    override suspend fun registerAndGenerateToken(accountId: Int): String? {
        val createdAt = LocalDateTime.now()
        val expiredAt = createdAt.plusDays(7)
        return registerAndGenerateToken(accountId, createdAt, expiredAt)
    }
}
