package com.madonasyombua.bakingapp.helpers;

import com.madonasyombua.bakingapp.models.Recipe;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;

interface RecipesApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
