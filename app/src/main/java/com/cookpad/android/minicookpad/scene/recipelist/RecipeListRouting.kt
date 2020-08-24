package com.cookpad.android.minicookpad.scene.recipelist

import androidx.navigation.fragment.findNavController
import com.cookpad.android.minicookpad.RecipeListFragment
import com.cookpad.android.minicookpad.RecipeListFragmentDirections

class RecipeListRouting(private val fragment: RecipeListFragment) : RecipeListContract.Routing {
    override fun navigateRecipeDetail(recipeId: String, recipeName: String) {
        fragment.findNavController()
            .navigate(RecipeListFragmentDirections.showRecipeDetail(recipeId, recipeName))
    }
}
