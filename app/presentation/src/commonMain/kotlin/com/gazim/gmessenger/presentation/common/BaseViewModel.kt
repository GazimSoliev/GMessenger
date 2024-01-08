package com.gazim.gmessenger.presentation.common

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax

// todo: make some override implementation final?
abstract class BaseViewModel<STATE : IState, SIDE_EFFECT : ISideEffect, ACTION : IAction> :
    ContainerHost<STATE, SIDE_EFFECT>,
    ABaseViewModel<STATE, SIDE_EFFECT, ACTION>(),
    ScreenModel {
    override val viewModelScope = screenModelScope.plus(Dispatchers.Default)
    override val state: StateFlow<STATE> get() = container.stateFlow
    override val sideEffect: Flow<SIDE_EFFECT> get() = container.sideEffectFlow

    override fun destroyViewModel() = viewModelScope.cancel()

    protected fun container(
        initialState: STATE,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: (suspend SimpleSyntax<STATE, SIDE_EFFECT>.() -> Unit)? = null,
    ) = viewModelScope.container(initialState, buildSettings, onCreate)
}
