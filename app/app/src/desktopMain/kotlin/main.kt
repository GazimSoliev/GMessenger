import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gazim.gmessenger.di.scopeModule
import com.gazim.gmessenger.di.serviceModule
import com.gazim.gmessenger.di.useCaseModule
import com.gazim.gmessenger.di.viewModelModule
import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.presentation.App
import com.gazim.gmessenger.presentation.component.NotificationsWindow
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.logger.SLF4JLogger

val notificationsReceiver = MutableSharedFlow<INotificationModel>()

@DelicateCoroutinesApi
fun main() {
    startKoin {
        logger(SLF4JLogger(level = Level.INFO))
        modules(scopeModule, serviceModule, useCaseModule, viewModelModule)
    }
    application {
        val notifications = rememberSaveable(notificationsReceiver) { mutableStateListOf<INotificationModel>() }
        val lazyListState = rememberLazyListState()
        LaunchedEffect(notificationsReceiver) {
            notificationsReceiver.collect {
                println("Notification collected: $it")
                notifications.add(it)
                lazyListState.scrollToItem(0)
                launch {
                    delay(5000)
                    notifications.remove(it)
                    println("Notification removed: $it")
                }
            }
        }
        NotificationsWindow(
            notifications.asReversed(),
            lazyListState = lazyListState,
            visible = notifications.isNotEmpty(),
        )
        val windowState = rememberWindowState()
        val isMaximized = windowState.placement == WindowPlacement.Maximized
        Window(
            state = windowState,
            onCloseRequest = ::exitApplication,
            title = "GMessenger",
            transparent = true,
            undecorated = true,
        ) {
            GMessengerTheme {
                val corner = if (isMaximized) RoundedCornerShape(0) else shapes.large
                val border = if (isMaximized) Modifier else Modifier.border(1.dp, colorScheme.surfaceVariant, corner)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(corner)
                        .then(border)
                ) {
                    Surface {
                        Column {
                            WindowDraggableArea {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                        .background(colorScheme.outlineVariant.copy(alpha = 0.1f)).height(36.dp),
                                ) {
                                    Text("GMessenger", modifier = Modifier.align(Alignment.Center))
                                    Row(
                                        modifier = Modifier.align(Alignment.CenterEnd).padding(horizontal = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        FilledTonalIconButton(
                                            modifier = Modifier.size(24.dp),
                                            onClick = { windowState.isMinimized = !windowState.isMinimized },
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(16.dp),
                                                imageVector = Icons.Rounded.Remove,
                                                contentDescription = null
                                            )
                                        }
                                        FilledTonalIconButton(
                                            modifier = Modifier.size(24.dp),
                                            onClick = {
                                                windowState.placement =
                                                    if (windowState.placement == WindowPlacement.Maximized)
                                                        WindowPlacement.Floating else WindowPlacement.Maximized
                                            }
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(16.dp),
                                                imageVector = if (windowState.placement == WindowPlacement.Maximized)
                                                    Icons.Rounded.FullscreenExit else Icons.Rounded.Fullscreen,
                                                contentDescription = null
                                            )
                                        }
                                        FilledTonalIconButton(
                                            modifier = Modifier.size(24.dp),
                                            onClick = ::exitApplication
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(16.dp),
                                                imageVector = Icons.Rounded.Close,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                            App()
                        }
                    }
                }
            }
        }
    }
}

// fun main() = application {
//    Window(onCloseRequest = ::exitApplication, title = "GMessenger") {
//        App()
//    }
// }
//
// @Preview
// @Composable
// fun AppDesktopPreview() {
//    App()
// }
