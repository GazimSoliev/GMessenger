package com.gazim.gmessenger.api.ktor_plugins

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureWebSockets() {
    install(WebSockets) {
        contentConverter =
            KotlinxWebsocketSerializationConverter(
                Json {
                    prettyPrint = true
                    isLenient = true
                },
            )
        pingInterval = 5_000
    }
}
