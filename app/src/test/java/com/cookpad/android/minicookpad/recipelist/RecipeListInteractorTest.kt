package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.RecipeEntity
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListContract
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListInteractor
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RecipeListInteractorTest {
    lateinit var recipeDataSource: RecipeDataSource
    lateinit var interactor: RecipeListInteractor

    @Before
    fun setup() {
        recipeDataSource = mock()
        interactor = RecipeListInteractor(recipeDataSource)
    }

    @Test
    fun verifyFetchRecipeListSuccess() {
        val onSuccess: (List<RecipeListContract.Recipe>) -> Unit = mock()
        val recipeList = listOf(
            RecipeEntity(
                id = "xxx",
                title = "美味しいきゅうりの塩もみ",
                imagePath = "images/recipe.png",
                steps = listOf("きゅうりを切る", "塩をまく", "もむ"),
                authorName = "クックパド美"
            )
        )

        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[0] as (List<RecipeEntity>) -> Unit).invoke(recipeList)
        }

        val argumentCaptor = argumentCaptor<List<RecipeListContract.Recipe>>()
        interactor.fetchRecipeList(onSuccess, {})
        verify(onSuccess).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.first().apply {
            assertThat(id).isEqualTo("xxx")
            assertThat(title).isEqualTo("美味しいきゅうりの塩もみ")
            assertThat(imagePath).isEqualTo("images/recipe.png")
            assertThat(steps).isEqualTo("きゅうりを切る、塩をまく、もむ")
            assertThat(authorName).isEqualTo("クックパド美")
        }
    }

    @Test
    fun verifyFetchRecipeListError() {
        val onFailed: (Throwable) -> Unit = mock()
        val throwable = Throwable("Recipe not found")
        whenever(recipeDataSource.fetchAll(any(), any())).then {
            (it.arguments[1] as (Throwable) -> Unit).invoke(throwable)
        }

        interactor.fetchRecipeList({}, onFailed)

        val argumentCaptor = argumentCaptor<Throwable>()
        verify(onFailed).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.apply {
            assertThat(message).isEqualTo("Recipe not found")
        }
    }
}
