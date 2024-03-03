package com.gazim.gmessenger.server.utils

import com.gazim.gmessenger.server.plugins.claimTokenId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

class SecurityUtils : ISecurityUtils {
    override suspend fun getUserTokenId(call: ApplicationCall): String =
        call.principal<JWTPrincipal>()!!.payload.getClaim(claimTokenId).asString()
}
