package com.gazim.gmessenger.presentation.features.chats

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.usecase.GetChatsUseCase
import com.gazim.gmessenger.domain.usecase.GetSessionUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.model.toChatUI
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

// todo: Take out actions
class ChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val getSessionUseCase: GetSessionUseCase,
//    private val notificationService: INotificationService,
) : BaseViewModel<ChatsState, ChatsSideEffect, ChatsAction>() {
    override val container: Container<ChatsState, ChatsSideEffect> =
        container(initialState = ChatsState()) {
            getChats()
        }

    override fun handleAction(action: ChatsAction) {
        intent {
            when (action) {
                is OnStart -> getChats()
                is OnItemClick -> {
                    postSideEffect(ToChatScreen(action.chat))
                }

                is OnCreateNewChat -> postSideEffect(ToFindUser)
                is OnAccountInfoClick -> postSideEffect(ToAccountInfoScreen)
                is OnLogOutClick -> {
                    val session = getSessionUseCase() ?: return@intent
                    postSideEffect(ToLoginScreen(session))
                    destroyViewModel()
                }
            }
        }
    }

    private suspend fun SimpleSyntax<ChatsState, ChatsSideEffect>.getChats() {
        runCatching {
            getChatsUseCase()
        }.onSuccess {
            reduce { state.copy(list = it.map(IChat::toChatUI)) }
        }.onFailure(Throwable::printStackTrace)
    }
}
