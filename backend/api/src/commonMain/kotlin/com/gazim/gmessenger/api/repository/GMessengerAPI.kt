package com.gazim.gmessenger.api.repository

import com.gazim.gmessenger.api.extensions.configureEngine
import com.gazim.gmessenger.api.ipServer
import com.gazim.gmessenger.api.model.*
import com.gazim.gmessenger.api.plugins.configureContentNegotiation
import com.gazim.gmessenger.api.plugins.configureWebSockets
import com.gazim.gmessenger.api.urlServer
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
import kotlinx.coroutines.flow.MutableStateFlow
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

        override suspend fun register(account: IAccount): Boolean =
            httpClient.use {
                it.post("$urlServer/registration".also(::println)) {
                    contentType(ContentType.Application.Json)
                    setBody(account)
                }.status == HttpStatusCode.OK
            }

        override suspend fun login(loginPassword: ILoginPassword): String =
            httpClient.use {
                httpClient.post("$urlServer/login".also { println(it) }) {
                    contentType(ContentType.Application.Json)
                    setBody(loginPassword)
                }.bodyAsText()
            }
    }

    override suspend fun whoAmI(): IUser = httpClient.get("$urlServer/user").body()

    override suspend fun getChats(): List<IChat> = httpClient.get("$urlServer/chats").body()

    override suspend fun createChat(user: IUser): Boolean =
        httpClient.post("$urlServer/create_chat") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.status == HttpStatusCode.OK

    override fun getChatWebSocket(chat: IChat): IChatWebSocket =
        object : IChatWebSocket {
            val getter = MutableStateFlow(emptyList<IMessage>())
            val setter = MutableSharedFlow<ISentMessage>()
            var output: Job? = null
            override val messages: Flow<List<IMessage>>
                get() = getter

            override suspend fun openConnection() {
                coroutineScope {
                    val user = whoAmI()
                    httpClient.webSocket(host = ipServer, port = 8080, path = "/chat/${chat.identifier}") {
                        val input =
                            this@coroutineScope.launch {
                                for (frame in incoming) converter
                                    ?.deserialize<List<IMessage>>(frame)
                                    ?.map { if (it.user == user) it.toYourMessage() else it }
                                    ?.let { getter.value = it }
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

            override suspend fun sendMessage(message: ISentMessage) {
                setter.emit(message)
            }

            override fun close() {
                output?.cancel()
            }
        }

    override suspend fun findUser(username: String): List<IUser> = httpClient.get("$urlServer/find_user?filter=$username").body()

    override suspend fun getNotifications(): INotificationSocket =
        object : INotificationSocket {
            private val _notifications = MutableSharedFlow<INotification>()
            private var job: Job? = null

            override val notifications: Flow<INotification> = _notifications.asSharedFlow()

            override suspend fun openConnection() {
                coroutineScope {
                    job =
                        launch(Dispatchers.IO) {
                            httpClient.webSocket(host = ipServer, port = 8080, path = "/notifications") {
                                withContext(Dispatchers.IO) {
                                    for (frame in incoming) {
                                        val notification = converter?.deserialize<INotification>(frame) ?: continue
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
