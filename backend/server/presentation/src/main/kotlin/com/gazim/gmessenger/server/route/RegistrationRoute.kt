package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IAccountPresent
import com.gazim.gmessenger.backend.common.route.registrationRoute
import com.gazim.gmessenger.server.domain.usecase.IRegisterUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.registrationRoute() {
    val registerUseCase by inject<IRegisterUseCase>()
    post(registrationRoute) {
        val account = call.receive<IAccountPresent>()
        val isSuccessful = registerUseCase(account.toDomain())
        call.respond(isSuccessful)
    }
}
