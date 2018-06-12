package com.madonasyombua.myapplication.interfaces;

/**
 * @author madon
 */
public interface DBUpdateListener {
    void onSuccess(int operationType);

    void onFailure();
}
