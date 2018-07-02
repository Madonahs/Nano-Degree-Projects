package com.madonasyombua.bakingapp.Testing;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.madonasyombua.bakingapp.activities.MainActivity;
import com.madonasyombua.bakingapp.app.AppLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


public abstract class BaseTest {
    private AppLogger appLogger;
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdlingResource(){
        appLogger = (AppLogger) activityActivityTestRule.getActivity().getApplicationContext();
        idlingResource = appLogger.getRecipesIdlingResources();

        IdlingRegistry.getInstance().register(idlingResource);

    }

    @After
    public void unregisterIdlingResources(){
        if(idlingResource != null){
            IdlingRegistry.getInstance().register(idlingResource);
        }
    }
}
