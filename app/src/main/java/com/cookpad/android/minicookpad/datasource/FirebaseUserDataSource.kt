package com.cookpad.android.minicookpad.datasource

import com.cookpad.android.minicookpad.datasource.entity.UserEntity
import com.google.firebase.firestore.FirebaseFirestore

/**
 * @author nakker
 */
class FirebaseUserDataSource : RemoteUserDataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun fetch(
        name: String,
        onSuccess: (UserEntity?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        firestore.collection(COLLECTION_PATH)
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener {
                it.documents
                    .firstOrNull()
                    ?.let { documentSnapshot ->
                        onSuccess.invoke(UserEntity.fromDocument(documentSnapshot))
                    }
                    ?: onSuccess.invoke(null)
            }
            .addOnFailureListener(onFailure)
    }

    override fun save(
        userEntity: UserEntity,
        onSuccess: (UserEntity) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        firestore.collection(COLLECTION_PATH)
            .add(userEntity.toMap())
            .addOnSuccessListener {
                onSuccess.invoke(userEntity.copy(id = it.id))
            }
            .addOnFailureListener(onFailure)
    }

    companion object {
        private const val COLLECTION_PATH = "users"
    }
}
