package com.gazim.gmessenger.presentation.features.finduser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.features.finduser.FindUserAction.*
import com.gazim.gmessenger.presentation.features.finduser.FindUserSideEffect.ToChatsScreen

class FindUserScreen : BaseScreen<FindUserState, FindUserSideEffect, FindUserAction, FindUserViewModel>() {
    override suspend fun handleSideEffect(sideEffect: FindUserSideEffect) {
        when (sideEffect) {
            is ToChatsScreen -> navigator.pop()
        }
    }

    @Composable
    override fun createViewModel(): FindUserViewModel = getScreenModel<FindUserViewModel>()

    @Composable
    override fun Screen() {
        FindUserComposition(
            modifier = Modifier.fillMaxSize(),
            users = state.users,
            query = state.query,
            onQueryChange = { sendAction(OnFilterChange(it)) },
            createChat = { sendAction(OnUserClick(it)) },
            back = { sendAction(OnBackClick) },
        )
    }
}
