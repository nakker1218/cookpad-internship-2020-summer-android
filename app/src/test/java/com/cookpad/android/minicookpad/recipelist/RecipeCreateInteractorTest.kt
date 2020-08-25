package com.cookpad.android.minicookpad.recipelist

import com.cookpad.android.minicookpad.datasource.ImageDataSource
import com.cookpad.android.minicookpad.datasource.RecipeDataSource
import com.cookpad.android.minicookpad.datasource.entity.RecipeEntity
import com.cookpad.android.minicookpad.scene.recipe_create.RecipeCreateContract
import com.cookpad.android.minicookpad.scene.recipe_create.RecipeCreateInteractor
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

/**
 * @author nakker
 */
class RecipeCreateInteractorTest {
    lateinit var imageDataSource: ImageDataSource
    lateinit var recipeDataSource: RecipeDataSource

    lateinit var interactor: RecipeCreateInteractor

    @Before
    fun setup() {
        imageDataSource = mock()
        recipeDataSource = mock()
        interactor = RecipeCreateInteractor(imageDataSource, recipeDataSource)
    }

    @Test
    fun success_saveRecipe() {
        val onSuccess = mock<() -> Unit>()
        val recipe = RecipeCreateContract.Recipe(
            title = "美味しいきゅうりの塩もみ",
            imageUri = "images/recipe.png",
            steps = listOf("きゅうりを切る", "塩をまく", "もむ")
        )
        val imagePath = "images/87360ebc00235b3b9b03e1716844de57"

        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke(imagePath)
        }
        whenever(recipeDataSource.createRecipe(any(), any(), any())).then {
            (it.arguments[1] as () -> Unit).invoke()
        }

        interactor.saveRecipe(recipe, onSuccess, {})

        val recipeArgumentCapture = argumentCaptor<RecipeEntity>()
        verify(recipeDataSource).createRecipe(recipeArgumentCapture.capture(), any(), any())
        recipeArgumentCapture.firstValue.apply {
            assertThat(this.imagePath).isEqualTo(imagePath)
            assertThat(title).isEqualTo(recipe.title)
            assertThat(steps).isEqualTo(recipe.steps)
        }

        verify(onSuccess, times(1)).invoke()
    }

    @Test
    fun failed_imageUpload_saveRecipe() {
        val recipe = RecipeCreateContract.Recipe(
            title = "美味しいきゅうりの塩もみ",
            imageUri = "images/recipe.png",
            steps = listOf("きゅうりを切る", "塩をまく", "もむ")
        )
        val onFailed = mock<(Throwable) -> Unit>()
        val throwable = Throwable("Network error")
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[2] as (Throwable) -> Unit).invoke(throwable)
        }

        interactor.saveRecipe(recipe, {}, onFailed)

        val argumentCaptor = argumentCaptor<Throwable>()
        verify(onFailed).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.apply {
            assertThat(message).isEqualTo(throwable.message)
        }
    }

    @Test
    fun failed_createRecipe_saveRecipe() {
        val recipe = RecipeCreateContract.Recipe(
            title = "美味しいきゅうりの塩もみ",
            imageUri = "images/recipe.png",
            steps = listOf("きゅうりを切る", "塩をまく", "もむ")
        )
        val imagePath = "images/87360ebc00235b3b9b03e1716844de57"
        val onFailed = mock<(Throwable) -> Unit>()
        val throwable = Throwable("Network error")
        whenever(imageDataSource.saveImage(any(), any(), any())).then {
            (it.arguments[1] as (String) -> Unit).invoke(imagePath)
        }
        whenever(recipeDataSource.createRecipe(any(), any(), any())).then {
            (it.arguments[2] as (Throwable) -> Unit).invoke(throwable)
        }

        interactor.saveRecipe(recipe, {}, onFailed)

        val argumentCaptor = argumentCaptor<Throwable>()
        verify(onFailed).invoke(argumentCaptor.capture())
        argumentCaptor.firstValue.apply {
            assertThat(message).isEqualTo(throwable.message)
        }
    }
}
