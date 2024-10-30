@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.usecase.GetUserUseCase
import com.gazim.gmessenger.server.utils.ISecurityUtils
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import org.koin.ktor.plugin.scope
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

suspend fun ApplicationCall.getTokenId() = scope.get<ISecurityUtils>().getUserTokenId(this)

suspend fun PipelineContext<*, ApplicationCall>.getUser(): User =
    call.scope.get<GetUserUseCase>().invoke(Uuid.parse(call.getTokenId()))

@OptIn(ExperimentalUuidApi::class)
suspend fun PipelineContext<*, ApplicationCall>.getUserId() = getUser().id

suspend fun WebSocketServerSession.getUser(): User = call.scope.get<GetUserUseCase>().invoke(Uuid.parse(call.getTokenId()))

suspend fun WebSocketServerSession.getUserId() = getUser().id
