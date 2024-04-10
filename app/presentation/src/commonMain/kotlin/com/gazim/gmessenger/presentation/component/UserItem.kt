package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.domain.model.IUserModel
import com.gazim.gmessenger.domain.model.UserModel

// todo: Rename a preview and change a composition?
@Composable
fun UserItem(
    user: IUserModel,
    modifier: Modifier = Modifier,
) {
    ElevatedCard {
        Column(modifier.padding(16.dp).fillMaxWidth()) {
            Text(user.nickname)
            Spacer(Modifier.height(8.dp))
            Text("@${user.username}")
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    UserItem(
        UserModel(
            id = "some id",
            nickname = "Nickname",
            username = "Username",
            null,
        ),
    )
}
