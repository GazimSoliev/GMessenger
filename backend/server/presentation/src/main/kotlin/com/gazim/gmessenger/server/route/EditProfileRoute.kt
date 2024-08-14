package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.ProfileForm
import com.gazim.gmessenger.api.route.EditProfileRoute
import com.gazim.gmessenger.server.domain.usecase.EditProfileUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.editProfileRoute() {
    val editProfileUseCase by inject<EditProfileUseCase>()
    post<EditProfileRoute> {
        val profileForm = call.receive<ProfileForm>()
        val userId = getUserId()
        editProfileUseCase(
            userId = userId,
            profileForm = profileForm.toDomain(),
        )
    }
}
