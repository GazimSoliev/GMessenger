package com.gazim.gmessenger.presentation.features.chats

import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IChatUI

data class ChatsState(val list: List<IChatUI> = emptyList()) : IState

sealed interface ChatsSideEffect : ISideEffect {
    object ToChatScreen : ChatsSideEffect

    object ToFindUser : ChatsSideEffect

    object ToAccountInfoScreen : ChatsSideEffect

    object ToLoginScreen : ChatsSideEffect
}

sealed interface ChatsAction : IAction {
    data class OnItemClick(val chat: IChatUI) : ChatsAction

    object OnCreateNewChat : ChatsAction

    object OnAccountInfoClick : ChatsAction

    object OnLogOutClick : ChatsAction

    object OnStart : ChatsAction
}
