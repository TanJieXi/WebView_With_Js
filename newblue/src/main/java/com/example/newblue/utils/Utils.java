package com.example.newblue.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    /**
     * @param birthDayStr
     * @return
     * @throws Exception String
     * @方法名称: getAge
     * @方法描述: 根据当前日期获取年龄
     * @Date : 2017-12-19
     */
    public static String getAge(String birthDayStr) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Date birthDay = format.parse(birthDayStr);
        Calendar calBir = Calendar.getInstance();
        calBir.setTime(birthDay);
        // 获取当前系统时间
        Calendar cal = Calendar.getInstance();
        // 如果出生日期大于当前时间，则抛出异常
        if (cal.before(calBir)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        // 取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        // 取出出生日期的年、月、日部分
        int yearBirth = calBir.get(Calendar.YEAR);
        int monthBirth = calBir.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = calBir.get(Calendar.DAY_OF_MONTH);
        String resAge = "";
        // 如果当前年份大于生日年份
        if (yearNow > yearBirth) {
            int age = yearNow - yearBirth;
            // 如果当前年比生日年份大一年
            if (yearNow - yearBirth == 1) {
                //如果为临近一个月  2017-12-31/2018-01-01
                if ((monthNow + 11) == monthBirth) {
                    if (dayOfMonthBirth > dayOfMonthNow) {
                        resAge = ((calBir.getActualMaximum(Calendar.DATE) - (dayOfMonthBirth - dayOfMonthNow)) + 1)
                                + "天";
                    } else {
                        // 如果当前日期不小于生日日期 则为1月
                        resAge = "1个月";
                    }
                } else if (monthNow < monthBirth) {// 如果当前月份比生日月份小 说明不满一年 计算月份
                    resAge = (12 - (monthBirth - monthNow) + 1) + "个月";
                } else {
                    // 如果当前月份不小于生日月份 则为1岁
                    resAge = "1岁";
                }
            } else {// 如果当前年份比生日年份超出1年一年以上 则计算岁
                // 如果当前月份比生日月份小，说明不满整岁，减去一岁
                if ((monthNow < monthBirth)) {
                    resAge = (age - 1) + "岁";
                } else if ((monthNow == monthBirth) && (dayOfMonthNow < dayOfMonthBirth)) {//如果当前月份等于生日月份，并且当前日期小于生日日期，说明不满整岁，减去一岁
                    resAge = (age - 1) + "岁";
                } else {
                    resAge = age + "岁";
                }
            }
        } else if (yearNow == yearBirth) {// 如果当前年份相同
            int age = 0;
            // 如果当前月份比生日月份大
            if (monthNow > monthBirth) {
                age = monthNow - monthBirth;
                // 如果当前月份只比生日月份大一月
                if (monthNow - monthBirth == 1) {
                    // 如果当前日期比生日日期小 则计算天数
                    if (dayOfMonthBirth > dayOfMonthNow)
                        resAge = ((calBir.getActualMaximum(Calendar.DATE) - (dayOfMonthBirth - dayOfMonthNow)) + 1)
                                + "天";
                    else
                        // 如果当前日期不小于生日日期 则为1月
                        resAge = "1个月";
                } else {
                    // 如果当前月份只比生日月份大不止1月 则计算月份
                    resAge = (age + 1) + "个月";
                }
            } else if (monthNow == monthBirth) {// 如果当前月份等于生日月份 则计算天数
                int ages = dayOfMonthNow - dayOfMonthBirth;
                resAge = (ages + 1) + "天";
            }
        }
        System.out.println("age:" + resAge);
        return resAge + "";
    }

    /**
     * 判断service是否运行
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

}
