package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.service.IUserService
import java.util.*

class EditProfileUseCaseImpl(
    private val userService: IUserService,
) : EditProfileUseCase {
    override suspend fun invoke(
        userId: UUID,
        profileForm: ProfileForm,
    ) = userService.editProfile(userId, profileForm)
}
