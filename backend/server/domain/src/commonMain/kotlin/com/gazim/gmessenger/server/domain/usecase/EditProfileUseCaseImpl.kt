package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IUserService

class EditProfileUseCaseImpl(private val userService: IUserService) : EditProfileUseCase {
    override suspend fun invoke(
        user: User,
        profileForm: ProfileForm,
    ) = userService.editProfile(user, profileForm)
}
