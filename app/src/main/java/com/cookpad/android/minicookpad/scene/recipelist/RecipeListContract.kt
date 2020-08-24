package com.cookpad.android.minicookpad.scene.recipelist

interface RecipeListContract {
    interface View {
        fun renderRecipeList(recipeList: List<RecipeListContract.Recipe>)
        fun renderError(throwable: Throwable)
    }

    interface Interactor {
        fun fetchRecipeList(onSuccess: (List<Recipe>) -> Unit, onFailed: (Throwable) -> Unit)
    }

    interface Presenter {
        fun onRecipeListRequested()
    }

    interface Routing

    data class Recipe(
        val id: String,
        val title: String,
        val imagePath: String,
        val steps: String,
        val authorName: String
    )
}
