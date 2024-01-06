package com.gazim.gmessenger.app.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.app.theme.GMessengerTheme

// todo: Rename a preview and change a composition?
@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: String,
    nickname: String,
    sentAt: String,
) {
    Card(
        modifier,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(message)
            Spacer(Modifier.height(8.dp))
            Text(
                "$sentAt, $nickname",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
fun MessageItemPreview() {
    MessageItem(
        message = "Hello",
        nickname = "Test",
        sentAt = "11:00 AM",
    )
}

@Preview
@Composable
fun MessageItemPreviewWitheTheme() {
    GMessengerTheme {
        Surface(Modifier.fillMaxSize()) {
            Box {
                MessageItem(
                    message = "Hello",
                    nickname = "Test",
                    sentAt = "11:00 AM",
                )
            }
        }
    }
}
