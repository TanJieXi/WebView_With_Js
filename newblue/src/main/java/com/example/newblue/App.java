package com.example.newblue;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by TanJieXi on 2018/3/29.
 */

public class App extends Application {
    public static Context mContext;
    public static App mainInstance;
    public static ArrayList<deviceBox> LeDevices;

    static{
        LeDevices = new ArrayList<>();

    }
    @Override
    public void onCreate() {
        super.onCreate();
        mainInstance = this;
        mContext = getApplicationContext();
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {

        return mContext;
    }
}
