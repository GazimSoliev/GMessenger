package com.gazim.gmessenger.presentation.theme

import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

@Composable
actual fun isSystemInDarkTheme(): Boolean {
    var isDark by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (true) {
                isDark = currentSystemTheme == SystemTheme.DARK
                delay(100)
            }
        }
    }
    return isDark
}