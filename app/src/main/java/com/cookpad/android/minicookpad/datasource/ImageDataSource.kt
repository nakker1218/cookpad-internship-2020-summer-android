package com.cookpad.android.minicookpad.datasource

import android.net.Uri

interface ImageDataSource {
    fun saveImage(uri: Uri, onSuccess: (String) -> Unit, onFailed: (Throwable) -> Unit)
}
