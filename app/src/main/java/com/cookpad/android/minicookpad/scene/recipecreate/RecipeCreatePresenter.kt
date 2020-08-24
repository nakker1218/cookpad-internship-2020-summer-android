package com.cookpad.android.minicookpad.scene.recipecreate

class RecipeCreatePresenter(
    val view: RecipeCreateContract.View,
    val interactor: RecipeCreateContract.Interactor,
    val routing: RecipeCreateContract.Routing
) : RecipeCreateContract.Presenter {

    override fun onRecipeSaveRequested(recipe: RecipeCreateContract.Recipe) {
        interactor.saveRecipe(
            recipe = recipe,
            onSuccess = {
                routing.navigateRecipeSaved()
            },
            onFailure = {
                view.renderError()
            }
        )
    }
}
