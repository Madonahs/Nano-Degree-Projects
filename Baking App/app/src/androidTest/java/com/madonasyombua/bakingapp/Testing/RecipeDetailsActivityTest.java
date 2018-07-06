package com.madonasyombua.bakingapp.Testing;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.madonasyombua.bakingapp.R;
import com.madonasyombua.bakingapp.activities.MainActivity;
import com.madonasyombua.bakingapp.activities.RecipeDetailActivity;
import com.madonasyombua.bakingapp.activities.RecipeInfoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


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
