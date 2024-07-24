package com.gazim.gmessenger.presentation.navigation

import androidx.annotation.MainThread
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Route {
    val routeName: String
    val argument: String
}

val Route.route get() = if (argument.isBlank()) routeName else "$routeName/{$argument}"

fun Route.putArgument(value: String) = "$routeName/$value"

@MainThread
inline fun <reified T> NavController.navigate(
    route: Route,
    arg: T,
) = navigate(route.putArgument(Json.encodeToString(arg)))

@MainThread
fun NavController.navigate(route: Route) = navigate(route.routeName)

@MainThread
fun NavController.replace(route: Route) {
    val currentRoute = currentDestination!!.route!!
    navigate(route.routeName) {
        popUpTo(currentRoute) { inclusive = true }
    }
}

inline fun <reified T> NavBackStackEntry.getObject(key: String): T = getObjectOrNull(key)!!

inline fun <reified T> NavBackStackEntry.getObjectOrNull(key: String): T? = arguments?.getString(key)?.let(Json.Default::decodeFromString)

inline fun <reified T> NavBackStackEntry.returnObjectOrNull(key: String): T? =
    savedStateHandle.get<String>(key)?.let(Json.Default::decodeFromString)

inline fun <reified T> NavController.popBackStack(
    key: String,
    arg: T,
) {
    previousBackStackEntry?.savedStateHandle?.set(key, Json.encodeToString(arg))
    popBackStack()
}
