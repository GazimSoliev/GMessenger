package com.gazim.gmessenger.app.common

import androidx.compose.runtime.Composable

abstract class ABaseScreen<
    STATE : IState,
    SIDE_EFFECT : ISideEffect,
    ACTION : IAction,
    VIEW_MODEL : IBaseViewModel<STATE, SIDE_EFFECT, ACTION>,
    > : IBaseScreen<STATE, SIDE_EFFECT, ACTION, VIEW_MODEL> {
    protected abstract val state: STATE

    protected abstract suspend fun handleSideEffect(sideEffect: SIDE_EFFECT)

    protected abstract fun sendAction(action: ACTION)

    @Composable
    protected abstract fun Screen()

    protected open fun onStart() = Unit

    protected open fun onStop() = Unit
}
