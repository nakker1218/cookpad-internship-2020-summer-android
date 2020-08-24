package com.cookpad.android.minicookpad.scene.recipelist

class RecipeListPresenter(
    private val view: RecipeListContract.View,
    private val interactor: RecipeListInteractor
) : RecipeListContract.Presenter {
    override fun onRecipeListRequested() {
        interactor.fetchRecipeList(
            onSuccess = { view.renderRecipeList(it) },
            onFailed = { view.renderError(it) }
        )
    }
}
