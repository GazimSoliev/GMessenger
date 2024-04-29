package com.gazim.gmessenger.extensions

import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.plugins.websocket.webSocket as webSocketBuilder

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
