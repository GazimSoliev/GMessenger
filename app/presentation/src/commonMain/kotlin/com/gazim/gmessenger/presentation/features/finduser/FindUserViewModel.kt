package com.gazim.gmessenger.presentation.features.finduser

import com.gazim.gmessenger.domain.model.User
import com.gazim.gmessenger.domain.usecase.CreateChatUseCase
import com.gazim.gmessenger.domain.usecase.FilterUsersUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.finduser.FindUserAction.*
import com.gazim.gmessenger.presentation.features.finduser.FindUserSideEffect.ToChatsScreen
import com.gazim.gmessenger.presentation.model.toDomain
import com.gazim.gmessenger.presentation.model.toUI
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax

// todo: Take out actions
class FindUserViewModel(
    private val filterUsersUseCase: FilterUsersUseCase,
    private val createChatUseCase: CreateChatUseCase,
) : BaseViewModel<FindUserState, FindUserSideEffect, FindUserAction>() {
    override val container: Container<FindUserState, FindUserSideEffect> =
        container(initialState = FindUserState())

    override fun handleAction(action: FindUserAction) {
        intent {
            when (action) {
                is OnFilterChange -> findUser(action.query.also { reduce { state.copy(query = it) } }.text)
                is OnUserClick -> createChat(action.user.toDomain())
                is OnBackClick -> {
                    postSideEffect(ToChatsScreen)
                }
            }
        }
    }

    private suspend fun Syntax<FindUserState, FindUserSideEffect>.findUser(filter: String) {
        filterUsersUseCase(filter)
            .onSuccess {
                reduce { state.copy(users = it.map { it.toUI() }) }
            }.onFailure(Throwable::printStackTrace)
    }

    private suspend fun Syntax<FindUserState, FindUserSideEffect>.createChat(user: User) {
        createChatUseCase(user)
            .onSuccess {
                postSideEffect(ToChatsScreen)
            }.onFailure(Throwable::printStackTrace)
    }
}
