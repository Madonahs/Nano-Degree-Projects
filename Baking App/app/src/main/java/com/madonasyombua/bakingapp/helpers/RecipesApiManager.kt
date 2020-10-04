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
package com.madonasyombua.bakingapp.helpers

import com.madonasyombua.bakingapp.models.Recipe
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.Serializable

class RecipesApiManager private constructor() : Serializable {
    private val recipesApi: RecipesApi
    fun getRecipes(apiCallback: ApiCallback<List<Recipe?>?>) {
        recipesApi.recipes!!.enqueue(object : Callback<List<Recipe?>?> {
            override fun onResponse(call: Call<List<Recipe?>?>, response: Response<List<Recipe?>?>) {
                apiCallback.onResponse(response.body())
            }

            override fun onFailure(call: Call<List<Recipe?>?>, t: Throwable) {
                if (call.isCanceled) {
                    Logger.e("Request was cancelled")
                    apiCallback.onCancel()
                } else {
                    Logger.e(t.message)
                    apiCallback.onResponse(null)
                }
            }
        })
    }

    companion object {
        private const val RECIPES_API_URL = "https://d17h27t6h515a5.cloudfront.net/"

        @Volatile
        private var sharedInstance: RecipesApiManager? = RecipesApiManager()
        @JvmStatic
        val instance: RecipesApiManager?
            get() {
                if (sharedInstance == null) {
                    synchronized(RecipesApiManager::class.java) { if (sharedInstance == null) sharedInstance = RecipesApiManager() }
                }
                return sharedInstance
            }
    }

    init {
        //Prevent from the reflection api.
        if (sharedInstance != null) {
            throw RuntimeException("Use getInstance() method to get the single instance of this class.")
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(RECIPES_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        recipesApi = retrofit.create(RecipesApi::class.java)
    }
}