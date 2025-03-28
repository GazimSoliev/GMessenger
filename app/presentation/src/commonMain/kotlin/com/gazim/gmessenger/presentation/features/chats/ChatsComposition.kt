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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.component.ChatItem
import com.gazim.gmessenger.presentation.component.ProfileIcon
import com.gazim.gmessenger.presentation.model.ChatUI
import com.gazim.gmessenger.presentation.model.ConversationUI
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.account_info
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
    accountImage: Painter? = null,
    firstNameLetter: Char = ' ',
    chats: List<ChatUI> = emptyList(),
    nextToChat: (ChatUI) -> Unit = {},
    createNewChat: () -> Unit = {},
    lookAtMyAccount: () -> Unit = {},
    logOut: () -> Unit = {},
) {
//    val strChats = stringResource(Res.string.chats)
    val strNewChat = stringResource(Res.string.new_chat)
    val strLogOut = stringResource(Res.string.log_out)
    val strAccountInfo = stringResource(Res.string.account_info)
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text("GMessenger") },
                    navigationIcon = {
                        ProfileIcon(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                            image = accountImage,
                            firstNameLetter = firstNameLetter,
                            size = 24.dp,
                            textStyle = MaterialTheme.typography.labelSmall,
                            onClick = lookAtMyAccount
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = strLogOut
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = strLogOut
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
                items(chats) {
                    ChatItem(
                        chatName = it.chatName,
                        chatLink = it.chatLink,
                        image = it.image,
                    ) { nextToChat(it) }
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
        chats = List(10) {
            ConversationUI(
                identifier = Uuid.random(),
                title = "Chat name",
                chatName = "Chat name",
                chatLink = "@identifier",
                image = null,
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
