package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.model.ServerInfoUI
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

@Composable
fun ServerList(servers: List<ServerInfoUI> = emptyList()) {
    Surface(
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            LazyColumn(Modifier.size(512.dp).padding(16.dp)) {
                items(servers) {
                    ServerItem(server = it.server, url = it.host, ping = it.ping, modifier = Modifier.fillMaxWidth())
                }
            }
            Row {
                Button({}) {
                    Text("Add server")
                }
                Button({}) {
                }
            }
        }
    }
}

@Preview
@Composable
fun ServerListPreview() {
    GMessengerTheme {
        ServerList(
            servers =
                listOf(
                    ServerInfoUI("Server", "localhost:8080", false, "0", true),
                    ServerInfoUI("Server", "url", false, "0", false),
                    ServerInfoUI("Server", "url", false, "0", false),
                ),
        )
    }
}
