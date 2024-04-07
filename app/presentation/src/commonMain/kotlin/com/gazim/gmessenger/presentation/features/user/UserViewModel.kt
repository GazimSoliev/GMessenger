package com.gazim.gmessenger.presentation.features.user

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.usecase.EditProfileFormUseCase
import com.gazim.gmessenger.domain.usecase.IGetOwnUser
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.user.UserAction.*
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.IUserUI
import com.gazim.gmessenger.presentation.model.UserUI
import com.gazim.gmessenger.presentation.model.toUserUI
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

typealias IntentScope = SimpleSyntax<UserState, UserSideEffect>

// todo: Take out actions
class UserViewModel(
    private val editProfileFormUseCase: EditProfileFormUseCase,
    private val getUserUseCase: IGetOwnUser
) : BaseViewModel<UserState, UserSideEffect, UserAction>() {

    private var user: IUserUI = UserUI()

    override fun handleAction(action: UserAction) {
        intent {
            when (action) {
                is OnNicknameChange -> reduce { state.copy(nicknameValue = action.value) }
                is OnUsernameChange -> reduce { state.copy(usernameValue = action.value) }
                is OnEditClick -> onEditClick()
                is OnSaveClick -> saveProfileChanges()
                is OnCancelClick -> reduce { state.copy(editMode = false) }
                is OnBack -> backClick()
            }
        }
    }

    override val container: Container<UserState, UserSideEffect> =
        container(initialState = UserState()) {
            viewModelScope.launch {
                runCatching {
                    updateUserProfile()
                }.onFailure(Throwable::printStackTrace)
            }
        }

    private suspend fun IntentScope.updateUserProfile() {
        user = getUserUseCase().toUserUI()
        reduce { state.copy(nickname = user.nickname, username = "@${user.username}") }
    }

    private fun IntentScope.saveProfileChanges() {
        viewModelScope.launch {
            runCatching {
                reduce { state.copy(editMode = false) }
                editProfileFormUseCase(
                    ProfileForm(
                        nickname = state.nicknameValue.text,
                        username = state.usernameValue.text
                    )
                )
                updateUserProfile()
            }.onFailure(Throwable::printStackTrace)
        }
    }

    private suspend fun IntentScope.onEditClick() {
        reduce {
            state.copy(
                editMode = true,
                nicknameValue = TextFieldValue(user.nickname),
                usernameValue = TextFieldValue(user.username),
            )
        }
    }

    private suspend fun IntentScope.backClick() {
        postSideEffect(ToBack)
        destroyViewModel()
    }
}
