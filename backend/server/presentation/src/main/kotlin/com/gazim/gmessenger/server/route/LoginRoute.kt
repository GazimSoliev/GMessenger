package com.gazim.gmessenger.server.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.model.Token
import com.gazim.gmessenger.api.route.LoginRoute
import com.gazim.gmessenger.server.domain.usecase.LoginUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import com.gazim.gmessenger.server.plugins.audience
import com.gazim.gmessenger.server.plugins.claimTokenId
import com.gazim.gmessenger.server.plugins.issuer
import com.gazim.gmessenger.server.plugins.secret
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Route.loginRoute() {
    val loginUseCase by inject<LoginUseCase>()
    post<LoginRoute> {
        val loginPassword = call.receive<AuthenticationForm>().toDomain()
        println("LoginPassword: $loginPassword")
        val token = loginUseCase(loginPassword)
        println("Token: $token")
        val jwtToken = if (token == null) null
        else generateJWT(token.id.toString(), token.expiredAt)
        call.respondNullable(Token(jwtToken))
    }
}

private fun generateJWT(
    tokenId: String,
    expiredAt: Instant,
) = JWT
    .create()
    .withAudience(audience)
    .withIssuer(issuer)
    .withClaim(claimTokenId, tokenId)
    .withExpiresAt(expiredAt.toJavaInstant())
    .sign(Algorithm.HMAC256(secret))
