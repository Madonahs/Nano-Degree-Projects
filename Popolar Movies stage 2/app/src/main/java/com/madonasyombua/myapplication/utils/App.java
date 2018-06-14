package com.madonasyombua.myapplication.utils;

import android.app.Application;

import com.madonasyombua.myapplication.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;

public class App extends Application {
    /**
     * for easier log checks
     */
    public App() {
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}