package com.cookpad.android.minicookpad.scene.recipe_list

import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity

class RecipeListInteractor(private val recipeDataSource: RecipeDataSource) :
    RecipeListContract.Interactor {
    override fun fetchRecipeList(
        onSuccess: (List<RecipeListContract.Recipe>) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        recipeDataSource.fetchAll(
            onSuccess = { list -> onSuccess.invoke(list.map { it.translate() }) },
            onFailed = onFailed
        )
    }

    private fun RecipeEntity.translate(): RecipeListContract.Recipe {
        return RecipeListContract.Recipe(
            id = id,
            title = title,
            imagePath = imagePath ?: "",
            steps = steps.joinToString("、"),
            authorName = authorName
        )
    }
}
