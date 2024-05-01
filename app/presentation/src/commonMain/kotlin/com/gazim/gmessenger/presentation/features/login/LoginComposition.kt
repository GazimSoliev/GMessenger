package com.gazim.gmessenger.presentation.features.login

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gazim.gmessenger.presentation.component.ServerItem
import com.gazim.gmessenger.presentation.model.ServerInfoUI
import gmessenger.app.presentation.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

// todo: Rename a preview and change a composition
@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginComposition(
    modifier: Modifier = Modifier,
    login: TextFieldValue,
    password: TextFieldValue,
    passwordVisibility: Boolean,
    showPasswordVisibilityButton: Boolean,
    onLoginChange: (TextFieldValue) -> Unit,
    onPasswordChange: (TextFieldValue) -> Unit,
    onRegister: () -> Unit,
    onClickLogIn: () -> Unit,
    onClickPasswordVisibility: () -> Unit,
    cancel: () -> Unit,
    snackbarHostState: SnackbarHostState,
    loggingInProgress: Boolean,
    servers: List<ServerInfoUI> = emptyList(),
    dialogIsOpened: Boolean = false,
    cancelDialog: () -> Unit = {},
    showDialog: () -> Unit = {}
) {
    val strAppName = stringResource(Res.string.app_name)
    val strLogin = stringResource(Res.string.login)
    val strPassword = stringResource(Res.string.password)
    val strLogIn = stringResource(Res.string.log_in)
    val strCancel = stringResource(Res.string.cancel)
    val strCreateAccount = stringResource(Res.string.create_account)
    val strShowPassword = stringResource(Res.string.show_password)
    val strHidePassword = stringResource(Res.string.hide_password)
    val strSelectServer = stringResource(Res.string.select_server)
    Surface {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(strAppName, style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(64.dp))
                Column {
                    TextField(
                        value = login,
                        onValueChange = onLoginChange,
                        label = {
                            Text(strLogin)
                        },
                    )
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = {
                            Text(strPassword)
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            if (!showPasswordVisibilityButton) return@TextField
                            val contentDescription: String
                            val icon: ImageVector
                            if (passwordVisibility) {
                                contentDescription = strHidePassword
                                icon = Icons.Rounded.VisibilityOff
                            } else {
                                contentDescription = strShowPassword
                                icon = Icons.Rounded.Visibility
                            }
                            IconButton(onClick = onClickPasswordVisibility) {
                                Icon(imageVector = icon, contentDescription = contentDescription)
                            }
                        },
                    )
                }
                Spacer(Modifier.height(32.dp))
                Column(Modifier.width(IntrinsicSize.Max)) {
                    Button(
                        onClick = if (loggingInProgress) cancel else onClickLogIn,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (loggingInProgress) {
                            CircularProgressIndicator(
                                Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(strCancel)
                        } else {
                            Text(strLogIn)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = onRegister,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loggingInProgress,
                    ) {
                        Text(strCreateAccount)
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = showDialog,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loggingInProgress,
                    ) {
                        Text(strSelectServer)
                    }
                }
            }
        }
        if (dialogIsOpened) {
            Dialog(
                onDismissRequest = cancelDialog
            ) {
                Surface {
                    LazyColumn(Modifier.size(256.dp)) {
                        items(servers) {
                            ServerItem(server = it.server, url = it.url, ping = it.ping, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginCompositionPreview() {
    LoginComposition(
        modifier = Modifier.fillMaxSize(),
        login = TextFieldValue("Login"),
        password = TextFieldValue(),
        passwordVisibility = false,
        showPasswordVisibilityButton = false,
        {},
        {},
        {},
        {},
        {},
        snackbarHostState = SnackbarHostState(),
        loggingInProgress = false,
        cancel = {},
    )
}
