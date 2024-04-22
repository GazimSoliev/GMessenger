package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.model.AccountModel
import com.gazim.gmessenger.domain.model.ILoginPasswordModel

class GMessengerAuthServiceImpl(private val gMessengerAuthAPI: GMessengerAuthAPI) : GMessengerAuthService {
    override suspend fun register(accountModel: AccountModel): Boolean = gMessengerAuthAPI.register(accountModel)

    override suspend fun login(loginPasswordModel: ILoginPasswordModel): String = gMessengerAuthAPI.login(loginPasswordModel)
}
