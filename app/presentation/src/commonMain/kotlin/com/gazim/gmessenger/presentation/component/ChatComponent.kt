package com.gazim.gmessenger.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.gazim.gmessenger.presentation.model.*
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// todo: Rename preview and maybe change a composition
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatComponent(
    modifier: Modifier = Modifier,
    chatTitle: String,
    messages: Flow<PagingData<IMessageItemUI>>,
    message: TextFieldValue,
    showReconnectScreen: Boolean,
    reconnectionTimerSeconds: Int,
    onMessageChange: (TextFieldValue) -> Unit,
    sendMsg: () -> Unit,
    back: () -> Unit,
) {
    val pagingMessages = messages.collectAsLazyPagingItems()
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd yyyy") }
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                chatTitle,
                                modifier = Modifier.background(colorScheme.surface).padding(8.dp),
                            )
                            AnimatedVisibility(visible = showReconnectScreen) {
                                Text("Reconnect in $reconnectionTimerSeconds", style = typography.labelSmall)
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = back) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
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
                    Modifier.fillMaxWidth().background(colorScheme.surface.copy(alpha = 0.95f)).padding(8.dp)
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
                            Modifier.padding(
                                start = 24.dp,
                                end = 8.dp,
                                top = 8.dp,
                                bottom = 8.dp,
                            ).weight(1f).heightIn(max = 128.dp),
                        ) {
                            it()
                            if (message.text.isEmpty()) {
                                Text(
                                    "Message",
                                    style = typography.bodyLarge,
                                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                )
                            }
                        }
                        IconButton(onClick = sendMsg) {
                            Icon(
                                imageVector = Icons.Rounded.Send,
                                contentDescription = null,
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
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                reverseLayout = true,
                contentPadding = contentPadding,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    count = pagingMessages.itemCount,
//                    key = {
//                        when(val msg = pagingMessages[it]) {
//                            is IFullMessageUI -> msg.id
//                            else -> msg.hashCode()
//                        }
//                    }
                ) { index ->
                    val msg = pagingMessages[index]
                    if (msg is IGroupedMessagesDateUI) {
                        val groupedDate =
                            rememberSaveable(msg) { dateFormatter.format(msg.date.toJavaLocalDate()) }
                        Text(
                            groupedDate,
                            modifier = Modifier.padding(16.dp),
                        )
                    } else if (msg is IFullMessageUI) {
                        Box(Modifier.fillMaxWidth()) {
                            val msgModifier =
                                if (msg is IYourMessageUI) {
                                    Modifier.align(Alignment.CenterEnd).padding(start = 64.dp)
                                } else {
                                    Modifier.align(Alignment.CenterStart).padding(end = 64.dp)
                                }
                            val sentAt =
                                rememberSaveable(msg) { timeFormatter.format(msg.sentAt.toJavaLocalDateTime()) }
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
fun ChatComponentPreview() {
    ChatComponent(
        modifier = Modifier.fillMaxSize(),
        chatTitle = "Chat",
        messages = flowOf(
            PagingData.from(
                List(3) {
                    TheirMessageUI(
                        id = UUID.randomUUID().toString(),
                        message = "Msg $it",
                        sentAt = LocalDateTime.now().toKotlinLocalDateTime(),
                        user = UserUI(id = "some id", nickname = "Test", username = "test"),
                    )
                }
            )
        ),
        reconnectionTimerSeconds = 3,
        message = TextFieldValue(),
        onMessageChange = {},
        sendMsg = {},
        showReconnectScreen = true,
        back = {},
    )
}

@Preview
@Composable
fun ChatComponentPreviewWithTheme() {
    GMessengerTheme {
        ChatComponentPreview()
    }
}
