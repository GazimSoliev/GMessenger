package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.MessageForm
import com.gazim.gmessenger.api.route.ChatRoute
import com.gazim.gmessenger.server.domain.usecase.GetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.GetMessageFlowUseCase
import com.gazim.gmessenger.server.domain.usecase.SendMessageUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import com.gazim.gmessenger.server.extensions.webSocket
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import java.util.*

fun Route.chatRoute() {
    val sendMessageUseCase by inject<SendMessageUseCase>()
    val getMessageFlowUseCase by inject<GetMessageFlowUseCase>()
    val getChatUseCase by inject<GetChatUseCase>()
    webSocket<ChatRoute.Id> { params ->
        val user = getUser()
        val chat =
            getChatUseCase(user, UUID.fromString(params.id))
                ?: return@webSocket println("Can't find chat")
        launch(Dispatchers.IO) {
            getMessageFlowUseCase(user, chat)
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
