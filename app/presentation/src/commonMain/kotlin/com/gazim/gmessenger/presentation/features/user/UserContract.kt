package com.gazim.gmessenger.presentation.features.user

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState

data class UserState(
    val nickname: String = "",
    val username: String = "",
    val nicknameValue: TextFieldValue = TextFieldValue(),
    val usernameValue: TextFieldValue = TextFieldValue(),
    val editMode: Boolean = false,
    val imageBitmap: ImageBitmap? = null,
) : IState

sealed interface UserSideEffect : ISideEffect {
    data object ToBack : UserSideEffect
}

sealed interface UserAction : IAction {
    data object OnBack : UserAction

    data class OnNicknameChange(val value: TextFieldValue) : UserAction

    data class OnUsernameChange(val value: TextFieldValue) : UserAction

    data object OnEditClick : UserAction

    data object OnCancelClick : UserAction

    data object OnSaveClick : UserAction

    data object UploadProfilePhoto : UserAction
}
