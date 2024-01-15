package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IUserPresent
import com.gazim.gmessenger.backend.common.route.createChatRoute
import com.gazim.gmessenger.server.di.chatModule
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createChatRoute() =
    post(createChatRoute) {
        val user = call.receive<IUserPresent>()
        call.respondText(
            if (
                chatModule.createChat(listOf(user, getUser()))
            ) {
                "Chat is created"
            } else {
                "Chat is not created"
            },
        )
    }
