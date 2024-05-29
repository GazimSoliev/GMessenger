package com.gazim.gmessenger.utils

import androidx.compose.runtime.Composable

fun interface PhotoPicker {
    fun pick()
}

@Composable
expect fun rememberPhotoPicker(block: (Pair<String, ByteArray>) -> Unit): PhotoPicker
