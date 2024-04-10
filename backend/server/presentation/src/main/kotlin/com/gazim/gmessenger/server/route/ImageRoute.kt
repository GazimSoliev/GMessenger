package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.imageRoute
import com.gazim.gmessenger.server.domain.usecase.GetImageContentUseCase
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.imageRoute() {
    val getImageContentUseCase by inject<GetImageContentUseCase>()
    get("$imageRoute/{id}") {
        val bytes = getImageContentUseCase(UUID.fromString(call.parameters["id"]!!))
        call.respondBytes(bytes)
    }
}
