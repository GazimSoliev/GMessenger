package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.findUserRoute
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.usecase.FindUserUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.findUserRoute() {
    val findUserUseCase by inject<FindUserUseCase>()
    get(findUserRoute) {
        val filterRequest = call.parameters["filter"]
        val users = filterRequest?.let { findUserUseCase(it) } ?: emptyList()
        call.respond(users.map(User::toAPI))
    }
}
