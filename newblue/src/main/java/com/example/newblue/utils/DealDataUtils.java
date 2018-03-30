package com.example.newblue.utils;

import android.annotation.SuppressLint;

import com.example.newblue.interfaces.DealDataListener;

/**
 * Created by TanJieXi on 2018/3/30.
 */

public class DealDataUtils {
    private DealDataListener mDealDataListener;
    private static volatile DealDataUtils instance = null;

    private DealDataUtils() {

    }

    public static DealDataUtils getInstance() {
        if (instance == null) {
            synchronized (DealDataUtils.class) {
                if (instance == null) {
                    instance = new DealDataUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 处理血氧计的数据
     *
     * @param data
     */
    @SuppressLint("DefaultLocale")
    public void dealOxiData(String data, DealDataListener listener) {
        this.mDealDataListener = listener;
        if (data != null) {
            System.out.println("data:" + data);
            char[] chars = data.toCharArray();
            String hexStr = "";

            if (data.contains("55AA03")) {
                if (data.contains("55AA03B100B4")) {
                    mDealDataListener.onFetch(100, "血氧脉率测量中，请稍候...");
                } else {
                    if (data.length() > 11) {
                        // 07-05 18:03:37.544: D/test(8103): data=55AA03624BB0
                        hexStr = "" + chars[6] + chars[7];
                        int myoxi = Integer.parseInt(hexStr, 16);
                        hexStr = "" + chars[8] + chars[9];
                        int mypulse = Integer.parseInt(hexStr, 16);
                        String result = "";
                        result = String.format("血氧：%d ;脉率：%d 次每分钟；", myoxi,
                                mypulse);
                        mDealDataListener.onFetch(200, result);
                        // 检测成功给界面组件赋值 袁明全加 20151001
                    }
                }
            }
        }
    }
}
