package com.gazim.gmessenger.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

private val isDarkTheme = MutableStateFlow(false)
private var activeJob: Job? = null

@OptIn(DelicateCoroutinesApi::class)
private fun observeSystemTheme() {
    activeJob =
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                isDarkTheme.value = currentSystemTheme == SystemTheme.DARK
                delay(100)
            }
        }
}

private fun checkAndObserveSystemTheme() {
    if (activeJob?.isActive == true) return
    synchronized(Unit) {
        if (activeJob?.isActive == true) return
        observeSystemTheme()
    }
}

@Composable
actual fun isSystemInDarkTheme(): Boolean {
    checkAndObserveSystemTheme()
    val isDark by isDarkTheme.collectAsState()
    return isDark
}
