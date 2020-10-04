/*
 * Copyright (C) 2018 Madonah Syombua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.madonasyombua.bakingapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.github.clans.fab.FloatingActionMenu
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.adapters.RecipeListAdapter
import com.madonasyombua.bakingapp.fragment.RecipeStepFragment
import com.madonasyombua.bakingapp.models.Recipe
import com.madonasyombua.bakingapp.utils.SpacingItemDecoration
import com.madonasyombua.bakingapp.widget.AppWidgetService
import com.orhanobut.logger.Logger

class RecipeInfoActivity : AppCompatActivity() {
    @BindView(R.id.recipe_step_list)
    var mRecyclerView: RecyclerView? = null

    @BindView(android.R.id.content)
    var mParentLayout: View? = null

    @BindView(R.id.menuFab)
    var mFabMenul: FloatingActionMenu? = null
    private var mTwoPane = false
    private var mRecipe: Recipe? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_info)
        ButterKnife.bind(this)
        val bundle = intent.extras
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY)
        } else {
            Toast.makeText(this, "failed to load", Toast.LENGTH_SHORT).show()
            finish()
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show the Up button in the action bar and set recipes name as title.
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = mRecipe!!.name
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        mTwoPane = false
        if (mTwoPane) {
            if (savedInstanceState == null && mRecipe!!.steps.isNotEmpty()) {
                showStep(0)
            }
        }
        setupRecyclerView()
    }

    @OnClick(R.id.addToWidgetFab)
    fun startwidget() {
        AppWidgetService.updateWidget(this, mRecipe)
        Toast.makeText(this, "added to widget", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("onDestroy")
    }

    private fun setupRecyclerView() {
        mRecyclerView?.addItemDecoration(SpacingItemDecoration(resources.getDimension(R.dimen.margin_medium).toInt()))
        mRecyclerView?.adapter = mRecipe?.let { RecipeListAdapter(it) { position: Int -> showStep(position) } }
    }

    private fun showStep(position: Int) {
        if (mTwoPane) {
            val arguments = Bundle()
            arguments.putParcelable(RecipeStepFragment.STEP_KEY, mRecipe?.steps?.get(position))
            val fragment = RecipeStepFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit()
        } else {
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra(RecipeDetailActivity.RECIPE_KEY, mRecipe)
            intent.putExtra(RecipeDetailActivity.STEP_SELECTED_KEY, position)
            startActivity(intent)
        }
    }

    companion object {
        const val RECIPE_KEY = "recipe_k"
    }
}