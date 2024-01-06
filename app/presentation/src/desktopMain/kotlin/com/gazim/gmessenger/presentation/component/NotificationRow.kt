package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

// todo: Rename a preview and change a design
@Composable
fun NotificationRow(
    modifier: Modifier = Modifier,
    sender: String = "",
    msg: String = "",
) {
    Card(modifier = modifier.then(Modifier.fillMaxWidth())) {
        Column(Modifier.padding(16.dp)) {
            Text(sender, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(msg, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview
@Composable
private fun NotificationPreview() {
    NotificationRow(sender = "Gazim", msg = "hello world")
}

@Preview
@Composable
private fun NotificationPreviewWithTheme() {
    GMessengerTheme {
        NotificationRow(sender = "Gazim", msg = "hello world")
    }
}
