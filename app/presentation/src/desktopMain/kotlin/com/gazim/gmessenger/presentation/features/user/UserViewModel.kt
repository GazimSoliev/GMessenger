package com.gazim.gmessenger.presentation.features.user

import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.user.UserAction.OnBack
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack
import com.gazim.gmessenger.domain.usecase.IGetOwnUser
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

// todo: Take out actions
class UserViewModel(private val getUserUseCase: IGetOwnUser) : BaseViewModel<UserState, UserSideEffect, UserAction>() {
    override fun handleAction(action: UserAction) {
        intent {
            when (action) {
                is OnBack -> {
                    postSideEffect(ToBack)
                    destroyViewModel()
                }
            }
        }
    }

    override val container: Container<UserState, UserSideEffect> =
        container(initialState = UserState()) {
            scope.launch {
                val user = getUserUseCase()
                reduce { UserState(nickname = user.nickname, username = "@${user.username}") }
            }
        }
}
