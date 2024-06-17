package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.model.RegistrationForm
import com.gazim.gmessenger.api.plugins.configureContentNegotiation
import com.gazim.gmessenger.api.route.LoginRoute
import com.gazim.gmessenger.api.route.RegistrationRoute
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class GMessengerAuthAPIImpl : GMessengerAuthAPI {
    private val httpClient
        get() =
            HttpClient {
                configureContentNegotiation()
                install(Resources)
                defaultRequest {
                    url(urlServer)
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

    override suspend fun login(loginPassword: AuthenticationForm): String =
        httpClient.use {
            httpClient
                .post(LoginRoute()) {
                    contentType(ContentType.Application.Json)
                    setBody(loginPassword)
                }.bodyAsText()
        }
}
