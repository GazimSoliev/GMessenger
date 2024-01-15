package com.gazim.gmessenger.server.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gazim.gmessenger.backend.common.model.ILoginPasswordPresent
import com.gazim.gmessenger.backend.common.route.loginRoute
import com.gazim.gmessenger.server.domain.usecase.ILoginUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import com.gazim.gmessenger.server.plugins.audience
import com.gazim.gmessenger.server.plugins.claimTokenId
import com.gazim.gmessenger.server.plugins.issuer
import com.gazim.gmessenger.server.plugins.secret
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.LocalDateTime
import java.time.ZoneId

fun Routing.loginRoute() {
    val loginUseCase by inject<ILoginUseCase>()
    post(loginRoute) {
        val loginPassword = call.receive<ILoginPasswordPresent>().toDomain()
        val tokenId = loginUseCase(loginPassword)
        val token = tokenId?.let(::generateJWT)
        call.respondNullable(token)
    }
}

private fun generateJWT(
    tokenId: Long,
    expiredAt: LocalDateTime = LocalDateTime.now().plusDays(7)
) = JWT.create()
    .withAudience(audience)
    .withIssuer(issuer)
    .withClaim(claimTokenId, tokenId)
    .withExpiresAt(expiredAt.atZone(ZoneId.systemDefault()).toInstant())
    .sign(Algorithm.HMAC256(secret))
