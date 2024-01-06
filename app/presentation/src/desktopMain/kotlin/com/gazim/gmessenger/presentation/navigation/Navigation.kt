package com.gazim.gmessenger.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import com.gazim.gmessenger.presentation.features.chats.ChatsScreen
import com.gazim.gmessenger.presentation.features.login.LoginScreen
import com.gazim.gmessenger.domain.usecase.IPassAuthUseCase

@Composable
fun Navigation(passAuthUseCase: IPassAuthUseCase) {
    Navigator(if (passAuthUseCase()) ChatsScreen() else LoginScreen()) {
        ScreenTransition(
            navigator = it,
            modifier = Modifier.fillMaxSize(),
            content = { s -> s.Content() },
            transition = {
                val (initialOffset, targetOffset) =
                    when (it.lastEvent) {
                        StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                        else -> ({ size: Int -> size }) to ({ size: Int -> -size })
                    }
                when {
                    initialState is LoginScreen && targetState is ChatsScreen ->
                        scaleIn(initialScale = 0f) + fadeIn() togetherWith
                            scaleOut(targetScale = 2f) + fadeOut()

                    initialState is ChatsScreen && targetState is LoginScreen ->
                        scaleIn(initialScale = 2f) + fadeIn() togetherWith
                            scaleOut(targetScale = 0f) + fadeOut()

                    else ->
                        slideInHorizontally(initialOffsetX = initialOffset) + fadeIn() togetherWith
                            slideOutHorizontally(targetOffsetX = targetOffset) + fadeOut()
                }
            },
        )
    }
}
