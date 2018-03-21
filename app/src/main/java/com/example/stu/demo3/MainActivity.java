package com.example.stu.demo3;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;


    //sss
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = (WebView) findViewById(R.id.my_web);
        initWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());


        //注意：通过 WebView的addJavascriptInterface（）进行对象映射，前一个类方法必须加上javascriptInterface注解，后面为标志
        webView.addJavascriptInterface(new MainJs(), "test");
        // 注意，android调用js代码，无结果用loadUrl,有结果用evaluteJavascript
        // 特别注意，android调用js代码必须在OnPageFinished里面，否则无效
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //这个地方来调用index.html里面的代码
                webView.evaluateJavascript("javascript:sum(10,20)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("dsfdsgsdf",value);
                    }
                });
            }
        });

        webView.loadUrl("file:///android_asset/index.html");

    }


}

