package com.gazim.gmessenger.presentation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.gazim.gmessenger.domain.usecase.IPassAuthUseCase
import com.gazim.gmessenger.presentation.navigation.Navigation
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    GMessengerTheme {
        Surface {
            Navigation(passAuthUseCase = koinInject<IPassAuthUseCase>())
        }
    }
}
