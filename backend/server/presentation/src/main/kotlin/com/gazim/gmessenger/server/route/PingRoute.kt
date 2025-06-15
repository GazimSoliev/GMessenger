package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.PingRoute
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pingRoute() {
    get<PingRoute> {
        call.respond(HttpStatusCode.OK, "OK")
    }
}
