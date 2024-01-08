package com.gazim.gmessenger.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch

// todo: make some implementation final?
abstract class BaseScreen<
    STATE : IState,
    SIDE_EFFECT : ISideEffect,
    ACTION : IAction,
    VIEW_MODEL : IBaseViewModel<STATE, SIDE_EFFECT, ACTION>,
> : Screen, ABaseScreen<STATE, SIDE_EFFECT, ACTION, VIEW_MODEL>() {
    private lateinit var viewModel: VIEW_MODEL
    protected lateinit var navigator: Navigator
        private set
    private lateinit var _state: State<STATE>
    override val state get() = _state.value
    override val key: ScreenKey = uniqueScreenKey

    override fun sendAction(action: ACTION) = viewModel.handleAction(action)

    @Composable
    override fun Content() {
        viewModel = createViewModel()
        navigator = LocalNavigator.currentOrThrow
        _state = viewModel.state.collectAsState()
        LaunchedEffect(viewModel) {
            viewModel.sideEffect.collect { launch { handleSideEffect(it) } }
        }
        LifecycleEffect(
            onStarted = ::onStart,
            onDisposed = ::onStop,
        )
        Screen()
    }

    @Composable
    protected abstract fun createViewModel(): VIEW_MODEL
}
