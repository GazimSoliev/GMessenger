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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.days
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Route.loginRoute() {
    val loginUseCase by inject<LoginUseCase>()
    post<LoginRoute> {
        val loginPassword = call.receive<AuthenticationForm>().toDomain()
        val createdAtInstance = Clock.System.now()
        val expiredAtInstance = createdAtInstance + 7.days
        val createdAt = createdAtInstance.toLocalDateTime(TimeZone.UTC)
        val expiredAt = expiredAtInstance.toLocalDateTime(TimeZone.UTC)
        println("LoginPassword: $loginPassword")
        val tokenId = loginUseCase(loginPassword, createdAt, expiredAt)
        println("Token: $tokenId")
        val token = tokenId?.let { generateJWT(it.id.toString(), expiredAtInstance) }
        call.respondNullable(Token(token))
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
