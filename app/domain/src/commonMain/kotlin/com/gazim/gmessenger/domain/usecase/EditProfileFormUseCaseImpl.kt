package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class EditProfileFormUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : EditProfileFormUseCase {
    override suspend fun invoke(profileForm: ProfileForm) = gMessengerSessionService.editProfile(profileForm)
}
