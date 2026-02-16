package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.route.FindUserRoute
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.usecase.FindUserUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.findUserRoute() {
    val findUserUseCase by inject<FindUserUseCase>()

    get<FindUserRoute.Query> { params ->
        val filterRequest = params.query
        val users = findUserUseCase(filterRequest)
        val userApis = users.map(User::toAPI)
        call.respond(userApis)
    }
}
