@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.features.chats

import androidx.lifecycle.viewModelScope
import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.usecase.GetChatsUseCase
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.domain.usecase.GetOwnUser
import com.gazim.gmessenger.domain.usecase.LogOutUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.extensions.getPainter
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.model.toUI
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.uuid.ExperimentalUuidApi

// todo: Take out actions
class ChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getOwnAccountUseCase: GetOwnUser,
    private val getImageContentUseCase: GetImageContentUseCase,
) : BaseViewModel<ChatsState, ChatsSideEffect, ChatsAction>() {
    override val container: Container<ChatsState, ChatsSideEffect> =
        container(initialState = ChatsState()) {
            loadScreen()
        }

    override fun handleAction(action: ChatsAction) {
        intent {
            when (action) {
                is OnItemClick -> postSideEffect(ToChatScreen(action.chat))
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

    private fun Syntax<ChatsState, ChatsSideEffect>.loadScreen() {
        viewModelScope.launch {
            launch { loadProfile() }
            launch { loadChats() }
        }
    }

    private suspend fun Syntax<ChatsState, ChatsSideEffect>.loadProfile() {
        getOwnAccountUseCase().onSuccess { user ->
            val imageUuid = user.photo?.id
            val image = imageUuid?.let { imageId -> getImageContentUseCase.getPainter(imageId) }
            reduce {
                state.copy(
                    profileImage = image,
                    firstNameLetter = user.username.firstOrNull() ?: ' ',
                )
            }
        }.onFailure(Throwable::printStackTrace)
    }

    private suspend fun Syntax<ChatsState, ChatsSideEffect>.loadChats() {
        getChatsUseCase().onSuccess { chats ->
            reduce {
                state.copy(
                    list = chats.map(IChat::toUI)
                )
            }
        }.onFailure(Throwable::printStackTrace)
    }
}
