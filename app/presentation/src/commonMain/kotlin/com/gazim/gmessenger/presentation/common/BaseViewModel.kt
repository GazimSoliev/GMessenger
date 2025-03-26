package com.gazim.gmessenger.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax

abstract class BaseViewModel<STATE : IState, SIDE_EFFECT : ISideEffect, ACTION : IAction> :
    ViewModel(),
    ContainerHost<STATE, SIDE_EFFECT> {
    val state: StateFlow<STATE> get() = container.stateFlow
    val sideEffect: Flow<SIDE_EFFECT> get() = container.sideEffectFlow

    abstract fun handleAction(action: ACTION)

    fun container(
        initialState: STATE,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: (suspend Syntax<STATE, SIDE_EFFECT>.() -> Unit)? = null
    ) = viewModelScope.container<STATE, SIDE_EFFECT>(
        initialState = initialState,
        buildSettings = buildSettings,
        onCreate = onCreate,
    )
}
