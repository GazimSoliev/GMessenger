package com.gazim.gmessenger.presentation.component

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

// todo: Rename a preview and change a composition, better is to use system API
@Composable
fun NotificationsView(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    padding: Dp = 16.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit = {},
) {
    Surface(modifier = modifier.then(Modifier.padding(padding)), shape = shape, color = color) {
        Column {
            Text("GMessenger Notifications", Modifier.padding(16.dp))
            content()
        }
    }
}

@Preview
@Composable
private fun NotificationsViewPreview() {
    NotificationsView(Modifier.size(512.dp), color = MaterialTheme.colorScheme.primaryContainer) {
        NotificationStack(List(3) { it }) {
            NotificationRow(sender = "User", msg = "Hello World")
        }
    }
}

@Preview
@Composable
private fun NotificationsViewPreviewWithTheme() {
    GMessengerTheme {
        NotificationsViewPreview()
    }
}
