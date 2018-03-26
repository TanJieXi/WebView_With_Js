package com.example.stu.demo3.rxjavaretrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.stu.demo3.R;
import com.example.stu.demo3.StringConverterFactory;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RActivity extends AppCompatActivity {

    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);

        initRe();
    }

    private void initRe() {
        mApi = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://v.juhe.cn")
                .build()
                .create(Api.class);
    }

    public void btnclick(View view) {
        try {
            String names = URLDecoder.decode("成都","utf-8");
            mApi.getWeather(names,"json","1","dcc498b952b3f9abdd0c8dcd4e68b8d9")
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, WeatherBean>() {
                        @Override
                        public WeatherBean call(String s) {
                            Gson gson = new Gson();
                            return gson.fromJson(s,WeatherBean.class);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WeatherBean>() {
                @Override
                public void onCompleted() {
                    Log.i("sdasfasd","-->onCompleted---》");
                }

                @Override
                public void onError(Throwable e) {
                    Log.i("sdasfasd","-->onError---》");
                }

                @Override
                public void onNext(WeatherBean s) {
                    Log.i("sdasfasd","-->成功---》" + s.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
