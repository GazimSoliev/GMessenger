package com.gazim.gmessenger.presentation.features.chats

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.usecase.GetChatsUseCase
import com.gazim.gmessenger.domain.usecase.LogOutUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.model.toUI
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax

// todo: Take out actions
class ChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val logOutUseCase: LogOutUseCase,
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
                    logOutUseCase().onSuccess {
                        postSideEffect(ToLoginScreen)
                    }
                }
            }
        }
    }

    private suspend fun Syntax<ChatsState, ChatsSideEffect>.getChats() {
        getChatsUseCase()
            .onSuccess {
                reduce { state.copy(list = it.map(IChat::toUI)) }
            }.onFailure(Throwable::printStackTrace)
    }
}
