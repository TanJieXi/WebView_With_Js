package com.example.newblue.interfaces;

/**
 * Created by TanJieXi on 2018/3/28.
 */

public interface ConnectBlueToothListener {
    /**
     * 连接成功
     */
    void onConnectSuccess(String message);

    /**
     * 连接失败或者重新连接
     */
    void onInterceptConnect(String message);

    /**
     * 收到设备传来的数据
     */
    void onDataFromBlue(String type, String message);

    /**
     * 重新连接
     */
    void onReConnectEqip(String message);
}
