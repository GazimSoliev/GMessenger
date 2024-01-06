package com.gazim.gmessenger.app.common

import androidx.compose.runtime.Composable

interface IBaseScreen<
    STATE : IState,
    SIDE_EFFECT : ISideEffect,
    ACTION : IAction,
    VIEW_MODEL : IBaseViewModel<STATE, SIDE_EFFECT, ACTION>,
    > {
    @Composable
    fun Content()
}
