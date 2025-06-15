package com.gazim.gmessenger.presentation.features.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.extensions.getPainter
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.navigation.*
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ChatsScreen(navController: NavController) {
    val viewModel = koinViewModel<ChatsViewModel>()
    val getImageContentUseCase = koinInject<GetImageContentUseCase>()
    val state by viewModel.collectAsState()
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is ToChatScreen -> navController.navigate(ChatRoute(sideEffect.chat))
            is ToFindUser -> navController.navigate(FindUserRoute())
            is ToAccountInfoScreen -> navController.navigate(UserRoute())
            is ToLoginScreen -> navController.replace(LoginRoute())
        }
    }
    ChatsComposition(
        modifier = Modifier.fillMaxSize(),
        profileImage = state.profileImage,
        firstNameLetter = state.firstNameLetter,
        chats = state.list,
        openChat = { chat -> viewModel.sendAction(OnItemClick(chat)) },
        logOut = { viewModel.sendAction(OnLogOutClick) },
        createNewChat = { viewModel.sendAction(OnCreateNewChat) },
        lookAtMyAccount = { viewModel.sendAction(OnAccountInfoClick) },
        getChatImage = { imageUuid -> getImageContentUseCase.getPainter(imageUuid) },
    )
}
