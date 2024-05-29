package com.gazim.gmessenger.utils

import androidx.compose.runtime.Composable
import java.awt.FileDialog
import java.awt.Frame

@Composable
actual fun rememberPhotoPicker(block: (Pair<String, ByteArray>) -> Unit): PhotoPicker =
    PhotoPicker {
        runCatching {
            val dialog =
                FileDialog(null as Frame?, "Select Photo").apply {
                    mode = FileDialog.LOAD
                    isVisible = true
                    setFile("*.jpg;*.png;*.jpeg")
                }
            val file = dialog.files
            dialog.dispose()
            file.singleOrNull()?.let {
                it.name.substringAfterLast('.', "") to it.readBytes()
            } ?: return@PhotoPicker
        }.onSuccess(block).onFailure(Throwable::printStackTrace)
    }
