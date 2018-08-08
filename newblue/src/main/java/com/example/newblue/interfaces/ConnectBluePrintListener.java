package com.example.newblue.interfaces;

/**
 * Created by TanJieXi on 2018/3/28.
 */

public interface ConnectBluePrintListener {
    /**
     * 连接成功
     * @param status 0:连接中  1：成功  2：失败  3：重新连接
     * @param  message 连接数据
     */
    void onConnectStatus(int status,String message);

    /**
     * 收到设备传来的数据
     */
    void onDataFromBlue(String type, String message);
}
