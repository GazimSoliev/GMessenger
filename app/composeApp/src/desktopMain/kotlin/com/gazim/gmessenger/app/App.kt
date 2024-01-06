package com.gazim.gmessenger.app

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.gazim.gmessenger.app.navigation.Navigation
import com.gazim.gmessenger.app.theme.GMessengerTheme
import com.gazim.gmessenger.domain.usecase.IPassAuthUseCase
import org.koin.compose.koinInject

@Composable
fun App() {
    GMessengerTheme {
        Surface {
            Navigation(passAuthUseCase = koinInject<IPassAuthUseCase>())
        }
    }
}
