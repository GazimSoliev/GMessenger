package com.gazim.gmessenger.presentation.features.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.features.user.UserAction.*
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack

class UserScreen : BaseScreen<UserState, UserSideEffect, UserAction, UserViewModel>() {
    override suspend fun handleSideEffect(sideEffect: UserSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
        }
    }

    @Composable
    override fun createViewModel(): UserViewModel = getScreenModel<UserViewModel>()

    @Composable
    override fun Screen() {
        AccountComposition(
            modifier = Modifier.fillMaxSize(),
            nickname = state.nickname,
            username = state.username,
            nicknameValue = state.nicknameValue,
            usernameValue = state.usernameValue,
            editMode = state.editMode,
            imageBitmap = state.imageBitmap,
            onNicknameChange = { sendAction(OnNicknameChange(it)) },
            onUsernameChange = { sendAction(OnUsernameChange(it)) },
            onEditClick = { sendAction(OnEditClick) },
            onCancelClick = { sendAction(OnCancelClick) },
            onSaveClick = { sendAction(OnSaveClick) },
            uploadNewPhoto = { sendAction(UploadProfilePhoto) },
            back = { sendAction(OnBack) },
        )
    }
}
