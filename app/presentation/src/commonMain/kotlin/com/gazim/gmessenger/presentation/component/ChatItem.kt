@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: Rename a preview
@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatItem(
    chatName: String = "",
    chatLink: String = "",
    image: Uuid? = null,
    firstNameLetter: Char = ' ',
    onClickChat: () -> Unit = {},
) {
    var profileImage by remember { mutableStateOf<Painter?>(null) }
    if (image != null) {
        val getImageContentUseCase = koinInject<GetImageContentUseCase>()
        LaunchedEffect(image) {
            launch(Dispatchers.IO) {
                getImageContentUseCase(image).onSuccess { bytes ->
                    val imageBitmap = bytes.decodeToImageBitmap()
                    profileImage = BitmapPainter(imageBitmap)
                }
            }
        }
    }
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClickChat)
                .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ProfileIcon(
            modifier = Modifier.padding(vertical = 4.dp),
            image = profileImage,
            firstNameLetter = firstNameLetter,
        )
        Column(modifier = Modifier) {
            Text(
                text = chatName,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = chatLink,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    Column {
        ChatItem(
            chatName = "GMessenger",
            chatLink = "@gmessenger",
            firstNameLetter = 'G'
        )
        Spacer(Modifier.height(32.dp))
        ChatItem(
            chatName = "Messenger",
            firstNameLetter = 'M'
        )
    }
}

@Preview
@Composable
fun ChatItemPreviewWithTheme() {
    GMessengerTheme {
        ChatItemPreview()
    }
}
