package com.gazim.gmessenger.presentation.features.finduser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.finduser.FindUserAction.*
import com.gazim.gmessenger.presentation.features.finduser.FindUserSideEffect.ToChatsScreen
import kotlinx.coroutines.cancel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun FindUserScreen(navController: NavHostController) {
    val viewModel = koinViewModel<FindUserViewModel>()
    val state by viewModel.collectAsState()
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is ToChatsScreen -> {
                navController.popBackStack()
                cancel()
            }
        }
    }
    FindUserComposition(
        modifier = Modifier.fillMaxSize(),
        users = state.users,
        query = state.query,
        onQueryChange = { query -> viewModel.sendAction(OnFilterChange(query)) },
        createChat = { userId -> viewModel.sendAction(OnUserClick(userId)) },
        back = { viewModel.sendAction(OnBackClick) },
    )
}
