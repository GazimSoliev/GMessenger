package com.gazim.gmessenger.presentation.features.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.chats.ChatsAction.*
import com.gazim.gmessenger.presentation.features.chats.ChatsSideEffect.*
import com.gazim.gmessenger.presentation.navigation.Screen
import com.gazim.gmessenger.presentation.navigation.navigate
import com.gazim.gmessenger.presentation.navigation.replace
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ChatsScreen(navController: NavController) {
    val viewModel = koinViewModel<ChatsViewModel>()
    val state by viewModel.collectAsState()
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is ToChatScreen -> navController.navigate(Screen.Chat, sideEffect.chat)
            is ToFindUser -> navController.navigate(Screen.FindUser)
            is ToAccountInfoScreen -> navController.navigate(Screen.User)
            is ToLoginScreen -> navController.replace(Screen.Login)
        }
    }
    ChatsComposition(
        modifier = Modifier.fillMaxSize(),
        chats = state.list,
        nextToChat = { viewModel.sendAction(OnItemClick(it)) },
        logOut = { viewModel.sendAction(OnLogOutClick) },
        createNewChat = { viewModel.sendAction(OnCreateNewChat) },
        lookAtMyAccount = { viewModel.sendAction(OnAccountInfoClick) },
    )
}
