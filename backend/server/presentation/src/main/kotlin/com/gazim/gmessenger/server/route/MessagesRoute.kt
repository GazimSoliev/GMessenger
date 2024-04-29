package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.MessagePageKey
import com.gazim.gmessenger.api.route.MessagesRoute
import com.gazim.gmessenger.server.domain.usecase.GetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import java.util.*

fun Route.messagesRoute() {
    val getMessagesUseCase by inject<GetMessagesUseCase>()
    val getChatUseCase by inject<GetChatUseCase>()
    post<MessagesRoute.ChatId> { params ->
        val chatId = params.chatId
        val user = getUser()
        val chat = getChatUseCase(user, UUID.fromString(chatId)) ?: return@post println("Can't find chat")
        val key = call.receiveNullable<MessagePageKey?>()
        val page = getMessagesUseCase(user, chat, key?.toDomain()).toAPI()
        call.respond(page)
    }
}
