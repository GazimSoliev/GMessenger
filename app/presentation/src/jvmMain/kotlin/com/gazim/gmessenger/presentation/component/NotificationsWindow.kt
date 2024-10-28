package com.gazim.gmessenger.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.domain.model.Notification
import com.gazim.gmessenger.domain.model.NotificationMessage
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

// todo: Use system API?
@Composable
fun NotificationsWindow(
    notifications: List<Notification>,
    lazyListState: LazyListState = rememberLazyListState(),
    visible: Boolean = true,
) {
    if (!visible) return
    RenderOnTopWindows(alignment = Alignment.BottomEnd) {
        GMessengerTheme {
            NotificationsView(Modifier.size(320.dp)) {
                NotificationStack(notifications = notifications, lazyListState = lazyListState) {
                    if (it is NotificationMessage) {
                        NotificationRow(
                            sender = it.user.nickname,
                            msg = it.message,
                        )
                    }
                }
            }
        }
    }
}
