@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.model.UserID
import com.gazim.gmessenger.core.route.CreateChatRoute
import com.gazim.gmessenger.server.domain.usecase.CreateChatUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

fun Route.createChatRoute() {
    val createChatUseCase by inject<CreateChatUseCase>()

    post<CreateChatRoute> {
        val userId = getUserId()
        val companionUserId = call.receive<UserID>().id
        val chat = createChatUseCase(
            ownerId = userId,
            userIds = listOf(companionUserId)
        )
        val chatApi = chat.toAPI()
        call.respondNullable(chatApi)
    }
}
