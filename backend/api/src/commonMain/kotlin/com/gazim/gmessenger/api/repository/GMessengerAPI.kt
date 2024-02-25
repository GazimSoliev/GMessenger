package com.gazim.gmessenger.api.repository

import com.gazim.gmessenger.api.extensions.configureEngine
import com.gazim.gmessenger.api.ipServer
import com.gazim.gmessenger.api.plugins.configureContentNegotiation
import com.gazim.gmessenger.api.plugins.configureWebSockets
import com.gazim.gmessenger.api.urlServer
import com.gazim.gmessenger.api.wsServer
import com.gazim.gmessenger.backend.common.model.*
import com.gazim.gmessenger.backend.common.route.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.Closeable
import kotlin.io.println
import kotlin.use

class GMessengerAPI(token: String) : IGMessengerAPI, Closeable {
    private val httpClient: HttpClient =
        HttpClient {
            configureEngine()
            configureWebSockets()
            configureContentNegotiation()
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(token, "")
                    }
                }
            }
        }

    companion object : IGMessengerAuthAPI {
        private val httpClient
            get() = HttpClient { configureContentNegotiation() }

        override suspend fun register(account: IAccountPresent): Boolean =
            httpClient.use {
                it.post("$urlServer$registrationRoute".also(::println)) {
                    contentType(ContentType.Application.Json)
                    setBody(account)
                }.status == HttpStatusCode.OK
            }

        override suspend fun login(loginPassword: ILoginPasswordPresent): String =
            httpClient.use {
                httpClient.post("$urlServer$loginRoute".also { println(it) }) {
                    contentType(ContentType.Application.Json)
                    setBody(loginPassword)
                }.bodyAsText()
            }
    }

    override suspend fun whoAmI(): IUserPresent = httpClient.get("$urlServer$userRoute").body()

    override suspend fun getChats(): List<IChatPresent> = httpClient.get("$urlServer$chatsRoute").body()

    override suspend fun createChat(user: IUserPresent): Boolean =
        httpClient.post("$urlServer$createChatRoute") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.status == HttpStatusCode.OK

    override fun getChatWebSocket(chat: IChatPresent): IChatWebSocket =
        object : IChatWebSocket {
            val getter = MutableSharedFlow<IMessagePresent>()
            val setter = MutableSharedFlow<ISentMessagePresent>()
            var output: Job? = null
            override val messages: Flow<IMessagePresent>
                get() = getter

            override suspend fun openConnection() {
                coroutineScope {
                    val user = whoAmI()
                    httpClient.webSocket("$wsServer$chatRoute/${chat.identifier}") {
                        println(this.call.request.url)
                        val input =
                            this@coroutineScope.launch {
                                for (frame in incoming) converter
                                    ?.deserialize<IMessagePresent>(frame)
                                    ?.let { if (it.user == user) it.toYourMessage() else it }
                                    ?.let { getter.emit(it) }
                            }
                        output =
                            this@coroutineScope.launch {
                                setter.collect { sendSerialized(it) }
                            }
                        output?.join()
                        input.cancelAndJoin()
                    }
                }
            }

            override suspend fun sendMessage(message: ISentMessagePresent) {
                setter.emit(message)
            }

            override fun close() {
                output?.cancel()
            }
        }

    override suspend fun findUser(username: String): List<IUserPresent> = httpClient.get("$urlServer$findUserRoute?filter=$username").body()

    override suspend fun getNotifications(): INotificationSocket =
        object : INotificationSocket {
            private val _notifications = MutableSharedFlow<INotificationPresent>()
            private var job: Job? = null

            override val notifications: Flow<INotificationPresent> = _notifications.asSharedFlow()

            override suspend fun openConnection() {
                coroutineScope {
                    job =
                        launch(Dispatchers.IO) {
                            httpClient.wss(host = ipServer, path = notificationRoute) {
                                withContext(Dispatchers.IO) {
                                    for (frame in incoming) {
                                        val notification = converter?.deserialize<INotificationPresent>(frame) ?: continue
                                        _notifications.emit(notification)
                                    }
                                }
                            }
                        }
                }
            }

            override suspend fun closeConnection() = job?.cancel() ?: Unit
        }

    override fun close() = httpClient.close()
}
