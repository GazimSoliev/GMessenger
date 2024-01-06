package com.gazim.gmessenger.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// todo: remove it if it's not needed in the future
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AutoCleanableNotificationStack(
    notificationsFlow: Flow<T>,
    isEmpty: (Boolean) -> Unit,
    notificationTimeout: Long = 5000,
    content: @Composable (T) -> Unit,
) {
    val notifications = rememberSaveable(notificationsFlow) { mutableStateListOf<T>() }
    LaunchedEffect(notificationsFlow) {
        launch {
            notificationsFlow.collect {
                println("Notification collected: $it")
                notifications.add(it)
                launch {
                    delay(notificationTimeout)
                    notifications.remove(it)
                    isEmpty(notifications.isEmpty())
                    println("Notification removed: $it")
                }
            }
        }
    }
    NotificationStack(notifications, reverseLayout = false) {
        println("Rendering: $it")
        Box(Modifier.animateItemPlacement()) {
            content(it)
        }
    }
}
