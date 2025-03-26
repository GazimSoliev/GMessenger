@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.features.finduser

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.component.ChatItem
import com.gazim.gmessenger.presentation.model.UserUI
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.back
import gmessenger.app.presentation.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo: Rename a preview and change a composition?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindUserComposition(
    modifier: Modifier = Modifier,
    users: List<UserUI>,
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    createChat: (Uuid) -> Unit,
    back: () -> Unit,
) {
    val strBack = stringResource(Res.string.back)
    val strSearch = stringResource(Res.string.search)
    Surface(modifier = modifier) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = typography.bodyLarge.copy(color = colorScheme.onBackground),
                        cursorBrush = SolidColor(colorScheme.onBackground),
                    ) { textField ->
                        textField()
                        if (query.text.isEmpty()) {
                            Text(
                                text = strSearch,
                                style = typography.bodyLarge,
                                color = colorScheme.onBackground.copy(alpha = 0.5f),
                            )
                        }
                    }
                }, navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = strBack)
                    }
                })
            },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(users) { user ->
                    ChatItem(
                        chatName = user.nickname,
                        chatLink = user.username,
                        image = user.photo?.id,
                    ) {
                        createChat(user.id)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FindUserCompositionPreview() {
    FindUserComposition(
        modifier = Modifier.fillMaxSize(),
        users =
            List(10) {
                UserUI(
                    nickname = "Name $it",
                    username = "Username $it",
                )
            },
        query = TextFieldValue(),
        onQueryChange = {},
        createChat = {},
        back = {},
    )
}
