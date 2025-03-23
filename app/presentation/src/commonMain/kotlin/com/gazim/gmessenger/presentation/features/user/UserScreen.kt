package com.gazim.gmessenger.presentation.features.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.user.UserAction.*
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.PickPhoto
import com.gazim.gmessenger.presentation.features.user.UserSideEffect.ToBack
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserScreen(navController: NavHostController) {
    val viewModel = koinViewModel<UserViewModel>()
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val launcher =
        rememberFilePickerLauncher(
            type = FileKitType.Image,
            mode = FileKitMode.Single,
        ) { file ->
            file ?: return@rememberFilePickerLauncher
            scope.launch {
                val image = file.name to file.readBytes()
                val loadProfileImage = LoadProfileImage(image)
                viewModel.sendAction(loadProfileImage)
            }
        }
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is PickPhoto -> launcher.launch()
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
