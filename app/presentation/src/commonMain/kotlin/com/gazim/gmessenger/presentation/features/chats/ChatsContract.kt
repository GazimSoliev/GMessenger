package com.gazim.gmessenger.presentation.features.chats

import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IChatUI

data class ChatsState(val list: List<IChatUI> = emptyList()) : IState

sealed interface ChatsSideEffect : ISideEffect {
    data class ToChatScreen(val chat: IChatUI) : ChatsSideEffect

    data object ToFindUser : ChatsSideEffect

    data object ToAccountInfoScreen : ChatsSideEffect

    data class ToLoginScreen(
        val session: String,
    ) : ChatsSideEffect
}

sealed interface ChatsAction : IAction {
    data class OnItemClick(val chat: IChatUI) : ChatsAction

    data object OnCreateNewChat : ChatsAction

    data object OnAccountInfoClick : ChatsAction

    data object OnLogOutClick : ChatsAction

    data object OnStart : ChatsAction
}
