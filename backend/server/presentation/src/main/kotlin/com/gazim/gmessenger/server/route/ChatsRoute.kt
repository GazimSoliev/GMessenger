package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.chatsRoute
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.usecase.IGetChatsUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chatsRoute() {
    val getChatsUseCase by inject<IGetChatsUseCase>()
    get(chatsRoute) {
        val chats = getChatsUseCase(getUser())
        call.respond(chats.map(IChat::toAPI))
    }
}
