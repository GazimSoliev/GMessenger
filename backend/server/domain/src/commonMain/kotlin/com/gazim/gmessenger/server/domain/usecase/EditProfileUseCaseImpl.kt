package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.service.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class EditProfileUseCaseImpl(
    private val userService: UserService,
) : EditProfileUseCase {
    override suspend fun invoke(
        userId: Uuid,
        profileForm: ProfileForm,
    ) = userService.editProfile(userId, profileForm)
}
