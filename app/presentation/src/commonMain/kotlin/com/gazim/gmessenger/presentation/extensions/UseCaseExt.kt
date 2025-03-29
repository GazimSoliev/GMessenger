package com.gazim.gmessenger.presentation.extensions

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalResourceApi::class)
suspend fun GetImageContentUseCase.getPainter(imageId: Uuid): Painter? {
    val result = invoke(imageId)
    val bytes = result.getOrNull() ?: return null
    val imageBitmap = bytes.decodeToImageBitmap()
    return BitmapPainter(imageBitmap)
}