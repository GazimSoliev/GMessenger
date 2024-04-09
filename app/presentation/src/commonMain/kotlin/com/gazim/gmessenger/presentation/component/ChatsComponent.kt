package com.gazim.gmessenger.presentation.component

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
import com.gazim.gmessenger.presentation.model.ChatUI
import com.gazim.gmessenger.presentation.model.IChatUI
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

// todo: Rename a preview and change a composition
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun ChatsComponent(
    modifier: Modifier = Modifier,
    chats: List<IChatUI>,
    nextToChat: (IChatUI) -> Unit,
    createNewChat: () -> Unit,
    lookAtMyAccount: () -> Unit,
    logOut: () -> Unit,
) {
    val strChats = stringResource(Res.string.chats)
    val strNewChat = stringResource(Res.string.new_chat)
    val strLogOut = stringResource(Res.string.log_out)
    val strAccountInfo = stringResource(Res.string.account_info)
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(strChats) },
                    navigationIcon = {
                        IconButton(onClick = lookAtMyAccount) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = strAccountInfo,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = logOut) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = strLogOut)
                        }
                    },
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = { createNewChat() }, icon = {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = strNewChat)
                }, text = { Text(strNewChat) })
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
                        image = it.image
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
                image = null
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
