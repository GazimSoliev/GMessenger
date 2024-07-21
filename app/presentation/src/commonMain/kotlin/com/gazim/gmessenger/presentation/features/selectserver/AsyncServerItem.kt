package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.presentation.component.ServerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AsyncServerItem(
    modifier: Modifier = Modifier,
    server: String = "",
    url: String = "",
    isSecure: Boolean = false,
    pinging: suspend (host: String, isSecure: Boolean) -> String = { _, _ -> "" }
) {
    var ping by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            while (true) {
                runCatching {
                    pinging(url, isSecure)
                }.onSuccess {
                    ping = it
                }.onFailure {
                    ping = "–"
                    it.printStackTrace()
                }
                delay(1_000)
            }
        }
    }
    ServerItem(
        modifier = modifier,
        server = server,
        url = url,
        ping = ping,
    )
}