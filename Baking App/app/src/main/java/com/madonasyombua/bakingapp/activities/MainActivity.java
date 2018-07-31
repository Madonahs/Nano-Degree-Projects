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
package com.madonasyombua.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.madonasyombua.bakingapp.R;
import com.madonasyombua.bakingapp.fragments.RecipeFragment;
import com.madonasyombua.bakingapp.models.Recipe;
import com.orhanobut.logger.Logger;


public class MainActivity extends FragmentActivity implements RecipeFragment.OnRecipeClickListener {

    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * the interface on RecipeFragment that starts the Recipe info activity
     * @param recipe the recipe model
     */
    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeInfoActivity.class);
        intent.putExtra(RecipeInfoActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG,"onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Logger.d(TAG,"onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG,"Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG,"Paused");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"Destroyed");
    }
}
