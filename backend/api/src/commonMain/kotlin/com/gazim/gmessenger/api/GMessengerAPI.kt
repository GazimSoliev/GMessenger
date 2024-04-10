package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.extensions.configureEngine
import com.gazim.gmessenger.api.model.*
import com.gazim.gmessenger.api.plugins.configureContentNegotiation
import com.gazim.gmessenger.api.plugins.configureWebSockets
import com.gazim.gmessenger.api.route.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
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

    private var _userId = ""

    suspend fun getUserId(): String {
        if (_userId.isNotBlank()) return _userId
        val mutex = Mutex()
        mutex.lock(this)
        if (_userId.isNotBlank()) return _userId
        val user = whoAmI()
        _userId = user.id
        mutex.unlock(this)
        return _userId
    }

    companion object : IGMessengerAuthAPI {
        private val httpClient
            get() = HttpClient { configureContentNegotiation() }

        override suspend fun register(account: RegistrationForm): Boolean =
            httpClient.use {
                it.post("$urlServer$registrationRoute".also(::println)) {
                    contentType(ContentType.Application.Json)
                    setBody(account)
                }.status == HttpStatusCode.OK
            }

        override suspend fun login(loginPassword: AuthenticationForm): String =
            httpClient.use {
                httpClient.post("$urlServer$loginRoute".also { println(it) }) {
                    contentType(ContentType.Application.Json)
                    setBody(loginPassword)
                }.bodyAsText()
            }
    }

    override suspend fun whoAmI(): User = httpClient.get("$urlServer$userRoute").body()

    override suspend fun getChats(): List<IChat> = httpClient.get("$urlServer$chatsRoute").body()

    override suspend fun createChat(user: User): Boolean =
        httpClient.post("$urlServer$createChatRoute") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.status == HttpStatusCode.OK

    override fun getChatWebSocket(chat: IChat): IChatWebSocket =
        object : IChatWebSocket {
            val getter = MutableSharedFlow<IMessage>()
            val setter = MutableSharedFlow<MessageForm>()
            var output: Job? = null
            override val messages: Flow<IMessage>
                get() = getter

            override suspend fun openConnection() {
                coroutineScope {
                    httpClient.webSocket("$wsServer$chatRoute/${chat.id}") {
                        println(this.call.request.url)
                        val input =
                            this@coroutineScope.launch {
                                for (frame in incoming) converter
                                    ?.deserialize<Message>(frame)
                                    ?.let { if (it.user.id == getUserId()) it.toMyMessage() else it }
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

            override suspend fun sendMessage(message: MessageForm) {
                setter.emit(message)
            }

            override fun close() {
                output?.cancel()
            }
        }

    override suspend fun findUser(username: String): List<User> = httpClient.get("$urlServer$findUserRoute?filter=$username").body()

    override suspend fun getNotifications(): INotificationSocket =
        object : INotificationSocket {
            private val _notifications = MutableSharedFlow<MessageNotification>()
            private var job: Job? = null

            override val notifications: Flow<MessageNotification> = _notifications.asSharedFlow()

            override suspend fun openConnection() {
                coroutineScope {
                    job =
                        launch(Dispatchers.IO) {
                            httpClient.wss(host = ipServer, path = notificationRoute) {
                                withContext(Dispatchers.IO) {
                                    for (frame in incoming) {
                                        val notification =
                                            converter?.deserialize<MessageNotification>(frame) ?: continue
                                        _notifications.emit(notification)
                                    }
                                }
                            }
                        }
                }
            }

            override suspend fun closeConnection() = job?.cancel() ?: Unit
        }

    override suspend fun getMessages(
        chat: IChat,
        key: MessagePageKey?,
    ): MyMessagePage {
        val page =
            httpClient.post("$urlServer$messagesRoute/${chat.id}") {
                contentType(ContentType.Application.Json)
                setBody(key)
            }.body<MessagePage>()
        return page.toMyPage(getUserId())
    }

    override suspend fun editProfile(profileForm: ProfileForm) {
        httpClient.post("$urlServer$editProfileRoute") {
            contentType(ContentType.Application.Json)
            setBody(profileForm)
        }
    }

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image =
        httpClient.post("$urlServer$uploadProfilePhotoRoute/$type") {
            setBody(bytes)
        }.body()

    override suspend fun getImageContent(photoId: String): ByteArray =
        httpClient.get("$urlServer$imageRoute/$photoId").bodyAsChannel().toByteArray()

    override fun close() = httpClient.close()
}
