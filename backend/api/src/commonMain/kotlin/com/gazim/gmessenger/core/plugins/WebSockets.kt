package com.gazim.gmessenger.core.plugins

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureWebSockets() {
    install(WebSockets) {
        contentConverter =
            KotlinxWebsocketSerializationConverter(
                Json {
                    prettyPrint = true
                    isLenient = true
                },
            )
        pingInterval = 5.seconds
    }
}
