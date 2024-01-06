package com.gazim.gmessenger.api.extensions

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

actual fun HttpClientConfig<*>.configureEngine() = engine {
    if (this !is OkHttpConfig) return@engine
    preconfigured = OkHttpClient.Builder()
        .pingInterval(5, TimeUnit.SECONDS)
        .build()
}