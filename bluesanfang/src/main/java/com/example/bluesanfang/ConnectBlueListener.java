package com.example.bluesanfang;

/**
 * Created by TanJieXi on 2018/3/28.
 */

public interface ConnectBlueListener {
    void onConnectSuccess(String message);
    void onNotiFySuccess(String type,String message);
    void onChangeText(String message);
}
