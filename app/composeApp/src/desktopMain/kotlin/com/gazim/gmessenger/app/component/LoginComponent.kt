package com.gazim.gmessenger.app.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

// todo: Rename a preview and change a composition
@Composable
fun LoginComponent(
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
) {
    Surface {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("GMessenger", style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(64.dp))
                Column {
                    TextField(
                        value = login,
                        onValueChange = onLoginChange,
                        label = {
                            Text("Login")
                        },
                    )
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = {
                            Text("Password")
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            if (!showPasswordVisibilityButton) return@TextField
                            val icon = if (passwordVisibility) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            }
                            IconButton(onClick = onClickPasswordVisibility) {
                                Icon(imageVector = icon, contentDescription = null)
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
                            CircularProgressIndicator(Modifier.size(16.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                            Spacer(Modifier.width(8.dp))
                            Text("Cancel")
                        } else {
                            Text("Log in")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = onRegister,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loggingInProgress,
                    ) {
                        Text("Create account")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginComponentPreview() {
    LoginComponent(
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
