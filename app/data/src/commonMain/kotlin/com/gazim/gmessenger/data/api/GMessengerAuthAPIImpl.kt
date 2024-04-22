package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.api.GMessengerAPI
import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.model.RegistrationForm
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.model.AccountModel
import com.gazim.gmessenger.domain.model.ILoginPasswordModel

class GMessengerAuthAPIImpl : GMessengerAuthAPI {
    override suspend fun register(accountModel: AccountModel): Boolean =
        GMessengerAPI.register(
            account =
                RegistrationForm(
                    nickname = accountModel.nickname,
                    username = accountModel.username,
                    login = accountModel.login,
                    password = accountModel.password,
                ),
        )

    override suspend fun login(loginPasswordModel: ILoginPasswordModel): String =
        GMessengerAPI.login(
            AuthenticationForm(
                login = loginPasswordModel.login,
                password = loginPasswordModel.password,
            ),
        )
}
