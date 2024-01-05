package com.gazim.gmessenger.domain.repository

import com.gazim.gmessenger.domain.model.IAccountModel
import com.gazim.gmessenger.domain.model.ILoginPasswordModel

interface IGMessengerAuthRepository {
    suspend fun register(accountModel: IAccountModel): Boolean

    suspend fun login(loginPasswordModel: ILoginPasswordModel): String
}
