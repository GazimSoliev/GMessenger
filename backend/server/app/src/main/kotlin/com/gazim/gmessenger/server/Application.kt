package com.gazim.gmessenger.server

import com.gazim.gmessenger.server.di.configureKoin
import com.gazim.gmessenger.server.plugins.*
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
    configureResources()
    configureRouting()
}
