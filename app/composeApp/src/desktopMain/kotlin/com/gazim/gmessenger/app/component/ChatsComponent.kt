package com.gazim.gmessenger.app.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.app.model.ChatUI
import com.gazim.gmessenger.app.model.IChatUI
import com.gazim.gmessenger.app.theme.GMessengerTheme

// todo: Rename a preview and change a composition
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsComponent(
    modifier: Modifier = Modifier,
    chats: List<IChatUI>,
    nextToChat: (IChatUI) -> Unit,
    createNewChat: () -> Unit,
    lookAtMyAccount: () -> Unit,
    logOut: () -> Unit,
) {
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Chats") },
                    navigationIcon = {
                        IconButton(onClick = lookAtMyAccount) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = logOut) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                        }
                    },
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = { createNewChat() }, icon = {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null)
                }, text = { Text("New chat") })
            },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(chats) {
                    ChatItem(
                        chatName = it.chatName,
                        chatLink = it.chatLink,
                    ) { nextToChat(it) }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatsComponentPreview() {
    ChatsComponent(
        modifier = Modifier.fillMaxSize(),
        List(10) {
            ChatUI(
                identifier = "identifier",
                title = "Chat name",
                chatName = "Chat name",
                chatLink = "@identifier",
            )
        },
        {},
        {},
        {},
        {},
    )
}

@Preview
@Composable
fun ChatsComponentPreviewWithTheme() {
    GMessengerTheme {
        ChatsComponentPreview()
    }
}
