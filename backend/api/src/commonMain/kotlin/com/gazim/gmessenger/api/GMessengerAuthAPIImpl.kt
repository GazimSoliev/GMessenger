package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.model.RegistrationForm
import com.gazim.gmessenger.api.model.Token
import com.gazim.gmessenger.api.plugins.configureContentNegotiation
import com.gazim.gmessenger.api.route.LoginRoute
import com.gazim.gmessenger.api.route.RegistrationRoute
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*

class GMessengerAuthAPIImpl(
    private val host: String,
    isSecure: Boolean,
) : GMessengerAuthAPI {
    private val httpProtocol = if (isSecure) URLProtocol.HTTPS else URLProtocol.HTTP

    private val httpClient
        get() =
            HttpClient {
                configureContentNegotiation()
                install(Resources)
                defaultRequest {
                    host = this@GMessengerAuthAPIImpl.host
                    url { protocol = httpProtocol }
                }
            }

    override suspend fun register(account: RegistrationForm): Boolean =
        httpClient.use {
            it
                .post(RegistrationRoute()) {
                    contentType(ContentType.Application.Json)
                    setBody(account)
                }.status == HttpStatusCode.OK
        }

    override suspend fun login(loginPassword: AuthenticationForm): Token =
        httpClient.use {
            httpClient
                .post(LoginRoute()) {
                    contentType(ContentType.Application.Json)
                    setBody(loginPassword)
                }.body()
        }
}
