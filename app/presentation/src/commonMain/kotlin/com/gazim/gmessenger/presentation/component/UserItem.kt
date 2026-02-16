package com.gazim.gmessenger.presentation.component

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// todo: Rename a preview and change a composition?
@Composable
fun UserItem(
    nickname: String = "Nickname",
    username: String = "Username",
    modifier: Modifier = Modifier,
) {
    ElevatedCard {
        Column(modifier.padding(16.dp).fillMaxWidth()) {
            Text(nickname)
            Spacer(Modifier.height(8.dp))
            Text("@$username")
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    UserItem()
}
