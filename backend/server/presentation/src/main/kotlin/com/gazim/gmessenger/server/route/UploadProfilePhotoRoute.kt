@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.route.UploadProfilePhotoRoute
import com.gazim.gmessenger.server.domain.usecase.UploadProfilePhotoUseCase
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.utils.io.*
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

fun Route.uploadProfilePhotoRoute() {
    val uploadProfilePhotoUseCase by inject<UploadProfilePhotoUseCase>()

    post<UploadProfilePhotoRoute.Type> { params ->
        val userId = getUserId()
        val bytes = call.receiveChannel().toByteArray()
        val image = uploadProfilePhotoUseCase(
            userId = userId,
            type = params.type,
            content = bytes
        )
        val imageApi = image.toAPI()
        call.respond(imageApi)
    }
}
