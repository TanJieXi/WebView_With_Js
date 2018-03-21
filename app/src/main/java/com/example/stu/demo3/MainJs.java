package com.example.stu.demo3;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Stu on 2018/3/21.
 *  这个类继承自Object，里面的方法是用来给js调用的，
 *  注意：
 *  1. 方法名字必须加上注解 @JavascriptInterface
 *  2. 调用方法：在js里面使用webview的标识+ . + 方法 如：test.textHello("这是调用的方法是，js里面的");
 */

public class MainJs extends Object{

    @JavascriptInterface
    public void textHello(String message){
        Log.i("dsfdsgsdf",message);
    }
}
