package com.gazim.gmessenger.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition

// todo: Review
@Composable
fun RenderOnTopWindows(
    alignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit,
) {
    var windowHeight by rememberSaveable { mutableStateOf(0.dp) }
    var windowWidth by rememberSaveable { mutableStateOf(0.dp) }
    val childConstraints =
        Constraints(
            minWidth = 0,
            minHeight = 0,
            maxWidth = Constraints.Infinity,
            maxHeight = Constraints.Infinity,
        )
    DialogWindow(
        {},
        state =
            rememberSaveable(windowHeight, windowWidth) {
                DialogState(
                    position = WindowPosition(alignment),
                    size = DpSize(windowWidth, windowHeight),
                )
            },
        transparent = true,
        undecorated = true,
        resizable = false,
        focusable = false,
    ) {
        this.window.isAlwaysOnTop = true
        Layout(content) { measurableList, _ ->
            val placeableList = measurableList.map { it.measure(childConstraints) }
            val layoutWidth = placeableList.maxByOrNull { it.width }?.width ?: 0
            val layoutHeight = placeableList.maxByOrNull { it.height }?.height ?: 0
            println(layoutWidth)
            println(layoutHeight)
            windowHeight = if (layoutWidth == 0) 0.dp else layoutHeight.toDp()
            windowWidth = if (layoutHeight == 0) 0.dp else layoutWidth.toDp()
            layout(layoutWidth, layoutHeight) {
                placeableList.forEach {
                    it.placeRelative(layoutWidth - it.width, layoutHeight - it.height)
                }
            }
        }
    }
}
