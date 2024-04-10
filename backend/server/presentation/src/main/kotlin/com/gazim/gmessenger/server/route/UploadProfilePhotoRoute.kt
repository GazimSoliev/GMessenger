package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.uploadProfilePhotoRoute
import com.gazim.gmessenger.server.domain.usecase.UploadProfilePhotoUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

fun Route.uploadProfilePhotoRoute() {
    val uploadProfilePhotoUseCase by inject<UploadProfilePhotoUseCase>()
    post("$uploadProfilePhotoRoute/{type}") {
        val user = getUser()
        val bytes = call.receiveChannel().toByteArray()
        val image = uploadProfilePhotoUseCase(user, call.parameters["type"] ?: "", bytes)
        call.respond(image.toAPI())
    }
}
