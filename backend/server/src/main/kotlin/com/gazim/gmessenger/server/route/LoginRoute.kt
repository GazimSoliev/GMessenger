package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.ILoginPassword
import com.gazim.gmessenger.backend.common.route.loginRoute
import com.gazim.gmessenger.server.di.loginModule
import com.gazim.gmessenger.server.di.tokenModule
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.loginRoute() =
    post(loginRoute) {
        val loginPassword = call.receive<ILoginPassword>()
        val accountId = loginModule.login(loginPassword)
        val token = accountId?.let { tokenModule.registerAndGenerateToken(it) }
        call.respondNullable(token)
    }
