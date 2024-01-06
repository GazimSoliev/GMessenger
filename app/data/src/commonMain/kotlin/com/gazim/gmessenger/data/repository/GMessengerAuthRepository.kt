package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.api.model.Account
import com.gazim.gmessenger.api.model.LoginPassword
import com.gazim.gmessenger.api.repository.GMessengerAPI
import com.gazim.gmessenger.domain.model.IAccountModel
import com.gazim.gmessenger.domain.model.ILoginPasswordModel
import com.gazim.gmessenger.domain.repository.IGMessengerAuthRepository

class GMessengerAuthRepository : IGMessengerAuthRepository {
    override suspend fun register(accountModel: IAccountModel): Boolean =
        GMessengerAPI.register(
            account =
                Account(
                    nickname = accountModel.nickname,
                    username = accountModel.username,
                    login = accountModel.login,
                    password = accountModel.password,
                ),
        )

    override suspend fun login(loginPasswordModel: ILoginPasswordModel): String =
        GMessengerAPI.login(
            LoginPassword(
                login = loginPasswordModel.login,
                password = loginPasswordModel.password,
            ),
        )
}
