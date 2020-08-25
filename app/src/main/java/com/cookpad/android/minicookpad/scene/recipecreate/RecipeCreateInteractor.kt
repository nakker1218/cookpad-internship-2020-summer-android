package com.cookpad.android.minicookpad.scene.recipecreate

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.RecipeEntity
import java.util.*

/**
 * @author nakker
 */
class RecipeCreateInteractor(
    private val imageDataSource: ImageDataSource,
    private val recipeDataSource: RecipeDataSource
) :
    RecipeCreateContract.Interactor {

    override fun saveRecipe(
        recipe: RecipeCreateContract.Recipe,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        imageDataSource.saveImage(
            uri = recipe.imageUri,
            onSuccess = {
                val entity = RecipeEntity(
                    id = UUID.randomUUID().toString(),
                    title = recipe.title,
                    imagePath = it,
                    authorName = "田中太郎",
                    steps = recipe.steps
                )
                recipeDataSource.createRecipe(
                    recipe = entity,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            },
            onFailure = onFailure
        )
    }
}
