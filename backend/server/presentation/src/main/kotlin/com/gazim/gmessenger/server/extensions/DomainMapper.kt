package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.backend.common.model.IAccountPresent
import com.gazim.gmessenger.backend.common.model.ILoginPasswordPresent
import com.gazim.gmessenger.backend.common.model.IUserPresent
import com.gazim.gmessenger.backend.common.model.UserPresent
import com.gazim.gmessenger.server.domain.model.*

fun ILoginPasswordPresent.toDomain(): ILoginPassword =
    LoginPassword(
        login = login,
        password = password
    )

fun IAccountPresent.toDomain(): IAccount =
    Account(
        nickname = nickname,
        username = username,
        login = login,
        password = password
    )

fun IUser.toPresent(): IUserPresent =
    UserPresent(
        nickname = nickname,
        username = username
    )