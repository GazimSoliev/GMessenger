package com.gazim.gmessenger.presentation.common

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax

// todo: make some override implementation final?
abstract class BaseViewModel<STATE : IState, SIDE_EFFECT : ISideEffect, ACTION : IAction> :
    ContainerHost<STATE, SIDE_EFFECT>,
    ABaseViewModel<STATE, SIDE_EFFECT, ACTION>() {
    override val scope =
        CoroutineScope(
            Dispatchers.IO +
                SupervisorJob() +
                CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() },
        )
    override val state: StateFlow<STATE> get() = container.stateFlow
    override val sideEffect: Flow<SIDE_EFFECT> get() = container.sideEffectFlow

    override fun destroyViewModel() = scope.cancel()

    protected fun container(
        initialState: STATE,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: (suspend SimpleSyntax<STATE, SIDE_EFFECT>.() -> Unit)? = null,
    ) = scope.container(initialState, buildSettings, onCreate)
}
