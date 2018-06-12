package com.madonasyombua.myapplication.interfaces;

public interface DataRequest {

    void onFailure(Throwable throwable);//if fetch failed
    void onSuccess(Object object);//if fetch successful
}
