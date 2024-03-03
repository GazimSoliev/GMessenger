package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.MessageForm
import com.gazim.gmessenger.api.route.chatRoute
import com.gazim.gmessenger.server.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.IGetMessagesUseCase
import com.gazim.gmessenger.server.domain.usecase.ISendMessageUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import java.util.*

fun Route.chatRoute() {
    val sendMessageUseCase by inject<ISendMessageUseCase>()
    val getMessagesUseCase by inject<IGetMessagesUseCase>()
    val getChatUseCase by inject<IGetChatUseCase>()
    webSocket("$chatRoute/{id}") {
        val user = getUser()
        val chat =
            call.parameters["id"]?.let { getChatUseCase(user, UUID.fromString(it)) }
                ?: return@webSocket println("Can't find chat")
        launch(Dispatchers.IO) {
            getMessagesUseCase(user, chat)
                .also { println("Sent: $it") }
                ?.collect {
                    it.also { println("Sent: $it") }
                    sendSerialized(it.toAPI())
                }
        }
        while (true) {
            val message = receiveDeserialized<MessageForm>()
            sendMessageUseCase(user, chat, message.toDomain())
        }
    }
}
