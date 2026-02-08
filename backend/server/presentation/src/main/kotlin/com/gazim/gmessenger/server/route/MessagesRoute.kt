package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.model.MessagePageKey
import com.gazim.gmessenger.core.route.MessagesRoute
import com.gazim.gmessenger.server.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Route.messagesRoute() {
    val getMessagesUseCase by inject<GetMessagesUseCase>()
    post<MessagesRoute.ChatId> { params ->
        val userId = getUserId()
        val chatId = Uuid.parse(params.chatId)
        val key = call.receiveNullable<MessagePageKey?>()
        val page =
            getMessagesUseCase(
                userId = userId,
                chatId = chatId,
                key = key?.toDomain(),
            ).toAPI()
        call.respond(page)
    }
}
