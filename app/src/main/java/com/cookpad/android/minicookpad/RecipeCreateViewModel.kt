package com.cookpad.android.minicookpad

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeCreateViewModel : ViewModel() {
    private val _imageUri = MutableLiveData<Uri>()

    val imageUri: LiveData<Uri> = _imageUri

    fun updateImageUri(uri: Uri) {
        _imageUri.postValue(uri)
    }
}
