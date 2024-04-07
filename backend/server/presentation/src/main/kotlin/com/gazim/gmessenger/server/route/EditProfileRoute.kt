package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.model.ProfileForm
import com.gazim.gmessenger.api.route.editProfileRoute
import com.gazim.gmessenger.server.domain.usecase.EditProfileUseCase
import com.gazim.gmessenger.server.extensions.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.editProfileRoute() {
    val editProfileUseCase by inject<EditProfileUseCase>()
    post(editProfileRoute) {
        val profileForm = call.receive<ProfileForm>()
        val user = getUser()
        editProfileUseCase(user, profileForm.toDomain())
    }
}
