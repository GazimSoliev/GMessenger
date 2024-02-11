package com.gazim.gmessenger.server.utils

import com.gazim.gmessenger.server.plugins.claimTokenId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

class SecurityUtils : ISecurityUtils {
    override suspend fun getUserTokenId(call: ApplicationCall): Int =
        call.principal<JWTPrincipal>()!!.payload.getClaim(claimTokenId).asInt()
}
