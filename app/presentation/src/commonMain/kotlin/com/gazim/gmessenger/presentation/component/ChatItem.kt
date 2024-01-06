package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

// todo: Rename a preview
@Composable
fun ChatItem(
    chatName: String,
    chatLink: String,
    onClickChat: () -> Unit,
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClickChat)) {
        Row(Modifier.padding(16.dp).height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(shape = CircleShape, modifier = Modifier.size(64.dp), border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Chat icon")
            }
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                Text(chatName)
                Text(chatLink)
            }
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    Column {
        ChatItem("Chat name", "@identifier") {}
        Spacer(Modifier.height(32.dp))
        ChatItem("Chat name", "") {}
    }
}

@Preview
@Composable
fun ChatItemPreviewWithTheme() {
    GMessengerTheme {
        ChatItemPreview()
    }
}
