package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IAccount
import com.gazim.gmessenger.backend.common.route.registrationRoute
import com.gazim.gmessenger.server.di.registrationModule
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.registrationRoute() =
    post(registrationRoute) {
        val account = call.receive<IAccount>()
        if (registrationModule.register(account)) {
            call.respondText("Account is created")
        } else {
            call.respondText("Can't create account")
        }
    }
