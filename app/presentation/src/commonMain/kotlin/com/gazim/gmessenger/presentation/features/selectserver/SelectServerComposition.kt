package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.model.ServerInfoUI
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectServerComposition(
    modifier: Modifier = Modifier,
    servers: List<ServerInfoUI> = emptyList(),
    editableMode: Boolean = false,
    serverTextField: TextFieldValue = TextFieldValue(),
    urlTextField: TextFieldValue = TextFieldValue(),
    onServerClick: (ServerInfoUI) -> Unit = {},
    onSelectClick: () -> Unit = {},
    onAddServerClick: () -> Unit = {},
    onServerChange: (TextFieldValue) -> Unit = {},
    onUrlChange: (TextFieldValue) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    pinging: suspend (String) -> String = { "" }
) {
    val width = 512.dp
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Select server")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (editableMode) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(value = serverTextField, onValueChange = onServerChange, label = { Text("Server") })
                    Spacer(Modifier.height(16.dp))
                    TextField(value = urlTextField, onValueChange = onUrlChange, label = { Text("URL") })
                    Spacer(Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Button(onClick = onSaveClick) {
                            Text("Save")
                        }
                        Spacer(Modifier.width(16.dp))
                        Button(onClick = onCancelClick) {
                            Text("Cancel")
                        }
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(servers) {
                            Row(
                                modifier = Modifier.widthIn(width, width).clickable { onServerClick(it) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = it.selected, onClick = { onServerClick(it) })
                                AsyncServerItem(
                                    server = it.server,
                                    url = it.url,
                                    pinging = pinging
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.widthIn(width, width).padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = onAddServerClick) {
                            Text("Add server")
                        }
                        Button(onClick = onSelectClick) {
                            Text("Select")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectServerCompositionPreview() {
    GMessengerTheme {
        SelectServerComposition(
            servers = listOf(
                ServerInfoUI("Server", "http://localhost:8080", "0", true),
                ServerInfoUI("Server", "url", "0", false),
                ServerInfoUI("Server", "url", "0", false),
            )
        )
    }
}

@Preview
@Composable
fun SelectServerCompositionEditModePreview() {
    GMessengerTheme {
        SelectServerComposition(
            editableMode = true,
            servers = listOf(
                ServerInfoUI("Server", "http://localhost:8080", "0", true),
                ServerInfoUI("Server", "url", "0", false),
                ServerInfoUI("Server", "url", "0", false),
            )
        )
    }
}