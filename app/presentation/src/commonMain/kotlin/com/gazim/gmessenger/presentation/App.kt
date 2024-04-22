package com.gazim.gmessenger.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.gazim.gmessenger.domain.usecase.PassAuthUseCase
import com.gazim.gmessenger.presentation.component.SplashComposition
import com.gazim.gmessenger.presentation.navigation.Navigation
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun App() {
    var showSplash by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2_000)
        showSplash = false
    }
    Surface {
        Navigation(passAuthUseCase = koinInject<PassAuthUseCase>())
        AnimatedVisibility(visible = showSplash, exit = fadeOut()) {
            SplashComposition()
        }
    }
}
