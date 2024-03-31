package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.MessagePageKey
import com.gazim.gmessenger.api.route.messagesRoute
import com.gazim.gmessenger.server.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.IGetMessagesUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.messagesRoute() {
    val getMessagesUseCase by inject<IGetMessagesUseCase>()
    val getChatUseCase by inject<IGetChatUseCase>()
    post("$messagesRoute/{chatId}") {
        val chatId = call.parameters["chadId"]
        val chatId = call.parameters["chatId"]
        val user = getUser()
        val chat = getChatUseCase(user, UUID.fromString(chatId)) ?: return@post println("Can't find chat")
        val key = call.receiveNullable<MessagePageKey>()
        val key = call.receiveNullable<MessagePageKey?>()
        val page = getMessagesUseCase(user, chat, key?.toDomain()).toAPI()
        call.respond(page)
    }
}
