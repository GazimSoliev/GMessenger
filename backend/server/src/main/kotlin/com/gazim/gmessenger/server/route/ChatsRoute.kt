package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.route.chatsRoute
import com.gazim.gmessenger.server.di.chatModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatsRoute() =
    get(chatsRoute) {
        val chats = chatModule.getChats(getUser())
        call.respond(chats)
    }
