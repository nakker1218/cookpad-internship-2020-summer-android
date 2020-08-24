package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.scene.recipelist.RecipeListContract
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListPresenter
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeListPresenterTest {
    private lateinit var view: RecipeListContract.View

    private lateinit var interactor: RecipeListContract.Interactor

    private lateinit var routing: RecipeListContract.Routing

    private lateinit var presenter: RecipeListPresenter

    @Before
    fun setup() {
        view = mock()
        interactor = mock()
        routing = mock()
        presenter = RecipeListPresenter(view, interactor, routing)
    }

    @Test
    fun verifyOnRecipeListRequestedSuccess() {
        val recipeList = listOf(
            RecipeListContract.Recipe(
                id = "xxx",
                title = "美味しいきゅうりの塩もみ",
                imagePath = "images/recipe.png",
                steps = "きゅうりを切る、塩をまく、もむ",
                authorName = "クックパド美"
            )
        )

        whenever(interactor.fetchRecipeList(any(), any())).then {
            (it.arguments[0] as (List<RecipeListContract.Recipe>) -> Unit).invoke(recipeList)
        }

        presenter.onRecipeListRequested()

        verify(view, times(1)).renderRecipeList(any())
        verify(view, times(0)).renderError(any())

        val argumentCaptor = argumentCaptor<List<RecipeListContract.Recipe>>()
        verify(view).renderRecipeList(argumentCaptor.capture())
        argumentCaptor.firstValue.first().apply {
            assertThat(id).isEqualTo("xxx")
            assertThat(title).isEqualTo("美味しいきゅうりの塩もみ")
            assertThat(imagePath).isEqualTo("images/recipe.png")
            assertThat(steps).isEqualTo("きゅうりを切る、塩をまく、もむ")
            assertThat(authorName).isEqualTo("クックパド美")
        }
    }

    @Test
    fun verifyOnRecipeListRequestedError() {
        val throwable = Throwable("Recipe list requested error")

        whenever(interactor.fetchRecipeList(any(), any())).then {
            (it.arguments[1] as (Throwable) -> Unit).invoke(throwable)
        }

        presenter.onRecipeListRequested()

        verify(view, times(0)).renderRecipeList(any())
        verify(view, times(1)).renderError(any())

        val argumentCaptor = argumentCaptor<Throwable>()
        verify(view).renderError(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue).isEqualTo(throwable)
    }

    @Test
    fun verifyOnRecipeDetailRequested() {
        val recipeId = "xxx"
        val recipeName = "美味しいきゅうりの塩もみ"

        presenter.onRecipeDetailRequested(recipeId, recipeName)

        verify(routing, times(1)).navigateRecipeDetail(any(), any())

        val recipeIdArgumentCaptor = argumentCaptor<String>()
        val recipeNameArgumentCaptor = argumentCaptor<String>()
        verify(routing).navigateRecipeDetail(recipeIdArgumentCaptor.capture(), recipeNameArgumentCaptor.capture())
        assertThat(recipeIdArgumentCaptor.firstValue).isEqualTo(recipeId)
        assertThat(recipeNameArgumentCaptor.firstValue).isEqualTo(recipeName)
    }
}
