package com.gazim.gmessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gazim.gmessenger.domain.usecase.PassAuthUseCase
import com.gazim.gmessenger.presentation.features.chat.ChatScreen
import com.gazim.gmessenger.presentation.features.chats.ChatsScreen
import com.gazim.gmessenger.presentation.features.finduser.FindUserScreen
import com.gazim.gmessenger.presentation.features.login.LoginScreen
import com.gazim.gmessenger.presentation.features.register.RegisterScreen
import com.gazim.gmessenger.presentation.features.selectserver.SelectServerScreen
import com.gazim.gmessenger.presentation.features.user.UserScreen
import com.gazim.gmessenger.presentation.model.ChatUI
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatRoute(
    val jsonChat: String,
)

fun ChatRoute(chat: ChatUI) = ChatRoute(Json.encodeToString(chat))

val ChatRoute.chat get() = Json.decodeFromString<ChatUI>(jsonChat)

@Serializable
class ChatsRoute

@Serializable
class FindUserRoute

@Serializable
class LoginRoute

@Serializable
class RegistrationRoute

@Serializable
class SelectServerRoute

@Serializable
class UserRoute

@Composable
fun Navigation(passAuthUseCase: PassAuthUseCase) {
    val startDestination = if (passAuthUseCase()) ChatsRoute() else LoginRoute()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<ChatRoute> { navBack ->
            val route = navBack.toRoute<ChatRoute>()
            println(route)
            ChatScreen(navController, route.chat)
        }
        composable<ChatsRoute> {
            ChatsScreen(navController)
        }
        composable<FindUserRoute> {
            FindUserScreen(navController)
        }
        composable<LoginRoute> {
            LoginScreen(navController)
        }
        composable<RegistrationRoute> {
            RegisterScreen(navController)
        }
        composable<SelectServerRoute> {
            SelectServerScreen(navController)
        }
        composable<UserRoute> {
            UserScreen(navController)
        }
    }
}

fun <T : Any> NavController.replace(route: T) {
    val currentRoute = currentDestination!!.route!!
    navigate(route) {
        popUpTo(currentRoute) { inclusive = true }
    }
}
