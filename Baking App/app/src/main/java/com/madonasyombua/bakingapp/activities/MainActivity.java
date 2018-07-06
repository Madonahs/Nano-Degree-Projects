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
