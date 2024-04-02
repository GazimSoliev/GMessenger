import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.gazim.gmessenger.di.scopeModule
import com.gazim.gmessenger.di.serviceModule
import com.gazim.gmessenger.di.useCaseModule
import com.gazim.gmessenger.di.viewModelModule
import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.presentation.App
import com.gazim.gmessenger.presentation.component.NotificationsWindow
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
        Window(
            onCloseRequest = ::exitApplication,
            title = "GMessenger",
            transparent = true,
            undecorated = true,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                App()
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
