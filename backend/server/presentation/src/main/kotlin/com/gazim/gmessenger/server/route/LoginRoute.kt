package com.gazim.gmessenger.server.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.route.loginRoute
import com.gazim.gmessenger.server.domain.usecase.LoginUseCase
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
    val loginUseCase by inject<LoginUseCase>()
    post(loginRoute) {
        val loginPassword = call.receive<AuthenticationForm>().toDomain()
        val createAt = LocalDateTime.now()
        val expiredAt = LocalDateTime.now().plusDays(7)
        val tokenId = loginUseCase(loginPassword, createAt, expiredAt)
        val token = tokenId?.let { generateJWT(it.id.toString(), expiredAt) }
        call.respondNullable(token)
    }
}

private fun generateJWT(
    tokenId: String,
    expiredAt: LocalDateTime = LocalDateTime.now().plusDays(7),
) = JWT.create()
    .withAudience(audience)
    .withIssuer(issuer)
    .withClaim(claimTokenId, tokenId)
    .withExpiresAt(expiredAt.atZone(ZoneId.systemDefault()).toInstant())
    .sign(Algorithm.HMAC256(secret))
