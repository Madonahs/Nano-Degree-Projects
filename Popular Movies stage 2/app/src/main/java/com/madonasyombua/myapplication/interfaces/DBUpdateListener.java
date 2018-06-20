package com.madonasyombua.myapplication.interfaces;

/**
 * @author madon
 */
public interface DBUpdateListener<T> {
    void onResponse(T result);

    void onCancel();
}