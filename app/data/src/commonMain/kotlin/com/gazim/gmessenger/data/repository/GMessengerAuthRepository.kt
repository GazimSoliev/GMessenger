package com.gazim.gmessenger.data.repository

import com.gazim.api.gmessenger.model.Account
import com.gazim.api.gmessenger.model.LoginPassword
import com.gazim.api.gmessenger.repository.GMessengerAPI
import com.gazim.gmessenger.client.pc.domain.model.IAccountModel
import com.gazim.gmessenger.client.pc.domain.model.ILoginPasswordModel
import com.gazim.gmessenger.client.pc.domain.repository.IGMessengerAuthRepository

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
