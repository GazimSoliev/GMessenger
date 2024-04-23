package com.gazim.gmessenger.presentation.features.finduser

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IUserUI

data class FindUserState(
    val query: TextFieldValue = TextFieldValue(),
    val users: List<IUserUI> = emptyList(),
) : IState

sealed interface FindUserSideEffect : ISideEffect {
    data object ToChatsScreen : FindUserSideEffect
}

sealed interface FindUserAction : IAction {
    data class OnFilterChange(val query: TextFieldValue) : FindUserAction

    data class OnUserClick(val user: IUserUI) : FindUserAction

    data object OnBackClick : FindUserAction
}
