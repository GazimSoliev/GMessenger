package com.gazim.gmessenger.presentation.features.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.user.UserAction.*
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.PickPhoto
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack
import com.gazim.gmessenger.utils.rememberPhotoPicker
import kotlinx.coroutines.cancel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun UserScreen(navController: NavHostController) {
    val viewModel = koinViewModel<UserViewModel>()
    val state by viewModel.collectAsState()
    val photoPicker = rememberPhotoPicker { viewModel.sendAction(LoadProfileImage(it)) }
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is PickPhoto -> photoPicker.pick()
            is ToBack -> {
                navController.popBackStack()
                cancel()
            }
        }
    }
    AccountComposition(
        modifier = Modifier.fillMaxSize(),
        nickname = state.nickname,
        username = state.username,
        nicknameValue = state.nicknameValue,
        usernameValue = state.usernameValue,
        editMode = state.editMode,
        imageBitmap = state.imageBitmap,
        onNicknameChange = { viewModel.sendAction(OnNicknameChange(it)) },
        onUsernameChange = { viewModel.sendAction(OnUsernameChange(it)) },
        onEditClick = { viewModel.sendAction(OnEditClick) },
        onCancelClick = { viewModel.sendAction(OnCancelClick) },
        onSaveClick = { viewModel.sendAction(OnSaveClick) },
        uploadNewPhoto = { viewModel.sendAction(UploadProfilePhoto) },
        back = { viewModel.sendAction(OnBack) },
    )
}
