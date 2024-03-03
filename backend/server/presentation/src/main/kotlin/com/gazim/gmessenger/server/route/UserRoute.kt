package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.userRoute
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() {
    get(userRoute) {
        val user = getUser()
        call.respond(user.toAPI())
    }
}
