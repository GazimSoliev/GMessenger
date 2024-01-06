package com.gazim.gmessenger.app.features.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.app.common.BaseScreen
import com.gazim.gmessenger.app.component.AccountComponent
import com.gazim.gmessenger.app.features.user.UserAction.OnBack
import com.gazim.gmessenger.app.features.user.UserSideEffect.ToBack

class UserScreen : BaseScreen<UserState, UserSideEffect, UserAction, UserViewModel>(UserViewModel::class) {
    override suspend fun handleSideEffect(sideEffect: UserSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
        }
    }

    @Composable
    override fun Screen() {
        AccountComponent(
            modifier = Modifier.fillMaxSize(),
            nickname = state.nickname,
            username = state.username,
            back = { sendAction(OnBack) },
        )
    }
}
