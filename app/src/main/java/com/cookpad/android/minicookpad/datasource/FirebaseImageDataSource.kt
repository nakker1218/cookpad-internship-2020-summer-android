package com.cookpad.android.minicookpad.datasource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

/**
 * @author nakker
 */
class FirebaseImageDataSource : ImageDataSource {
    private val reference: StorageReference = FirebaseStorage.getInstance().reference

    override fun saveImage(
        uri: String,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        val imageRef = "$COLLECTION_PATH/${UUID.randomUUID()}"
        reference.child(imageRef)
            .putFile(Uri.parse(uri))
            .addOnSuccessListener { onSuccess.invoke(imageRef) }
            .addOnFailureListener(onFailed)
    }

    companion object {
        private const val COLLECTION_PATH = "images"
    }

}
