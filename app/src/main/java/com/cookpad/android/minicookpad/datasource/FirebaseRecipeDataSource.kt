package com.cookpad.android.minicookpad.datasource

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRecipeDataSource : RecipeDataSource {
    private val firestore = FirebaseFirestore.getInstance()

    override fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit) {
        firestore.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                onSuccess.invoke(result.mapNotNull { RecipeEntity.fromDocument(it) })
            }
            .addOnFailureListener(onFailed)
    }
}
