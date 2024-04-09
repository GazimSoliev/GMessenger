package com.gazim.gmessenger.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toComposeBitmapImage(): ImageBitmap = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()
