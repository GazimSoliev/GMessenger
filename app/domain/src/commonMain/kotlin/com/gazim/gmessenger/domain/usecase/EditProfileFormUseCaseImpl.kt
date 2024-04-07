package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.service.IGMessengerService

class EditProfileFormUseCaseImpl(private val gMessengerService: IGMessengerService) : EditProfileFormUseCase {
    override suspend fun invoke(profileForm: ProfileForm) = gMessengerService.editProfile(profileForm)
}
