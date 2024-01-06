package com.gazim.gmessenger.app.features.user

import com.gazim.gmessenger.app.common.IAction
import com.gazim.gmessenger.app.common.ISideEffect
import com.gazim.gmessenger.app.common.IState

data class UserState(
    val nickname: String = "",
    val username: String = "",
) : IState

sealed interface UserSideEffect : ISideEffect {
    object ToBack : UserSideEffect
}

sealed interface UserAction : IAction {
    object OnBack : UserAction
}
