package com.gazim.gmessenger.utils

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


actual fun pickPhoto(): Pair<String, ByteArray>? {
    val chooser = JFileChooser()
    val filter = FileNameExtensionFilter(
        "Images", "jpg", "jpeg", "png"
    )
    chooser.fileFilter = filter
    val returnVal = chooser.showOpenDialog(null)
    if (returnVal == JFileChooser.APPROVE_OPTION) return chooser.selectedFile?.let {
        it.name.substringAfterLast('.', "") to it.readBytes()
    }
    return null
}