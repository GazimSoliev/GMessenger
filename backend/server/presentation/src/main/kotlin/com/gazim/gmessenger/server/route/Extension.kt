@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.usecase.GetUserUseCase
import com.gazim.gmessenger.server.utils.ISecurityUtils
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.get
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

suspend fun ApplicationCall.getTokenId(): String {
    val securityUtils = get<ISecurityUtils>()
    val tokenId = securityUtils.getUserTokenId(this)
    return tokenId
}

suspend fun ApplicationCall.getUser(): User {
    val getUserUseCase = get<GetUserUseCase>()
    val tokenId = getTokenId()
    val tokenUuid = Uuid.parse(tokenId)
    val user = getUserUseCase(tokenUuid)
    return user
}

suspend fun RoutingContext.getUser(): User = call.getUser()

suspend fun RoutingContext.getUserId() = getUser().id

suspend fun WebSocketServerSession.getUser() = call.getUser()

suspend fun WebSocketServerSession.getUserId() = getUser().id
