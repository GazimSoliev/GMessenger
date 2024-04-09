package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

// todo: Change this screen and rename preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun AccountComponent(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap? = null,
    nickname: String = "",
    username: String = "",
    usernameValue: TextFieldValue = TextFieldValue(),
    nicknameValue: TextFieldValue = TextFieldValue(),
    editMode: Boolean = false,
    onNicknameChange: (TextFieldValue) -> Unit = {},
    onUsernameChange: (TextFieldValue) -> Unit = {},
    onEditClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    uploadNewPhoto: () -> Unit = {},
    back: () -> Unit = {},
) {
    val strAccountInfo = stringResource(Res.string.account_info)
    val strBack = stringResource(Res.string.back)
    val strNickname = stringResource(Res.string.nickname)
    val strUsername = stringResource(Res.string.username)
    val strEditAccount = stringResource(Res.string.edit_account)
    val strCancel = stringResource(Res.string.cancel)
    val strDone = stringResource(Res.string.done)
    val strEdit = stringResource(Res.string.edit)
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(if (editMode) strEditAccount else strAccountInfo) },
                    navigationIcon = {
                        IconButton(onClick = back) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = strBack)
                        }
                    },
                    actions = {
                        if (editMode) {
                            IconButton(onClick = onCancelClick) {
                                Icon(imageVector = Icons.Rounded.Close, contentDescription = strCancel)
                            }
                            IconButton(onClick = onSaveClick) {
                                Icon(imageVector = Icons.Rounded.Done, contentDescription = strDone)
                            }
                        } else {
                            IconButton(onClick = onEditClick) {
                                Icon(imageVector = Icons.Rounded.Edit, contentDescription = strEdit)
                            }
                        }
                    },
                )
            },
        ) {
            Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (editMode) {
                        TextField(
                            value = nicknameValue,
                            onValueChange = onNicknameChange,
                            label = { Text(strNickname) },
                        )
                        Spacer(Modifier.height(16.dp))
                        TextField(
                            value = usernameValue,
                            onValueChange = onUsernameChange,
                            label = { Text(strUsername) },
                        )
                    } else {
                        Box {
                            Box(
                                modifier = Modifier
                                    .size(256.dp)
                                    .border(4.dp, colorScheme.outline, CircleShape)
                                    .clip(CircleShape)
                            ) {
                                if (imageBitmap != null) {
                                    Image(
                                        bitmap = imageBitmap,
                                        modifier = Modifier.fillMaxSize().blur(1.dp),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null
                                    )
                                    Image(
                                        bitmap = imageBitmap,
                                        modifier = Modifier.fillMaxSize(),
                                        contentDescription = null
                                    )
                                } else
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                            }
                            FilledTonalIconButton(
                                onClick = uploadNewPhoto,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(Modifier.height(64.dp))
                        Text(nickname, style = typography.displayLarge)
                        Spacer(Modifier.height(32.dp))
                        Text(username, style = typography.displayLarge)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AccountComponentPreview() {
    GMessengerTheme(false) {
        AccountComponent(nickname = "Steve", username = "@steve123")
    }
}

@Preview
@Composable
fun EditModeAccountComponentPreview() {
    GMessengerTheme(false) {
        AccountComponent(
            usernameValue = TextFieldValue("Steve"),
            nicknameValue = TextFieldValue("@steve123"),
            editMode = true,
        )
    }
}
