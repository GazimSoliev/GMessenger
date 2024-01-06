package com.gazim.gmessenger.app.features.chats

import com.gazim.gmessenger.app.common.BaseViewModel
import com.gazim.gmessenger.app.features.chats.ChatsAction.*
import com.gazim.gmessenger.app.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.app.model.toChatModel
import com.gazim.gmessenger.app.model.toChatUI
import com.gazim.gmessenger.app.service.INotificationService
import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.usecase.ICloseAccountScopeUseCase
import com.gazim.gmessenger.domain.usecase.ICreateChatScopeUseCase
import com.gazim.gmessenger.domain.usecase.IGetChatsUseCase
import com.gazim.gmessenger.domain.usecase.ISendChatUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

// todo: Take out actions
class ChatsViewModel(
    private val getChatsUseCase: IGetChatsUseCase,
    private val sendChatUseCase: ISendChatUseCase,
    private val closeAccountScopeUseCase: ICloseAccountScopeUseCase,
    private val createChatScopeUseCase: ICreateChatScopeUseCase,
    private val notificationService: INotificationService,
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
                    sendChatUseCase(action.chat.toChatModel())
                    createChatScopeUseCase()
                    postSideEffect(ToChatScreen)
                }

                is OnCreateNewChat -> postSideEffect(ToFindUser)
                is OnAccountInfoClick -> postSideEffect(ToAccountInfoScreen)
                is OnLogOutClick -> {
                    postSideEffect(ToLoginScreen)
                    notificationService.stop()
                    closeAccountScopeUseCase()
                    destroyViewModel()
                }
            }
        }
    }

    private suspend fun SimpleSyntax<ChatsState, ChatsSideEffect>.getChats() {
        runCatching {
            getChatsUseCase()
        }.onSuccess {
            reduce { state.copy(list = it.map(IChatModel::toChatUI)) }
        }.onFailure(Throwable::printStackTrace)
    }
}
