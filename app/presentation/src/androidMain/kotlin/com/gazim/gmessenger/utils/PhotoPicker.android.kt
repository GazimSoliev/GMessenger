package com.gazim.gmessenger.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPhotoPicker(block: (Pair<String, ByteArray>) -> Unit): PhotoPicker {
    val context = LocalContext.current
    val pickMultipleMedia =
        rememberLauncherForActivityResult(PickVisualMedia()) result@{ uri ->
            runCatching {
                val contentResolver = context.contentResolver
                val fileName = uri?.getFileName(contentResolver) ?: return@result
                val bytes = contentResolver.openInputStream(uri).use { stream -> stream?.readBytes() } ?: return@result
                fileName.substringAfterLast('.', "") to bytes
            }.onSuccess(block).onFailure(Throwable::printStackTrace)
        }
    return PhotoPicker { pickMultipleMedia.launch(PickVisualMediaRequest(ImageOnly)) }
}

fun Uri.getFileName(resolver: ContentResolver): String? {
    val returnCursor = resolver.query(this, null, null, null, null) ?: return null
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}
