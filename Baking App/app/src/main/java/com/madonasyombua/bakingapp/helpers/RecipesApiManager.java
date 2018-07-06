package com.madonasyombua.bakingapp.helpers;

import android.support.annotation.NonNull;

import com.madonasyombua.bakingapp.models.Recipe;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class RecipesApiManager implements Serializable {

   private static final String RECIPES_API_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static volatile RecipesApiManager sharedInstance = new RecipesApiManager();
    private RecipesApi recipesApi;

    private RecipesApiManager() {
        //Prevent from the reflection api.
        if (sharedInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        recipesApi = retrofit.create(RecipesApi.class);
    }

    public static RecipesApiManager getInstance() {
        if (sharedInstance == null) {
            synchronized (RecipesApiManager.class) {
                if (sharedInstance == null) sharedInstance = new RecipesApiManager();
            }
        }

        return sharedInstance;
    }

    public void getRecipes(final ApiCallback<List<Recipe>> apiCallback) {
        recipesApi.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                apiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Logger.e("Request was cancelled");
                    apiCallback.onCancel();
                } else {
                    Logger.e(t.getMessage());
                    apiCallback.onResponse(null);
                }
            }
        });
    }

}

