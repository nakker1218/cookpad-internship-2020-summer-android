package com.cookpad.android.minicookpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.android.minicookpad.databinding.FragmentRecipeListBinding
import com.cookpad.android.minicookpad.datasource.FirebaseRecipeDataSource
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListContract
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListInteractor
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListPresenter
import com.cookpad.android.minicookpad.scene.recipelist.RecipeListRouting

class RecipeListFragment : Fragment(), RecipeListContract.View {
    private lateinit var binding: FragmentRecipeListBinding

    private lateinit var adapter: RecipeListAdapter

    lateinit var presenter: RecipeListContract.Presenter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RecipeListPresenter(
                view = this,
                interactor = RecipeListInteractor(FirebaseRecipeDataSource()),
                routing = RecipeListRouting(this)
        )
        presenter.onRecipeListRequested()

        adapter = RecipeListAdapter { recipeId, recipeName ->
            presenter.onRecipeDetailRequested(recipeId, recipeName)
        }.also { binding.recipeList.adapter = it }
        binding.recipeList.layoutManager = LinearLayoutManager(requireContext())
        binding.createButton.setOnClickListener { findNavController().navigate(R.id.createRecipe) }
    }

    override fun renderRecipeList(recipeList: List<RecipeListContract.Recipe>) {
        adapter.update(recipeList)
    }

    override fun renderError(throwable: Throwable) {
        Toast.makeText(requireContext(), "レシピ一覧の取得に失敗しました", Toast.LENGTH_SHORT).show()
    }
}
