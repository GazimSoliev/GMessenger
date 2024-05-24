package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

@Composable
fun ServerItem(
    modifier: Modifier = Modifier,
    server: String = "",
    url: String = "",
    ping: String = ""
) {
    Row(modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(server)
            Text(url)
        }
        Spacer(Modifier.weight(1f))
        Text(ping)
    }
}

@Preview
@Composable
fun ServerItemPreview() {
    GMessengerTheme {
        Surface {
            ServerItem(modifier = Modifier.fillMaxWidth(), server = "Server", url = "url", ping = "0")
        }
    }
}