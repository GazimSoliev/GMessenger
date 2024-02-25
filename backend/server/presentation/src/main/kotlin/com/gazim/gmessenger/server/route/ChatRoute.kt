package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.ISentMessagePresent
import com.gazim.gmessenger.backend.common.route.chatRoute
import com.gazim.gmessenger.server.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.IGetMessagesUseCase
import com.gazim.gmessenger.server.domain.usecase.ISendMessageUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import com.gazim.gmessenger.server.extensions.toPresent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Route.chatRoute() {
    val sendMessageUseCase by inject<ISendMessageUseCase>()
    val getMessagesUseCase by inject<IGetMessagesUseCase>()
    val getChatUseCase by inject<IGetChatUseCase>()
    webSocket("$chatRoute/{id}") {
        val user = getUser()
        val chat =
            call.parameters["id"]?.toInt()?.let { getChatUseCase(user, it) }
                ?: return@webSocket println("Can't find chat")
        launch(Dispatchers.IO) {
            getMessagesUseCase(user, chat)
                .also { println("Sent: $it") }
                ?.collect {
                    it.also { println("Sent: $it") }
                    sendSerialized(it.toPresent())
                }
        }
        while (true) {
            val message = receiveDeserialized<ISentMessagePresent>()
            sendMessageUseCase(user, chat, message.toDomain())
        }
    }
}
