@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: Rename a preview
@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatItem(
    title: String = "",
    link: String = "",
    lastMessage: String = "",
    image: Uuid? = null,
    firstNameLetter: Char = ' ',
    onClickChat: () -> Unit = {},
    getImage: suspend (Uuid) -> Painter? = { null },
) {
    var chatImage by remember { mutableStateOf<Painter?>(null) }
    if (image != null) {
        LaunchedEffect(image) {
            launch(Dispatchers.IO) {
                chatImage = getImage(image) ?: return@launch
            }
        }
    }
    val opacity = 0.8f
    val modifierWithOpacity = Modifier.graphicsLayer {
        alpha = opacity
    }
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClickChat)
                .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ProfileIcon(
            modifier = Modifier.padding(vertical = 4.dp),
            image = chatImage,
            firstNameLetter = firstNameLetter,
        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    modifier = modifierWithOpacity,
                    text = "27 Mar",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().then(modifierWithOpacity),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.labelSmall
                )
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    Surface {
        Column {
            ChatItem(
                title = "GMessenger",
                link = "@gmessenger",
                lastMessage = "Hello, how are you?",
                firstNameLetter = 'G'
            )
            Spacer(Modifier.height(32.dp))
            ChatItem(
                title = "Messenger",
                firstNameLetter = 'M'
            )
        }
    }
}

@Preview
@Composable
fun ChatItemPreviewWithTheme() {
    GMessengerTheme(true) {
        ChatItemPreview()
    }
}
