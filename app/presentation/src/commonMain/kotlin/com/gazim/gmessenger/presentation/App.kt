package com.gazim.gmessenger.presentation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.gazim.gmessenger.domain.usecase.PassAuthUseCase
import com.gazim.gmessenger.presentation.navigation.Navigation
import org.koin.compose.koinInject

@Composable
fun App() {
    Surface {
        Navigation(passAuthUseCase = koinInject<PassAuthUseCase>())
    }
}
