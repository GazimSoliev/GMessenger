package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.route.ChatsRoute
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.usecase.GetChatsUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Route.chatsRoute() {
    val getChatsUseCase by inject<GetChatsUseCase>()
    get<ChatsRoute> {
        val chats = getChatsUseCase(getUserId())
        call.respond(chats.map(IChat::toAPI))
    }
}
