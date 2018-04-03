package com.example.bluesanfang;

import android.annotation.SuppressLint;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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


    /**
     * 处理体温计的数据
     * 备注：这个体温计的数据是由设备发送了两段数据过来配对的，所以会有一个追加操作
     * @param data
     */
    private StringBuilder sb = new StringBuilder();
    public void dealTemData(String data, DealDataListener listener) {
        this.mDealDataListener = listener;
        Log.i("sjkljklsjadkll", "数据---》" + data);
        // 11-26 18:54:47.796: D/test(8170): data=AF6A725A0E440088
        sb = sb.append(data);
        if (data != null && !data.equals("")) {
            char[] chars = data.toCharArray();
            String hexStr = new String();
            // 体温
            //家康体温枪
            String str = new String(sb);
            if (str.length() > 15 && str.trim().contains("AF6A")) {
                str = str.trim().substring(str.indexOf("AF6A"));
                if (str.length() > 15) {

                    chars = str.toCharArray();

                    hexStr = "" + chars[8] + chars[9] + chars[10] + chars[11];

                    Log.d("test", "parseInt=" + Integer.parseInt(hexStr, 16));

                    float myweight = Integer.parseInt(hexStr, 16);

                    DecimalFormat formater = new DecimalFormat("#0.#");
                    formater.setRoundingMode(RoundingMode.FLOOR);
                    if ((myweight / 100) >= 29 && (myweight / 100) <= 46) {
                        String result = formater.format(myweight / 100);
                        mDealDataListener.onFetch(200, "体温：" + result);
                    } else {
                        mDealDataListener.onFetch(100, "测量温度不正常,请重新测量!");
                    }
                    sb.delete(0,sb.length());
                }
            }
        }

    }
    // byte转十六进制字符串
    String bytes2HexString(byte[] bytes) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }
    public byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }


}
