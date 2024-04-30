package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.GMessengerServer
import com.gazim.gmessenger.api.route.PingRoute
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.*
import kotlin.time.Duration
import kotlin.time.measureTime

class GMessengerAPIConnectionImpl : GMessengerAPIConnection {
    override val availableServers: List<GMessengerServer>
        get() =
            listOf(
                GMessengerServer(
                    url = "http://127.0.0.1:8080",
                    title = "Test server",
                    description = "Test GMessenger server",
                ),
            )

    override suspend fun ping(url: String): Duration =
        HttpClient {
            install(Resources)
            defaultRequest {
                url(url)
            }
        }.use {
            measureTime {
                it.get(PingRoute())
            }
        }
}
