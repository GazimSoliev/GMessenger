package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.GMessengerServer
import com.gazim.gmessenger.api.route.PingRoute
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import kotlin.time.Duration
import kotlin.time.measureTime

class GMessengerAPIConnectionImpl : GMessengerAPIConnection {
    override val availableServers: List<GMessengerServer>
        get() =
            listOf(
                GMessengerServer(
                    host = "127.0.0.1:8080",
                    isSecure = false,
                    title = "Test server",
                    description = "Test GMessenger server",
                ),
            )

    override suspend fun ping(host: String, isSecure: Boolean): Duration =
        HttpClient {
            install(Resources)
            defaultRequest {
                url {
                    this.host = host
                    this.protocol = if (isSecure) URLProtocol.HTTPS else URLProtocol.HTTP
                }
            }
        }.use {
            measureTime {
                it.get(PingRoute())
            }
        }
}
