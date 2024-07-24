package com.gazim.gmessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gazim.gmessenger.domain.usecase.PassAuthUseCase
import com.gazim.gmessenger.presentation.features.chat.ChatScreen
import com.gazim.gmessenger.presentation.features.chats.ChatsScreen
import com.gazim.gmessenger.presentation.features.finduser.FindUserScreen
import com.gazim.gmessenger.presentation.features.login.LoginScreen
import com.gazim.gmessenger.presentation.features.register.RegisterScreen
import com.gazim.gmessenger.presentation.features.selectserver.SelectServerScreen
import com.gazim.gmessenger.presentation.features.user.UserScreen

enum class Screen(
    override val argument: String = "",
) : Route {
    Chat("chatId"),
    Chats,
    FindUser,
    Login,
    Registration,
    SelectServer,
    User,
    ;

    override val routeName: String get() = name
}

@Composable
fun Navigation(passAuthUseCase: PassAuthUseCase) {
    val startDestination = (if (passAuthUseCase()) Screen.Chats else Screen.Login).route
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Chat.route) {
            ChatScreen(navController, it.getObject(Screen.Chat.argument))
        }
        composable(Screen.Chats.route) {
            ChatsScreen(navController)
        }
        composable(Screen.FindUser.route) {
            FindUserScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Registration.route) {
            RegisterScreen(navController)
        }
        composable(Screen.SelectServer.route) {
            SelectServerScreen(navController)
        }
        composable(Screen.User.route) {
            UserScreen(navController)
        }
    }
}
