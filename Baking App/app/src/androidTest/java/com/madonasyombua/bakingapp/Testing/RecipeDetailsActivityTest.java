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
package com.madonasyombua.bakingapp.Testing;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.madonasyombua.bakingapp.R;
import com.madonasyombua.bakingapp.activities.MainActivity;
import com.madonasyombua.bakingapp.activities.RecipeDetailActivity;
import com.madonasyombua.bakingapp.activities.RecipeInfoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        //Checks if the key is present
        Intents.init();

        getRecipeInfo(0);
        intended(hasExtraWithKey(RecipeInfoActivity.RECIPE_KEY));

        Intents.release();


    }

    @Test
    public void clickOnRecyclerViewItemOpensRecipeInfoActivity() {

        getRecipeInfo(0);

        onView(withId(R.id.ingredients_text))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_step_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewStepItemOpensRecipeStepActivityOrFragment() {
        getRecipeInfo(0);

        boolean twoPaneMode = false;
        if (!twoPaneMode) {
            Intents.init();
            selectRecipeStep(1);
            intended(hasComponent(RecipeDetailActivity.class.getName()));
            intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));
            intended(hasExtraWithKey(RecipeDetailActivity.STEP_SELECTED_KEY));
            Intents.release();


            onView(withId(R.id.recipe_step_tab_layout))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            selectRecipeStep(1);

            onView(withId(R.id.exo_player_view))
                    .check(matches(isDisplayed()));
        }


    }

    public static void getRecipeInfo(int recipePosition) {
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }
}
