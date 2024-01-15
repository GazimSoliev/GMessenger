package com.gazim.gmessenger.server.plugins

import com.gazim.gmessenger.server.route.generalRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() = routing(Routing::generalRoute)
