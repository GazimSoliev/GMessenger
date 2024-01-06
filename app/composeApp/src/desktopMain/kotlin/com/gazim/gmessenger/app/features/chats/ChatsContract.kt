package com.gazim.gmessenger.app.features.chats

import com.gazim.gmessenger.app.common.IAction
import com.gazim.gmessenger.app.common.ISideEffect
import com.gazim.gmessenger.app.common.IState
import com.gazim.gmessenger.app.model.IChatUI

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
