package com.healon.up20user.Share;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lizhangfeng on 2016/7/13 0013.
 * Description:
 */
public class PrefrenceWrapper {

    private SharedPreferences sharedPreferences;

    protected PrefrenceWrapper(Context context, final String spName) {
        sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);//Context.MODE_MULTI_PROCESS支持跨进程访问
    }
    /**
     * 存入字符串
     * @param key
     * @param value
     */
    protected void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    protected String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * 存入int型
     * @param key
     * @param value
     */
    protected void putint(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }
    protected int getint(String key) {
        return sharedPreferences.getInt(key,0);
    }
    /**
     * 存入boolean
     * @param key
     * @param vaule
     */
    protected void setBoolean(String key, boolean vaule){
        sharedPreferences.edit().putBoolean(key,vaule).commit();
    }

    protected boolean getBoolean(String key, boolean defaultVaule){
        return sharedPreferences.getBoolean(key,defaultVaule);
    }

    /**
     * 清空sharePrefrence文件
     */
    public void clear() {
        sharedPreferences.edit().clear();
    }

}
