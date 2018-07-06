package com.madonasyombua.bakingapp.helpers;


public interface ApiCallback<T> {
    void onResponse(T result);

    void onCancel();
}
