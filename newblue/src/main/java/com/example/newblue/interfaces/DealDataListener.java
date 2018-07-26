package com.example.newblue.interfaces;

/**
 * Created by TanJieXi on 2018/3/28.
 */

/**
 * 处理数据的回调
 */
public interface DealDataListener {
    /**
     * 回调数据
     * @param type  区别是哪个仪器的
     * @param code  100：连接状态等的信息   200:仪器最终数据
     * @param message  返回的数据信息
     */
    void onFetch(String type,int code,String message);
}
