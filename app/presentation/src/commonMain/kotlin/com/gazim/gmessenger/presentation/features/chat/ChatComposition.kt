@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.gazim.gmessenger.presentation.component.MessageItem
import com.gazim.gmessenger.presentation.model.*
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: Rename preview and maybe change a composition
@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun ChatComposition(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    imageBitmap: ImageBitmap? = null,
    chatTitle: String,
    messages: Flow<PagingData<MessageItemUI>>,
    message: TextFieldValue,
    showReconnectScreen: Boolean,
    reconnectionTimerSeconds: Int,
    onMessageChange: (TextFieldValue) -> Unit,
    sendMsg: () -> Unit,
    onFollowMessage: (Boolean) -> Unit,
    back: () -> Unit,
) {
    val strBack = stringResource(Res.string.back)
    val strReconnectIn = stringResource(Res.string.reconnect_in)
    val strSend = stringResource(Res.string.send)
    val strMessage = stringResource(Res.string.message)
    val pagingMessages = messages.collectAsLazyPagingItems()
    val timeFormatter = remember { LocalDateTime.Format { byUnicodePattern("HH:mm") } }
    val dateFormatter = remember { LocalDate.Format { byUnicodePattern("dd.MM.yyyy") } }
    val followAddingNewMsg by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    LaunchedEffect(followAddingNewMsg) { onFollowMessage(followAddingNewMsg) }
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                modifier = Modifier.size(32.dp),
                                border = BorderStroke(1.dp, colorScheme.outline),
                            ) {
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
                            Spacer(Modifier.width(8.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = chatTitle,
                                    modifier = Modifier.background(colorScheme.surface),
                                )
                                AnimatedVisibility(visible = showReconnectScreen) {
                                    Text(
                                        text = "$strReconnectIn $reconnectionTimerSeconds",
                                        style = typography.labelSmall,
                                        modifier = Modifier.padding(top = 4.dp),
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = back) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = strBack,
                            )
                        }
                    },
                    colors =
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorScheme.surface.copy(alpha = 0.95f),
                        ),
                )
            },
            bottomBar = {
                BasicTextField(
                    value = message,
                    onValueChange = onMessageChange,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(colorScheme.surface.copy(alpha = 0.95f))
                            .padding(8.dp)
                            .navigationBarsPadding()
                            .background(colorScheme.surfaceVariant, RoundedCornerShape(24.dp))
                            .onPreviewKeyEvent {
                                if (it.key != Key.Enter || it.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false
                                sendMsg()
                                true
                            },
                    textStyle = typography.bodyLarge.copy(color = colorScheme.onSurfaceVariant),
                    cursorBrush = SolidColor(colorScheme.onBackground),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier =
                                Modifier
                                    .padding(
                                        start = 24.dp,
                                        end = 8.dp,
                                        top = 8.dp,
                                        bottom = 8.dp,
                                    ).weight(1f)
                                    .heightIn(max = 128.dp),
                        ) {
                            it()
                            if (message.text.isEmpty()) {
                                Text(
                                    text = strMessage,
                                    style = typography.bodyLarge,
                                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                )
                            }
                        }
                        IconButton(onClick = sendMsg) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Send,
                                contentDescription = strSend,
                                tint = colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            },
        ) { paddingValues ->
            val contentPadding =
                PaddingValues(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                reverseLayout = true,
                contentPadding = contentPadding,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    count = pagingMessages.itemCount,
                    key = {
                        when (val msg = pagingMessages[it]) {
                            is MessageUI -> msg.id
                            else -> msg.hashCode()
                        }
                    },
                ) { index ->
                    val msg = pagingMessages[index]
                    if (msg is GroupedMessagesDateUI) {
                        val groupedDate =
                            rememberSaveable(msg) { dateFormatter.format(msg.date) }
                        Text(
                            groupedDate,
                            modifier = Modifier.padding(16.dp),
                        )
                    } else if (msg is MessageUI) {
                        Box(Modifier.fillMaxWidth()) {
                            val msgModifier =
                                if (msg is YourMessageUI) {
                                    Modifier.align(Alignment.CenterEnd).padding(start = 64.dp)
                                } else {
                                    Modifier.align(Alignment.CenterStart).padding(end = 64.dp)
                                }
                            val sentAt =
                                rememberSaveable(msg) { timeFormatter.format(msg.localSentAt) }
                            MessageItem(
                                modifier = msgModifier,
                                message = msg.message,
                                nickname = msg.user.nickname,
                                sentAt = sentAt,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatCompositionPreview() {
    ChatComposition(
        modifier = Modifier.fillMaxSize(),
        chatTitle = "Chat",
        lazyListState = rememberLazyListState(),
        messages =
            flowOf(
                PagingData.from(
                    List(3) {
                        TheirMessageUI(
                            id = Uuid.random(),
                            message = "Msg $it",
                            sentAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
                            localSentAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
                            user = UserUI(id = Uuid.random(), nickname = "Test", username = "test"),
                        )
                    },
                ),
            ),
        reconnectionTimerSeconds = 3,
        message = TextFieldValue(),
        onMessageChange = {},
        sendMsg = {},
        showReconnectScreen = true,
        onFollowMessage = {},
        back = {},
    )
}

@Preview
@Composable
fun ChatCompositionPreviewWithTheme() {
    GMessengerTheme {
        ChatCompositionPreview()
    }
}
