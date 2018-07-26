package com.example.newblue.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TanJieXi on 2018/3/30.
 */

public class Utils {

    /**
     * 16进制转字符串
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        return sb.toString();
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static int twototen(String str) {
        return Integer.valueOf(str, 2);
    }

    /**
     * 累加和校验码
     */
    public static String Checksum(String hex) {
        int chekint = 0;
        String output = "";
        for (int i = 0; i < hex.length() - 1; i = i + 2) {
            output = hex.substring(i, i + 2);
            if (output.length() > 1) {
                int decimal = Integer.parseInt(output, 16);
                chekint = chekint + decimal;
            }
        }
        chekint = chekint % 256;
        return intToHex(chekint);
    }

    /**
     * 256以下10进制数字转16进制2位字符
     */
    public static String intToHex(int intNum) {
        String output = "";
        output = Integer.toHexString(intNum);
        if (output.length() == 1) {
            output = "0" + output;
        }
        return output;
    }

    /**
     * byte[]转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytes2HexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    // 把日期转为字符串
    public static String ConverToString(Date date) {
        if(date == null){
            return "";
        }
        return ConverToString(date, "yyyy-MM-dd");
    }

    // 把日期转为字符串
    public static String ConverToString(Date date, String type) {
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date);
    }
    /**
     * 计算年龄
     *
     * @param birth yyyy-MM-dd
     * @return
     */
    public static String calcAge(String birth) {
        if (!TextUtils.isEmpty(birth)) {
            String str = "";


            Date birthDate = null;

            try {
                birthDate = Utils.ConverToDate(birth);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Calendar flightCal = Calendar.getInstance();
            flightCal.setTime(new Date());
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(birthDate);

            int y = flightCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            int m = flightCal.get(Calendar.MONTH)
                    - birthCal.get(Calendar.MONTH);
            int d = flightCal.get(Calendar.DATE) - birthCal.get(Calendar.DATE);
            if (y < 0) {

            }

            if (m < 0) {
                y--;
            }
            if (m >= 0 && d < 0) {
                y--;
            }
            str = String.valueOf(y);

            return str;
        } else {
            return "-1";
        }

    }

    // 把字符串转为日期
    public static Date ConverToDate(String strDate) {
        return ConverToDate(strDate, "yyyy-MM-dd");
    }
    // 把字符串转为日期
    public static Date ConverToDate(String strDate, String type) {


        try {
            if (!TextUtils.isEmpty(strDate)) {
                DateFormat df = new SimpleDateFormat(type);
                return df.parse(strDate);
            } else {
                return null;
            }
        } catch (ParseException e) {
            Log.e("Conver", "ConverToDate出错：strDate=" + strDate + ";type=" + type);
            return null;
        }

    }


    public static byte[] getHexBytes(String message) {
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
