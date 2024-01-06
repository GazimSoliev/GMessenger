package com.gazim.gmessenger.presentation.features.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.component.ChatsComponent
import com.gazim.gmessenger.presentation.features.chat.ChatScreen
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.features.finduser.FindUserScreen
import com.gazim.gmessenger.presentation.features.login.LoginScreen
import com.gazim.gmessenger.presentation.features.user.UserScreen

class ChatsScreen : BaseScreen<ChatsState, ChatsSideEffect, ChatsAction, ChatsViewModel>(ChatsViewModel::class) {
    override suspend fun handleSideEffect(sideEffect: ChatsSideEffect) {
        when (sideEffect) {
            is ToChatScreen -> navigator.push(ChatScreen())
            is ToFindUser -> navigator.push(FindUserScreen())
            is ToAccountInfoScreen -> navigator.push(UserScreen())
            is ToLoginScreen -> navigator.replace(LoginScreen())
        }
    }

    @Composable
    override fun Screen() {
        ChatsComponent(
            modifier = Modifier.fillMaxSize(),
            chats = state.list,
            nextToChat = { sendAction(OnItemClick(it)) },
            logOut = { sendAction(OnLogOutClick) },
            createNewChat = { sendAction(OnCreateNewChat) },
            lookAtMyAccount = { sendAction(OnAccountInfoClick) },
        )
    }

    override fun onStart() = sendAction(OnStart)
}
