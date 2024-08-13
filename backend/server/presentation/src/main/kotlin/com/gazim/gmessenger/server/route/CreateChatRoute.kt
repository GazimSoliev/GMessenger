package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.UserID
import com.gazim.gmessenger.api.route.CreateChatRoute
import com.gazim.gmessenger.server.domain.usecase.CreateChatUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import java.util.*

fun Route.createChatRoute() {
    val createChatUseCase by inject<CreateChatUseCase>()
    post<CreateChatRoute> {
        val userId = UUID.fromString(call.receive<UserID>().id)
        val chat = createChatUseCase(getUserId(), listOf(userId))
        call.respondNullable(chat?.toAPI())
    }
}
