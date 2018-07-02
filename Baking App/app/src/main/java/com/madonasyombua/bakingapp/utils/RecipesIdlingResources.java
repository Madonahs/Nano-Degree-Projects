package com.madonasyombua.bakingapp.utils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipesIdlingResources implements IdlingResource {

    private volatile ResourceCallback resourceCallback;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return atomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        this.resourceCallback = callback;

    }

    public void recordIdleState(boolean idleState){
        atomicBoolean.set(idleState);

        if(idleState && resourceCallback != null){

            resourceCallback.onTransitionToIdle();
        }
    }
}
