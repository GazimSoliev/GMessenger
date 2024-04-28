package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.User
import com.gazim.gmessenger.api.route.createChatRoute
import com.gazim.gmessenger.server.domain.usecase.CreateChatUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createChatRoute() {
    val createChatUseCase by inject<CreateChatUseCase>()
    post(createChatRoute) {
        val user = call.receive<User>()
        val chat = createChatUseCase(getUser(), listOf(user.toDomain()))
        call.respondNullable(chat?.toAPI())
    }
}
