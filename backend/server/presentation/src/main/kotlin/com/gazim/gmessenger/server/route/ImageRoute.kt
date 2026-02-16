@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.core.route.ImageRoute
import com.gazim.gmessenger.server.domain.usecase.GetImageContentUseCase
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi

fun Route.imageRoute() {
    val getImageContentUseCase by inject<GetImageContentUseCase>()

    get<ImageRoute.Id> { params ->
        val imageId = params.id
        val bytes = getImageContentUseCase(imageId)
        call.respondBytes(bytes)
    }
}
