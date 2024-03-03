package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.api.GMessengerAPI
import com.gazim.gmessenger.api.model.AuthenticationForm
import com.gazim.gmessenger.api.model.RegistrationForm
import com.gazim.gmessenger.domain.model.AccountModel
import com.gazim.gmessenger.domain.model.ILoginPasswordModel
import com.gazim.gmessenger.domain.service.IGMessengerAuthService

class GMessengerAuthService : IGMessengerAuthService {
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
