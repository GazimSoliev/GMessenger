package com.gazim.gmessenger.presentation.features.chats

import androidx.compose.ui.graphics.painter.Painter
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.ChatUI

data class ChatsState(
    val list: List<ChatUI> = emptyList(),
    val profileImage: Painter? = null,
    val firstNameLetter: Char = ' ',
) : IState

sealed interface ChatsSideEffect : ISideEffect {
    data class ToChatScreen(
        val chat: ChatUI,
    ) : ChatsSideEffect

    data object ToFindUser : ChatsSideEffect

    data object ToAccountInfoScreen : ChatsSideEffect

    data object ToLoginScreen : ChatsSideEffect
}

sealed interface ChatsAction : IAction {
    data class OnItemClick(
        val chat: ChatUI,
    ) : ChatsAction

    data object OnCreateNewChat : ChatsAction

    data object OnAccountInfoClick : ChatsAction

    data object OnLogOutClick : ChatsAction
}
