package com.gazim.gmessenger.presentation.component

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// todo: Rename a preview and remove println
@Composable
fun <T> NotificationStack(
    notifications: List<T>,
    modifier: Modifier = Modifier,
    spacedBy: Dp = 16.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    reverseLayout: Boolean = false,
    lazyListState: LazyListState = rememberLazyListState(),
    content: @Composable LazyItemScope.(T) -> Unit,
) {
    println(notifications)
    println("Test")
    LazyColumn(
        modifier = modifier,
        contentPadding = if (notifications.isEmpty()) PaddingValues(0.dp) else contentPadding,
        verticalArrangement = Arrangement.spacedBy(spacedBy),
        reverseLayout = reverseLayout,
        state = lazyListState,
    ) {
        items(notifications, key = { it.hashCode() }) {
            content(it)
        }
    }
}

@Preview
@Composable
fun NotificationStackPreview() {
    NotificationStack(
        List(10) { it },
    ) {
        Card(Modifier.fillMaxWidth()) {
            Text(text = it.toString(), Modifier.padding(16.dp))
        }
    }
}
