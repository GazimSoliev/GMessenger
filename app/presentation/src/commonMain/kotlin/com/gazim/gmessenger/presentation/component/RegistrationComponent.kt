package com.gazim.gmessenger.presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

const val helpDialogText =
    """Nickname allowed: 0-64 character length, any character.

Username allowed: 4-32 character length, numbers and latin symbols.

Login allowed: 8-32 character length, numbers and latin symbols.

Password allowed: 8-128 character length, numbers, latin symbols and special characters. Password also required though: 1 upper case, 1 lowe case, 1 number, 1 special character and entropy."""

// todo: Rename a preview and change a composition
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationComponent(
    modifier: Modifier = Modifier,
    nickname: TextFieldValue,
    username: TextFieldValue,
    login: TextFieldValue,
    password: TextFieldValue,
    isWrongNickname: Boolean,
    isWrongUsername: Boolean,
    isWrongLogin: Boolean,
    isWrongPassword: Boolean,
    passwordVisibility: Boolean,
    showPasswordVisibilityButton: Boolean,
    onNicknameChange: (TextFieldValue) -> Unit,
    onUsernameChange: (TextFieldValue) -> Unit,
    onLoginChange: (TextFieldValue) -> Unit,
    onPasswordChange: (TextFieldValue) -> Unit,
    onClickRegistration: () -> Unit,
    onClickPasswordVisibility: () -> Unit,
    back: () -> Unit,
    registrationInProgress: Boolean,
    cancel: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val incorrectInput = "Incorrect input. Look at \"Help?\""
    var showAlertDialog by remember { mutableStateOf(false) }
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Requirements") },
            text = {
                Text(helpDialogText)
            },
            confirmButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text("OK")
                }
            },
        )
    }
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(title = { Text("Registration") }, navigationIcon = {
                    IconButton(onClick = back, enabled = !registrationInProgress) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = nickname,
                    onValueChange = onNicknameChange,
                    isError = isWrongNickname,
                    label = { Text("Nickname") },
                    supportingText = { if (isWrongNickname) Text(incorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    isError = isWrongUsername,
                    label = { Text("Username") },
                    supportingText = { if (isWrongUsername) Text(incorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = login,
                    onValueChange = onLoginChange,
                    isError = isWrongLogin,
                    label = { Text("Login") },
                    supportingText = { if (isWrongLogin) Text(incorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    isError = isWrongPassword,
                    supportingText = { if (isWrongPassword) Text(incorrectInput) },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        if (!showPasswordVisibilityButton) return@TextField
                        val icon =
                            if (passwordVisibility) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            }
                        IconButton(onClick = onClickPasswordVisibility) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    },
                )
                Spacer(Modifier.height(32.dp))
                Column(Modifier.width(IntrinsicSize.Max)) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = if (registrationInProgress) cancel else onClickRegistration,
                    ) {
                        if (registrationInProgress) {
                            CircularProgressIndicator(
                                Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Cancel")
                        } else {
                            Text("Register")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        showAlertDialog = true
                    }) {
                        Text("Help?")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationComponentPreview() {
    RegistrationComponent(
        modifier = Modifier.fillMaxSize(),
        nickname = TextFieldValue(),
        username = TextFieldValue("username"),
        login = TextFieldValue("Login"),
        password = TextFieldValue("Password"),
        isWrongNickname = true,
        isWrongUsername = true,
        isWrongLogin = false,
        isWrongPassword = false,
        passwordVisibility = false,
        showPasswordVisibilityButton = true,
        onNicknameChange = {},
        onUsernameChange = {},
        onLoginChange = {},
        onPasswordChange = {},
        onClickRegistration = {},
        onClickPasswordVisibility = {},
        back = {},
        registrationInProgress = true,
        cancel = {},
        snackbarHostState = SnackbarHostState(),
    )
}
