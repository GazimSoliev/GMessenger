package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.service.GMessengerService

class EditProfileFormUseCaseImpl(private val gMessengerService: GMessengerService) : EditProfileFormUseCase {
    override suspend fun invoke(profileForm: ProfileForm) = gMessengerService.editProfile(profileForm)
}
