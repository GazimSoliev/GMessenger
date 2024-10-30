package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.GetChatRoute
import com.gazim.gmessenger.server.domain.usecase.GetChatUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Route.getChatRoute() {
    val getChatUseCase by inject<GetChatUseCase>()
    get<GetChatRoute.Id> { params ->
        val userId = getUserId()
        val chatId = Uuid.parse(params.id)
        val chat =
            getChatUseCase(
                userId = userId,
                chatId = chatId,
            )!!
        call.respond(chat.toAPI())
    }
}
