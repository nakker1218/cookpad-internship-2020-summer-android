package com.cookpad.android.minicookpad.scene.recipecreate

import android.net.Uri

/**
 * @author nakker
 */
interface RecipeCreateContract {
    interface View {
        fun renderError()
    }

    interface Interactor {
        fun saveRecipe(recipe: Recipe, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit)
    }

    interface Presenter {
        fun onRecipeSaveRequested(recipe: Recipe)
    }

    interface Routing {
        fun navigateRecipeSaved()
    }

    data class Recipe(
        val imageUri: String,
        val title: String,
        val steps: List<String>
    )
}
