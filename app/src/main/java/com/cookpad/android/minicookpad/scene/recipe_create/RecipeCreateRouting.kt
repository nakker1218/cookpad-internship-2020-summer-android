package com.cookpad.android.minicookpad.scene.recipe_create

/**
 * @author nakker
 */
class RecipeCreateRouting(private val activity: RecipeCreateActivity) :
    RecipeCreateContract.Routing {
    override fun navigateRecipeSaved() {
        activity.finish()
    }
}
