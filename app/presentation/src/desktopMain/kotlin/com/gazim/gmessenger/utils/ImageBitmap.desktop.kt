package com.gazim.gmessenger.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toComposeBitmapImage(): ImageBitmap = Image.makeFromEncoded(this).toComposeImageBitmap()
