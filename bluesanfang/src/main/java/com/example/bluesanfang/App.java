package com.example.bluesanfang;

import android.app.Application;
import android.content.Context;

import com.clj.fastble.BleManager;

/**
 * Created by TanJieXi on 2018/3/29.
 */

public class App extends Application {
    public static Context mContext;
    public static App mainInstance;

    static{

    }
    @Override
    public void onCreate() {
        super.onCreate();
        mainInstance = this;
        mContext = getApplicationContext();
        BleManager.getInstance().init(this);
    }

    /**
     * 获取全局上下文
     *
     */
    public static Context getContext() {

        return mContext;
    }
}
