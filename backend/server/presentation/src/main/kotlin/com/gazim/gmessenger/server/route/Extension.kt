package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.usecase.IGetUserUseCase
import com.gazim.gmessenger.server.utils.ISecurityUtils
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import org.koin.ktor.plugin.scope
import java.util.*

suspend fun ApplicationCall.getTokenId() = scope.get<ISecurityUtils>().getUserTokenId(this)

suspend fun PipelineContext<*, ApplicationCall>.getUser(): User =
    call.scope.get<IGetUserUseCase>().invoke(UUID.fromString(call.getTokenId()))

suspend fun WebSocketServerSession.getUser(): User = call.scope.get<IGetUserUseCase>().invoke(UUID.fromString(call.getTokenId()))
