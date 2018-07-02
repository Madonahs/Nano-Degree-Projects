package com.madonasyombua.bakingapp.app;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.madonasyombua.bakingapp.BuildConfig;
import com.madonasyombua.bakingapp.utils.RecipesIdlingResources;
import com.orhanobut.logger.AndroidLogAdapter;


public class AppLogger  extends Application{

    private RecipesIdlingResources recipesIdlingResources;


    public AppLogger(){
        if(BuildConfig.DEBUG){
            initializedIdlingResources();
        }

        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {

                return BuildConfig.DEBUG;
            }
        });
    }

    @VisibleForTesting
    private void initializedIdlingResources(){
        if(recipesIdlingResources == null){
            recipesIdlingResources = new RecipesIdlingResources();
        }
    }

    public void setIdleState(boolean idleState){
        if(recipesIdlingResources != null){
            recipesIdlingResources.recordIdleState(idleState);
        }
    }

    public RecipesIdlingResources getRecipesIdlingResources() {
        return recipesIdlingResources;
    }
}
