package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.route.userRoute
import com.gazim.gmessenger.server.di.userModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() =
    get(userRoute) {
        call.respond(userModule.getUser(getTokenId()))
    }
