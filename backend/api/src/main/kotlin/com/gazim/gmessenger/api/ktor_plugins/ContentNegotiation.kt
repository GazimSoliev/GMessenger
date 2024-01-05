package com.gazim.gmessenger.api.ktor_plugins

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureContentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                prettyPrint = true
            },
        )
    }
}
