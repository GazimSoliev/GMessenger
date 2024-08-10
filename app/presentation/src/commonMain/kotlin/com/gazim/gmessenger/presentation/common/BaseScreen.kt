package com.gazim.gmessenger.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope

typealias HandleSideEffect<SIDE_EFFECT> = suspend CoroutineScope.(sideEffect: SIDE_EFFECT) -> Unit

@Composable
fun <STATE : IState> BaseViewModel<STATE, *, *>.collectAsState() = container.stateFlow.collectAsState()

@Composable
fun <SIDE_EFFECT : ISideEffect> BaseViewModel<*, SIDE_EFFECT, *>.handleSideEffect(handleSideEffect: HandleSideEffect<SIDE_EFFECT>) {
    LaunchedEffect(Unit) {
        container.sideEffectFlow.collect {
            handleSideEffect(it)
        }
    }
}

fun <ACTION : IAction> BaseViewModel<*, *, ACTION>.sendAction(action: ACTION) = handleAction(action)
