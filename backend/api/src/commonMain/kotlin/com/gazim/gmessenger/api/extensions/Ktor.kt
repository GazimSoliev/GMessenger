package com.gazim.gmessenger.api.extensions

import com.gazim.gmessenger.api.wsServer
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import io.ktor.client.plugins.websocket.webSocket as webSocketBuilder

fun HttpClientConfig<*>.configureEngine() =
    engine {
        if (this !is OkHttpConfig) return@engine
        preconfigured =
            OkHttpClient
                .Builder()
                .pingInterval(5, TimeUnit.SECONDS)
                .build()
    }

suspend inline fun <reified T : Any> HttpClient.webSocket(
    resource: T,
    noinline block: suspend DefaultClientWebSocketSession.() -> Unit,
) {
    webSocketBuilder(
        // TODO: HOT FIX
        urlString = href(resource).let {
            wsServer.dropLast(1) + it
        },
        block = block
    )
}
