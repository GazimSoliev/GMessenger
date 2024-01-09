package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IUser
import com.gazim.gmessenger.server.di.securityUtils
import com.gazim.gmessenger.server.di.userModule
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.runBlocking

@KtorDsl
fun Route.get(
    path: RoutePath,
    body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit,
): Route = get(path.path, body)

fun PipelineContext<Unit, ApplicationCall>.getTokenId(): Int = runBlocking { securityUtils.getUserTokenId(call) }

fun ApplicationCall.getTokenId(): Int = runBlocking { securityUtils.getUserTokenId(this@getTokenId) }

suspend fun PipelineContext<Unit, ApplicationCall>.getUser(): IUser = userModule.getUser(call.getTokenId())

suspend fun WebSocketServerSession.getUser(): IUser = userModule.getUser(call.getTokenId())
