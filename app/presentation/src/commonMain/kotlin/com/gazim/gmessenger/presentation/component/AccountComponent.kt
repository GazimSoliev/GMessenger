package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.account_info
import gmessenger.app.presentation.generated.resources.back
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

// todo: Change this screen and rename preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun AccountComponent(
    modifier: Modifier = Modifier,
    nickname: String,
    username: String,
    back: () -> Unit,
) {
    val strAccountInfo = stringResource(Res.string.account_info)
    val strBack = stringResource(Res.string.back)
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(title = { Text(strAccountInfo) }, navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = strBack)
                    }
                })
            },
        ) {
            Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(nickname, style = typography.displayLarge)
                    Spacer(Modifier.height(32.dp))
                    Text(username, style = typography.displayLarge)
                }
            }
        }
    }
}

@Preview
@Composable
fun AccountComponentPreview() {
    AccountComponent(nickname = "Steve", username = "@steve123", back = {})
}
