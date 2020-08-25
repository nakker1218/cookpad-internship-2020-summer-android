package com.cookpad.android.minicookpad.datasource

import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity

interface RecipeDataSource {
    fun fetchAll(onSuccess: (List<RecipeEntity>) -> Unit, onFailed: (Throwable) -> Unit)

    fun createRecipe(
        recipe: RecipeEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )
}
