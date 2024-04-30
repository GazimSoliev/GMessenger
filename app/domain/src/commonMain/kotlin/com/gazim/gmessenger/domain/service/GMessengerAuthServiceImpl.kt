package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.model.RegistrationForm

class GMessengerAuthServiceImpl(private val gMessengerAuthAPI: GMessengerAuthAPI) : GMessengerAuthService {
    override suspend fun register(registrationForm: RegistrationForm): Boolean = gMessengerAuthAPI.register(registrationForm)

    override suspend fun login(loginPasswordModel: AuthenticationForm): String? = gMessengerAuthAPI.login(loginPasswordModel)
}
