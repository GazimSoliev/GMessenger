package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.ImageRoute
import com.gazim.gmessenger.server.domain.usecase.GetImageContentUseCase
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Route.imageRoute() {
    val getImageContentUseCase by inject<GetImageContentUseCase>()
    get<ImageRoute.Id> { params ->
        val imageId = Uuid.parse(params.id)
        val bytes = getImageContentUseCase(imageId)
        call.respondBytes(bytes)
    }
}
