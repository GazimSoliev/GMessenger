package com.gazim.gmessenger.server.utils

import io.ktor.server.application.*

interface ISecurityUtils {
    suspend fun getUserTokenId(call: ApplicationCall): String
}
