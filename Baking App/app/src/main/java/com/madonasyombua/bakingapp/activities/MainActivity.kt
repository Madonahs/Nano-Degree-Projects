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
import androidx.fragment.app.FragmentActivity
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.fragment.RecipeFragment

import com.madonasyombua.bakingapp.models.Recipe

import com.orhanobut.logger.Logger

class MainActivity : FragmentActivity(), RecipeFragment.OnRecipeClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * the interface on RecipeFragment that starts the Recipe info activity
     * @param recipe the recipe model
     */
    override fun onRecipeSelected(recipe: Recipe?) {
        val intent = Intent(this, RecipeInfoActivity::class.java)
        intent.putExtra(RecipeInfoActivity.RECIPE_KEY, recipe)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Logger.d(TAG, "Stopped")
    }

    override fun onPause() {
        super.onPause()
        Logger.d(TAG, "Paused")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(TAG, "Destroyed")
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}