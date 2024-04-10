package com.gazim.gmessenger.utils

import java.awt.FileDialog
import java.awt.Frame

actual fun pickPhoto(): Pair<String, ByteArray>? {
    val dialog =
        FileDialog(null as Frame?, "Select Photo").apply {
            mode = FileDialog.LOAD
            isVisible = true
            setFile("*.jpg;*.png;*.jpeg")
        }
    val file = dialog.files
    dialog.dispose()
    return file.singleOrNull()?.let {
        it.name.substringAfterLast('.', "") to it.readBytes()
    }
}
