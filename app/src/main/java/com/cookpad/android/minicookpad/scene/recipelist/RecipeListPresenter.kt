package com.cookpad.android.minicookpad.scene.recipelist

class RecipeListPresenter(
    private val view: RecipeListContract.View,
    private val interactor: RecipeListInteractor,
    private val routing: RecipeListContract.Routing
) : RecipeListContract.Presenter {
    override fun onRecipeListRequested() {
        interactor.fetchRecipeList(
            onSuccess = { view.renderRecipeList(it) },
            onFailed = { view.renderError(it) }
        )
    }

    override fun onRecipeDetailRequested(recipeId: String, recipeName: String) {
        routing.navigateRecipeDetail(recipeId, recipeName)
    }
}
