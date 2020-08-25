package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.scene.recipecreate.RecipeCreateContract
import com.cookpad.android.minicookpad.scene.recipecreate.RecipeCreatePresenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

/**
 * @author nakker
 */
class RecipeCreatePresenterTest {
    private lateinit var view: RecipeCreateContract.View

    private lateinit var interactor: RecipeCreateContract.Interactor

    private lateinit var routing: RecipeCreateContract.Routing

    private lateinit var presenter: RecipeCreatePresenter

    @Before
    fun setup() {
        view = mock()
        interactor = mock()
        routing = mock()
        presenter = RecipeCreatePresenter(view, interactor, routing)
    }

    @Test
    fun success_onRecipeSaveRequested() {
        val recipe = RecipeCreateContract.Recipe(
            title = "美味しいきゅうりの塩もみ",
            imageUri = "images/recipe.png",
            steps = listOf("きゅうりを切る", "塩をまく", "もむ")
        )
        whenever(interactor.saveRecipe(any(), any(), any())).then {
            (it.arguments[1] as () -> Unit).invoke()
        }

        presenter.onRecipeSaveRequested(recipe)

        verify(routing, times(1)).navigateRecipeSaved()
        verify(view, times(0)).renderError()
    }

    @Test
    fun failed_onRecipeSaveRequested() {
        val recipe = RecipeCreateContract.Recipe(
            title = "美味しいきゅうりの塩もみ",
            imageUri = "images/recipe.png",
            steps = listOf("きゅうりを切る", "塩をまく", "もむ")
        )
        val throwable = Throwable("Failed")
        whenever(interactor.saveRecipe(any(), any(), any())).then {
            (it.arguments[2] as (Throwable) -> Unit).invoke(throwable)
        }

        presenter.onRecipeSaveRequested(recipe)

        verify(routing, times(0)).navigateRecipeSaved()
        verify(view, times(1)).renderError()
    }
}
