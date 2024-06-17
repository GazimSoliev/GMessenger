package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.factrory.GMessengerAuthAPIFactory
import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.model.RegistrationForm

class GMessengerAuthServiceImpl(gMessengerAuthAPIFactory: GMessengerAuthAPIFactory) : GMessengerAuthService {
    private val gMessengerAuthAPI = gMessengerAuthAPIFactory()
    override suspend fun register(registrationForm: RegistrationForm): Boolean = gMessengerAuthAPI.register(registrationForm)

    override suspend fun login(loginPasswordModel: AuthenticationForm): String? = gMessengerAuthAPI.login(loginPasswordModel)
}
