@file:Suppress("SpellCheckingInspection")

package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.model.RegistrationForm
import com.gazim.gmessenger.api.GMessengerAuthAPI as GMAAPI
import com.gazim.gmessenger.api.model.RegistrationForm as RegistrationFormAPI
import com.gazim.gmessenger.domain.model.AuthenticationForm as AuthenticationFormAPI

class GMessengerAuthAPIImpl(
    host: String,
    isSecure: Boolean,
) : GMessengerAuthAPI {
    private val gMessengerAuthAPI = GMAAPI(host = host, isSecure = isSecure)

    override suspend fun register(registrationForm: RegistrationForm): Boolean =
        gMessengerAuthAPI.register(
            RegistrationFormAPI(
                nickname = registrationForm.nickname,
                username = registrationForm.username,
                login = registrationForm.login,
                password = registrationForm.password,
            ),
        )

    override suspend fun login(loginPasswordModel: AuthenticationFormAPI): String? =
        gMessengerAuthAPI
            .login(
                AuthenticationForm(
                    login = loginPasswordModel.login,
                    password = loginPasswordModel.password,
                ),
            ).token
}
