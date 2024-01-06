package com.gazim.gmessenger.app.features.finduser

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.app.common.IAction
import com.gazim.gmessenger.app.common.ISideEffect
import com.gazim.gmessenger.app.common.IState
import com.gazim.gmessenger.domain.model.IUserModel

data class FindUserState(
    val query: TextFieldValue = TextFieldValue(),
    val users: List<IUserModel> = emptyList(),
) : IState

sealed interface FindUserSideEffect : ISideEffect {
    object ToChatsScreen : FindUserSideEffect
}

sealed interface FindUserAction : IAction {
    data class OnFilterChange(val query: TextFieldValue) : FindUserAction

    data class OnUserClick(val user: IUserModel) : FindUserAction

    object OnBackClick : FindUserAction
}
