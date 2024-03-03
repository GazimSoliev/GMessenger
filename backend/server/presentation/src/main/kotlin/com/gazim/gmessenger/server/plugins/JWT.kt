package com.gazim.gmessenger.server.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

const val audience = "users"
const val issuer = "Gazim Developer"
const val secret = "GMessengerServer_secret"
const val realm = "GMessengerServer"
const val jwtName = "GMessengerServerAuth"
const val claimTokenId = "id"

fun Application.configureJWT() {
    install(Authentication) {
        jwt(jwtName) {
            realm = com.gazim.gmessenger.server.plugins.realm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build(),
            )
            validate { credential ->
                if (credential.payload.getClaim(claimTokenId).asString() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
