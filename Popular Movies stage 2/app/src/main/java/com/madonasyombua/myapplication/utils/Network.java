package com.madonasyombua.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * moved from movie Async task
 * @author madon
 */
public class Network {

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}



