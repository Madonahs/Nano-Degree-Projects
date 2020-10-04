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

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.adapters.StepsAdapter
import com.madonasyombua.bakingapp.models.Recipe
import com.orhanobut.logger.Logger

class RecipeDetailActivity : AppCompatActivity() {
    @BindView(R.id.recipe_step_tab_layout)
    var tabLayout: TabLayout? = null

    @BindView(R.id.recipe_step_viewpager)
    var viewPager: ViewPager? = null
    private var mRecipe: Recipe? = null
    private var mStepSelectedPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_step_detail)
        ButterKnife.bind(this)
        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        val bundle = intent.extras
        if (bundle != null && bundle.containsKey(RECIPE_KEY) && bundle.containsKey(STEP_SELECTED_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY)
            mStepSelectedPosition = bundle.getInt(STEP_SELECTED_KEY)
        } else {
            Toast.makeText(this, "Failed to Load", Toast.LENGTH_SHORT).show()
            finish()
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = mRecipe?.name
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val adapter = StepsAdapter(applicationContext, mRecipe?.steps, supportFragmentManager)
        viewPager?.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                actionBar?.title = mRecipe?.steps?.get(position)?.shortDescription
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPager?.currentItem = mStepSelectedPosition
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("onDestroy")
    }

    companion object {
        const val RECIPE_KEY = "recipe_k"
        const val STEP_SELECTED_KEY = "step_k"
    }
}