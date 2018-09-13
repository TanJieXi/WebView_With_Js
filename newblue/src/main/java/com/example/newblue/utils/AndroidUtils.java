package com.example.newblue.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.view.View;

/**
 * Created by TanJieXi on 2018/8/30.
 */
public class AndroidUtils {
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Activity context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 设置控件是否可点击
     * @param clickAble
     * @param views
     */
    public static void setViewClicable(boolean clickAble,View... views){
        for (int i = 0,len = views.length; i < len; i++) {
            views[i].setClickable(clickAble);
        }
    }

    /**
     * 判断是否在主线程
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
