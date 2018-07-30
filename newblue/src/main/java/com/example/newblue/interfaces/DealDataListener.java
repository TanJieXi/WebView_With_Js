package com.example.newblue.interfaces;

/**
 * Created by TanJieXi on 2018/3/28.
 */

import org.json.JSONObject;

/**
 * 处理数据的回调
 */
public interface DealDataListener {
    /**
     * 仪器状态，或者失败等提醒
     * @param type  区别是哪个仪器的
     * @param code  100：连接状态等的信息
     * @param message  错误信息
     */
    void onStatusFetch(String type,int code,String message);
    /**
     * 回调数据
     * @param type  区别是哪个仪器的
     * @param code    200:仪器最终数据
     * @param resulJson  返回的是处理之后的数据信息
     */
    void onFetch(String type, int code, JSONObject resulJson);
}
