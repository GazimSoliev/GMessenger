@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.model.MessageForm
import com.gazim.gmessenger.core.route.ChatRoute
import com.gazim.gmessenger.server.domain.usecase.GetMessageFlowUseCase
import com.gazim.gmessenger.server.domain.usecase.SendMessageUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.webSocket
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

fun Route.chatRoute() {
    val sendMessageUseCase by inject<SendMessageUseCase>()
    val getMessageFlowUseCase by inject<GetMessageFlowUseCase>()

    webSocket<ChatRoute.Id> { params ->
        val userId = getUserId()
        val chatId = params.id
        val messageFlow = getMessageFlowUseCase(userId, chatId)

        launch {
            messageFlow.collect { message ->
                println("Sent: $message")
                sendSerialized(message.toAPI())
            }
        }
        while (true) {
            val message = receiveDeserialized<MessageForm>()
            sendMessageUseCase(
                userId = userId,
                chatId = chatId,
                message = message.message,
            )
        }
    }
}
