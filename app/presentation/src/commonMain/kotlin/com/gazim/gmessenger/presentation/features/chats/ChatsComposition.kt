@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.features.chats

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.component.ChatItem
import com.gazim.gmessenger.presentation.component.ProfileIcon
import com.gazim.gmessenger.presentation.model.ChatUI
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.log_out
import gmessenger.app.presentation.generated.resources.new_chat
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: Rename a preview and change a composition
@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ChatsComposition(
    modifier: Modifier = Modifier,
    profileImage: Painter? = null,
    firstNameLetter: Char = ' ',
    chats: List<ChatUI> = emptyList(),
    openChat: (ChatUI) -> Unit = {},
    createNewChat: () -> Unit = {},
    lookAtMyAccount: () -> Unit = {},
    logOut: () -> Unit = {},
    getChatImage: suspend (Uuid) -> Painter? = { null },
) {
    val strNewChat = stringResource(Res.string.new_chat)
    val strLogOut = stringResource(Res.string.log_out)
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text("GMessenger") },
                    navigationIcon = {
                        ProfileIcon(
                            modifier =
                                Modifier
                                    .padding(horizontal = 24.dp),
                            image = profileImage,
                            firstNameLetter = firstNameLetter,
                            size = 24.dp,
                            textStyle = MaterialTheme.typography.labelSmall,
                            onClick = lookAtMyAccount,
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = strLogOut,
                            )
                        }
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text(strLogOut) },
                                onClick = {
                                    expanded = false
                                    logOut()
                                },
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { createNewChat() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = strNewChat)
                }
            },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(chats) { chat ->
                    ChatItem(
                        title = chat.title,
                        link = chat.link,
                        image = chat.image,
                        lastMessage = chat.lastMessage,
                        firstNameLetter = chat.firstLetter,
                        lastMessageDateTime = chat.lastMessageDateTime,
                        onClickChat = { openChat(chat) },
                        getImage = getChatImage,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatsCompositionPreview() {
    ChatsComposition(
        modifier = Modifier.fillMaxSize(),
        chats =
            List(10) {
                ChatUI(
                    identifier = Uuid.random(),
                    title = "Chat name",
                    link = "@identifier",
                    image = null,
                    lastMessage = "Hello!",
                    firstLetter = 'C',
                    lastMessageDateTime = "27 Mar",
                )
            },
        firstNameLetter = 'G',
    )
}

@Preview
@Composable
fun ChatsComponentPreviewWithTheme() {
    GMessengerTheme {
        ChatsCompositionPreview()
    }
}
