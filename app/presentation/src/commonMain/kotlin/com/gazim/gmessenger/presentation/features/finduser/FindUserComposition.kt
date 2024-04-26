package com.gazim.gmessenger.presentation.features.finduser

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
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
import com.gazim.gmessenger.presentation.component.UserItem
import com.gazim.gmessenger.presentation.model.IUserUI
import com.gazim.gmessenger.presentation.model.UserUI
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.back
import gmessenger.app.presentation.generated.resources.search
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

// todo: Rename a preview and change a composition?
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun FindUserComposition(
    modifier: Modifier = Modifier,
    users: List<IUserUI>,
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    createChat: (IUserUI) -> Unit,
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
                    ) {
                        it()
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
                items(users) {
                    UserItem(nickname = it.nickname, username = it.username, Modifier.clickable { createChat(it) })
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
                    id = "some",
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
