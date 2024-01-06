package com.gazim.gmessenger.api.extensions

import io.ktor.client.*

expect fun HttpClientConfig<*>.configureEngine()
