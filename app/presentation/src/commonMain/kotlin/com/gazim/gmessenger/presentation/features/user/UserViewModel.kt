@file:OptIn(ExperimentalResourceApi::class)

package com.gazim.gmessenger.presentation.features.user

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.usecase.EditProfileFormUseCase
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.domain.usecase.GetOwnUser
import com.gazim.gmessenger.domain.usecase.UploadProfilePhotoUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.user.UserAction.*
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.PickPhoto
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.UserUI
import com.gazim.gmessenger.presentation.model.toUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax

private typealias IntentScope = Syntax<UserState, UserSideEffect>

// todo: Take out actions
@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
class UserViewModel(
    private val editProfileFormUseCase: EditProfileFormUseCase,
    private val getUserUseCase: GetOwnUser,
    private val uploadProfilePhotoUseCase: UploadProfilePhotoUseCase,
    private val getImageContentUseCase: GetImageContentUseCase,
) : BaseViewModel<UserState, UserSideEffect, UserAction>() {
    private var user = UserUI()

    override val container: Container<UserState, UserSideEffect> =
        container(initialState = UserState()) {
            viewModelScope.launch {
                runCatching {
                    updateUserProfile()
                }.onFailure(Throwable::printStackTrace)
            }
        }

    override fun handleAction(action: UserAction) {
        intent {
            when (action) {
                is OnNicknameChange -> reduce { state.copy(nicknameValue = action.value) }
                is OnUsernameChange -> reduce { state.copy(usernameValue = action.value) }
                is OnEditClick -> onEditClick()
                is OnSaveClick -> saveProfileChanges()
                is OnCancelClick -> reduce { state.copy(editMode = false) }
                is OnBack -> backClick()
                is UploadProfilePhoto -> postSideEffect(PickPhoto)
                is LoadProfileImage -> uploadProfilePhoto(action.image)
            }
        }
    }

    private fun IntentScope.uploadProfilePhoto(bytes: Pair<String, ByteArray>) {
        viewModelScope.launch(Dispatchers.IO) {
            val type = bytes.first.split(".").last()
            val image = uploadProfilePhotoUseCase(type, bytes.second).getOrNull() ?: return@launch
            val byteArray = getImageContentUseCase(image.id).getOrNull() ?: return@launch
            reduce { state.copy(imageBitmap = byteArray.decodeToImageBitmap()) }
        }
    }

    private fun IntentScope.updateUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val u = getUserUseCase().getOrNull() ?: return@launch
            user = u.toUI()
            reduce { state.copy(nickname = user.nickname, username = "@${user.username}") }
            val image = u.photo ?: return@launch
            val byteArray = getImageContentUseCase(image.id).getOrNull() ?: return@launch
            reduce { state.copy(imageBitmap = byteArray.decodeToImageBitmap()) }
        }
    }

    private fun IntentScope.saveProfileChanges() {
        viewModelScope.launch {
            reduce { state.copy(editMode = false) }
            editProfileFormUseCase(
                ProfileForm(
                    nickname = state.nicknameValue.text,
                    username = state.usernameValue.text,
                ),
            ).onSuccess {
                updateUserProfile()
            }
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
    }
}
