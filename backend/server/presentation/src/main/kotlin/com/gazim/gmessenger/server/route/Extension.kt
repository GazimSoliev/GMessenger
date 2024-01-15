package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.usecase.IGetUserUseCase
import com.gazim.gmessenger.server.utils.ISecurityUtils
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import org.koin.ktor.plugin.scope

suspend fun PipelineContext<Unit, ApplicationCall>.getTokenId(): Long = call.scope.get<ISecurityUtils>().getUserTokenId(call)

suspend fun ApplicationCall.getTokenId(): Long = scope.get<ISecurityUtils>().getUserTokenId(this)

suspend fun PipelineContext<*, ApplicationCall>.getUser(): IUser = call.scope.get<IGetUserUseCase>().invoke(call.getTokenId())

suspend fun WebSocketServerSession.getUser(): IUser = call.scope.get<IGetUserUseCase>().invoke(call.getTokenId())
