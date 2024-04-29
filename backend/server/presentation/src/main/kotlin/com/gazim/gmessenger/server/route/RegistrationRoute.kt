package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.RegistrationForm
import com.gazim.gmessenger.api.route.RegistrationRoute
import com.gazim.gmessenger.server.domain.usecase.RegisterUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.registrationRoute() {
    val registerUseCase by inject<RegisterUseCase>()
    post<RegistrationRoute> {
        val account = call.receive<RegistrationForm>()
        val isSuccessful = registerUseCase(account.toDomain())
        call.respond(isSuccessful)
    }
}
