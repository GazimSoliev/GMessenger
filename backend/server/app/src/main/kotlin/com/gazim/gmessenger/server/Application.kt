package com.gazim.gmessenger.server

import com.gazim.gmessenger.server.di.configureKoin
import com.gazim.gmessenger.server.plugins.configureContentNegotiation
import com.gazim.gmessenger.server.plugins.configureJWT
import com.gazim.gmessenger.server.plugins.configureRouting
import com.gazim.gmessenger.server.plugins.configureWebSockets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::generalModule,
    ).start(wait = true)
}

private fun Application.generalModule() {
    configureKoin()
    configureContentNegotiation()
    configureJWT()
    configureWebSockets()
    configureRouting()
}
