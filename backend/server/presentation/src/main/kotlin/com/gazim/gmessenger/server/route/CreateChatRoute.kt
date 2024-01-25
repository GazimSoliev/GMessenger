package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IUserPresent
import com.gazim.gmessenger.backend.common.route.createChatRoute
import com.gazim.gmessenger.server.domain.usecase.ICreateChatUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import com.gazim.gmessenger.server.extensions.toPresent
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createChatRoute() {
    val createChatUseCase by inject<ICreateChatUseCase>()
    post(createChatRoute) {
        val user = call.receive<IUserPresent>()
        val chat = createChatUseCase(getUser(), listOf(user.toDomain()))
        call.respondNullable(chat?.toPresent())
    }
}
