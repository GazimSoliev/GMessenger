package com.gazim.gmessenger.presentation.common

import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.ContainerHost

abstract class ABaseViewModel<STATE : IState, SIDE_EFFECT : ISideEffect, ACTION : IAction> :
    IBaseViewModel<STATE, SIDE_EFFECT, ACTION>,
    ContainerHost<STATE, SIDE_EFFECT> {
    protected abstract val viewModelScope: CoroutineScope
}
