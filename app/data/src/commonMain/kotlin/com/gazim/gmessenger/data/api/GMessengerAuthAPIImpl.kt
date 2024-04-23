package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.api.GMessengerAPI
import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.api.model.RegistrationForm as RegistrationFormAPI
import com.gazim.gmessenger.domain.model.AuthenticationForm as AuthenticationFormAPI

class GMessengerAuthAPIImpl : GMessengerAuthAPI {
    override suspend fun register(registrationForm: com.gazim.gmessenger.domain.model.RegistrationForm): Boolean =
        GMessengerAPI.register(
            RegistrationFormAPI(
                nickname = registrationForm.nickname,
                username = registrationForm.username,
                login = registrationForm.login,
                password = registrationForm.password,
            ),
        )

    override suspend fun login(loginPasswordModel: AuthenticationFormAPI): String =
        GMessengerAPI.login(
            AuthenticationForm(
                login = loginPasswordModel.login,
                password = loginPasswordModel.password,
            ),
        )
}
