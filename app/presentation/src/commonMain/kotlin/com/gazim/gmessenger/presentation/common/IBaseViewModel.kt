package com.gazim.gmessenger.presentation.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel<STATE : IState, SIDE_EFFECT : ISideEffect, ACTION : IAction> {
    val state: StateFlow<STATE>
    val sideEffect: Flow<SIDE_EFFECT>

    fun handleAction(action: ACTION)

    fun destroyViewModel()
}
