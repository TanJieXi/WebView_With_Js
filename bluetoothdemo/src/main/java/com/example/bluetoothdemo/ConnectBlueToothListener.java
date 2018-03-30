package com.example.bluetoothdemo;

/**
 * Created by TanJieXi on 2018/3/28.
 */

public interface ConnectBlueToothListener {
    void onConnectSuccess(String message);
    void onInterceptConnect(String message);
    void onDataFromBlue(String type, String message);
}
