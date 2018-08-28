package com.healon.up20user.Base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Lizhangfeng on 2016/7/13 0013.
 * Description: Activity基本
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setFullScreen();
        //setLanguageformat();//设置语言
        setContentView(getContentViewId());
        // 如果存在actionBar，就隐藏
        if (getActionBar() != null)
            getActionBar().hide();
        beforeInitView();
        initView();
        initData();
    }

    public boolean isTabletDevice() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public abstract int getContentViewId();//放layoutId

    public abstract void beforeInitView();//初始化View之前做的事

    public abstract void initView();//初始化View

    public abstract void initData();//初始化数据


    /**
     * 不用强制转换的findviewbyid
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewByIdNoCast(int id) {
        return (T) findViewById(id);
    }


    /**
     * 设置全屏
     */
    public void setFullScreen() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //全局异常捕获以及设置一运行就是横屏
    @Override
    protected void onResume() {
        //判断是否为平板，平板显示为横屏，手机显示为竖屏
        if (isTabletDevice()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }


}
