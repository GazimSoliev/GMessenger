package com.gazim.gmessenger.core

import com.gazim.gmessenger.core.extensions.configureEngine
import com.gazim.gmessenger.core.extensions.webSocket
import com.gazim.gmessenger.core.model.*
import com.gazim.gmessenger.core.plugins.configureContentNegotiation
import com.gazim.gmessenger.core.plugins.configureWebSockets
import com.gazim.gmessenger.core.route.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GMessengerAPIImpl(
    private val host: String,
    isSecure: Boolean,
    token: String,
) : GMessengerAPI {
    private val httpProtocol = if (isSecure) URLProtocol.HTTPS else URLProtocol.HTTP
    private val wsProtocol = if (isSecure) URLProtocol.WSS else URLProtocol.WS

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
            install(Resources)
            defaultRequest {
                host = this@GMessengerAPIImpl.host
                url { protocol = httpProtocol }
                contentType(ContentType.Application.Json)
            }
        }

    private var _userId: Uuid? = null

    suspend fun getUserId(): Uuid {
        if (_userId != null) return _userId!!
        val mutex = Mutex()
        mutex.lock(this)
        if (_userId != null) return _userId!!
        val user = whoAmI()
        _userId = user.id
        mutex.unlock(this)
        return _userId!!
    }

    override suspend fun whoAmI(): User = httpClient.get(UserRoute()).body()

    override suspend fun getChats(): List<IChat> = httpClient.get(ChatsRoute()).body()

    override suspend fun getChat(chatId: Uuid): IChat = httpClient.get(GetChatRoute.Id(chatId.toString())).body()

    override suspend fun createChat(userID: Uuid): Boolean =
        httpClient
            .post(CreateChatRoute()) {
                setBody(UserID(userID))
            }.status == HttpStatusCode.OK

    override fun getChatWebSocket(chatID: Uuid): ChatWebSocket =
        object : ChatWebSocket {
            val getter = MutableSharedFlow<IMessage>()
            val setter = MutableSharedFlow<MessageForm>()
            var output: Job? = null
            override val messages: Flow<IMessage>
                get() = getter

            override suspend fun openConnection() {
                coroutineScope {
                    httpClient.webSocket(
                        resource = ChatRoute.Id(chatID.toString()),
                        request = { url { protocol = wsProtocol } },
                    ) {
                        println(this.call.request.url)
                        val input =
                            this@coroutineScope.launch {
                                for (frame in incoming) {
                                    converter
                                        ?.deserialize<Message>(frame)
                                        ?.let { if (it.user.id == getUserId()) it.toMyMessage() else it }
                                        ?.let { getter.emit(it) }
                                }
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

    override suspend fun findUser(username: String): List<User> =
        if (username.isBlank()) {
            emptyList()
        } else {
            httpClient.get(FindUserRoute.Query(username)).body()
        }

    override suspend fun getNotifications(): NotificationSocket =
        object : NotificationSocket {
            private val _notifications = MutableSharedFlow<MessageNotification>()
            private var job: Job? = null

            override val notifications: Flow<MessageNotification> = _notifications.asSharedFlow()

            override suspend fun openConnection() {
                coroutineScope {
                    job =
                        launch(Dispatchers.IO) {
                            httpClient.webSocket(NotificationsRoute) {
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
        chatId: Uuid,
        key: MessagePageKey?,
    ): MyMessagePage {
        val page =
            httpClient
                .post(MessagesRoute.ChatId(chatId.toString())) {
                    setBody(key)
                }.body<MessagePage>()
        return page.toMyPage(getUserId())
    }

    override suspend fun editProfile(profileForm: ProfileForm) {
        httpClient.post(EditProfileRoute()) {
            setBody(profileForm)
        }
    }

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image =
        httpClient
            .post(UploadProfilePhotoRoute.Type(type)) {
                setBody(bytes)
            }.body()

    override suspend fun getImageContent(photoId: Uuid): ByteArray = httpClient.get(ImageRoute.Id(photoId.toString())).bodyAsBytes()

    override fun close() = httpClient.close()
}
