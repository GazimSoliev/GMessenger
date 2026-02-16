@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gazim.gmessenger.core.model.AuthenticationForm
import com.gazim.gmessenger.core.model.Token
import com.gazim.gmessenger.core.route.LoginRoute
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
import org.koin.ktor.ext.inject
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import kotlin.uuid.ExperimentalUuidApi

fun Route.loginRoute() {
    val loginUseCase by inject<LoginUseCase>()

    post<LoginRoute> {
        val authenticationFormApi = call.receive<AuthenticationForm>()
        val authenticationForm = authenticationFormApi.toDomain()
        println("AuthenticationForm: $authenticationFormApi")
        val token = loginUseCase(authenticationForm)
        println("Token: $token")
        val jwtToken =
            if (token != null) {
                generateJWT(token.id.toString(), token.expiredAt)
            } else {
                null
            }
        val tokenApi = Token(jwtToken)
        call.respondNullable(tokenApi)
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
