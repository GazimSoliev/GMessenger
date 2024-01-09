package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.route.findUserRoute
import com.gazim.gmessenger.server.di.userModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.findUserRoute() =
    get(findUserRoute) {
        val filterRequest = call.parameters["filter"]
        filterRequest?.let {
            call.respond(userModule.findUser(it))
        }
    }
