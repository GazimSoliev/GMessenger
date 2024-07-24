package com.gazim.gmessenger.api.extensions

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.resources.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

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
    crossinline request: HttpRequestBuilder.() -> Unit = {},
    noinline block: suspend DefaultClientWebSocketSession.() -> Unit,
) {
    val resources = pluginOrNull(Resources) ?: throw IllegalStateException("Resources plugin is not installed")
    webSocket(
        request = {
            href(resources.resourcesFormat, resource, url)
            request()
        },
        block = block,
    )
}
