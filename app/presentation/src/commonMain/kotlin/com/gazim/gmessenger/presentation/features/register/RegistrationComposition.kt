package com.gazim.gmessenger.presentation.features.register

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme
import gmessenger.app.presentation.generated.resources.*
import org.jetbrains.compose.resources.stringResource

// todo: Rename a preview and change a composition
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationComposition(
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
    val strRequirementsDetails = stringResource(Res.string.requirements_details)
    val strIncorrectInput = stringResource(Res.string.incorrect_input)
    val strRequirements = stringResource(Res.string.requirements)
    val strOk = stringResource(Res.string.ok)
    val strRegistration = stringResource(Res.string.registration)
    val strNickname = stringResource(Res.string.nickname)
    val strUsername = stringResource(Res.string.username)
    val strLogin = stringResource(Res.string.login)
    val strPassword = stringResource(Res.string.password)
    val strShowPassword = stringResource(Res.string.show_password)
    val strHidePassword = stringResource(Res.string.hide_password)
    val strRegister = stringResource(Res.string.register)
    val strCancel = stringResource(Res.string.cancel)
    val strHelp = stringResource(Res.string.help)
    var showAlertDialog by remember { mutableStateOf(false) }
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text(strRequirements) },
            text = {
                Text(strRequirementsDetails)
            },
            confirmButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text(strOk)
                }
            },
        )
    }
    Surface {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(title = { Text(strRegistration) }, navigationIcon = {
                    IconButton(onClick = back, enabled = !registrationInProgress) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
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
                    label = { Text(strNickname) },
                    supportingText = { if (isWrongNickname) Text(strIncorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    isError = isWrongUsername,
                    label = { Text(strUsername) },
                    supportingText = { if (isWrongUsername) Text(strIncorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = login,
                    onValueChange = onLoginChange,
                    isError = isWrongLogin,
                    label = { Text(strLogin) },
                    supportingText = { if (isWrongLogin) Text(strIncorrectInput) },
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text(strPassword) },
                    isError = isWrongPassword,
                    supportingText = { if (isWrongPassword) Text(strIncorrectInput) },
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
                            Text(strCancel)
                        } else {
                            Text(strRegister)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        showAlertDialog = true
                    }) {
                        Text(strHelp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationCompositionPreview() {
    GMessengerTheme {
        RegistrationComposition(
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
}
