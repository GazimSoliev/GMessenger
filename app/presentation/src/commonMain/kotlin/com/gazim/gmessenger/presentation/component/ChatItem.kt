@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
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
    onClickChat: () -> Unit,
) {
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    if (image != null) {
        val getImageContentUseCase = koinInject<GetImageContentUseCase>()
        LaunchedEffect(image) {
            launch(Dispatchers.IO) {
                getImageContentUseCase(image).onSuccess {
                    bitmap = it.decodeToImageBitmap()
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
        Surface(
            shape = CircleShape,
            modifier = Modifier.padding(vertical = 2.dp).size(48.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
        ) {
            val imageBitmap = bitmap
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    modifier = Modifier.fillMaxSize().blur(1.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Image(
                    bitmap = imageBitmap,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
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
