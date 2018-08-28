package com.healon.up20user.Share;

import android.content.Context;

/**
 * Created by Lizhangfeng on 2016/7/13 0013.
 * Description:
 */
public class SharePrefreceHelper extends PrefrenceWrapper {


    private SharePrefreceHelper(Context context, final String spName) {
        super(context, spName);
    }

    public static SharePrefreceHelper getInstence(Context context, final String spName) {
        return new SharePrefreceHelper(context, spName);
    }

    public void setIsFirst(boolean isFirst) {
        setBoolean("isFirst", isFirst);
    }

    public boolean isFirst() {
        return getBoolean("isFirst", true);
    }

    //权限
    public void setIsFirstwelcome(boolean IsFirstwelcome) {
        setBoolean("IsFirstwelcome", IsFirstwelcome);
    }

    public boolean isIsFirstwelcome() {
        return getBoolean("IsFirstwelcome", true);
    }

    //记录宽
    public void setWidth(int Width) {
        putint("Width", Width);
    }

    public int getWidth() {
        return getint("Width");
    }

    //记录高
    public void setHeight(int Height) {
        putint("Height", Height);
    }

    public int getHeight() {
        return getint("Height");
    }

    //记录是否第一次连接
    public void setFirstConnect(boolean isFirst) {
        setBoolean("setFirstConnect", isFirst);
    }

    public boolean getFirstConnect() {
        return getBoolean("setFirstConnect",false);
    }
}
