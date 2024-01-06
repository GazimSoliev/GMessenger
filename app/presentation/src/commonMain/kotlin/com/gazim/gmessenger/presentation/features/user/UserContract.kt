package com.gazim.gmessenger.presentation.features.user

import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState

data class UserState(
    val nickname: String = "",
    val username: String = "",
) : IState

sealed interface UserSideEffect : ISideEffect {
    data object ToBack : UserSideEffect
}

sealed interface UserAction : IAction {
    data object OnBack : UserAction
}
