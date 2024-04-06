package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.service.IGMessengerService

class EditProfileFormImpl(private val gMessengerService: IGMessengerService) : EditProfileForm {
    override suspend fun invoke(profileForm: ProfileForm) =
        gMessengerService.editProfile(profileForm)
}