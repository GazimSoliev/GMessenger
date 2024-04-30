package com.gazim.gmessenger.api.extensions

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import io.ktor.client.plugins.websocket.webSocket as webSocketBuilder

fun HttpClientConfig<*>.configureEngine() =
    engine {
        if (this !is OkHttpConfig) return@engine
        preconfigured =
            OkHttpClient.Builder()
                .pingInterval(5, TimeUnit.SECONDS)
                .build()
    }

suspend inline fun <reified T : Any> HttpClient.webSocket(
    resource: T,
    noinline request: HttpRequestBuilder.() -> Unit = {},
    noinline block: suspend DefaultClientWebSocketSession.() -> Unit,
) {
    webSocketBuilder(
        urlString = href(resource),
        request = request,
        block = block,
    )
}
