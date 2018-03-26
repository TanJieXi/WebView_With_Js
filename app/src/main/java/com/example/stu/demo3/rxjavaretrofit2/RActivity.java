package com.example.stu.demo3.rxjavaretrofit2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.stu.demo3.R;
import com.example.stu.demo3.StringConverterFactory;
import com.example.stu.demo3.anima.AnimActivity;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RActivity extends AppCompatActivity{

    private Api mApi;
    private Api mApi2;

    @BindView(R.id.btn_three)
    Button btn_three;
    @BindView(R.id.tv_text)
    TextView tv_text;



    @OnClick(R.id.tv_text) void openIntent(){
        startActivity(new Intent(this, AnimActivity.class));
    }

    @OnClick(R.id.btn_three) void clickThree(){
        YoYo.with(Techniques.Shake)
                .duration(1500)
                .repeat(5)
                .playOn(findViewById(R.id.tv_text));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);

        ButterKnife.bind(this);

        initRe();

        setupWindowAnimations();
    }


    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(6000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(6000);
        getWindow().setReturnTransition(slide);
    }

    private void initRe() {
        mApi = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://v.juhe.cn/")
                .build()
                .create(Api.class);

        mApi2 = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
                .create(Api.class);
    }

    public void btnclick(View view) {
        try {
            String names = URLDecoder.decode("成都","utf-8");
            mApi.getWeather(names,"json","1","dcc498b952b3f9abdd0c8dcd4e68b8d9")
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            //这里来判断一下网络返回的错误代码
                            Gson gson = new Gson();
                            WeatherBean weatherBean = gson.fromJson(s, WeatherBean.class);
                            if(weatherBean.getResultcode().equals(NetWorkException.REQUEST_OK + "")){
                                return s;
                            }
                            return null;
                        }
                    })
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
                    Log.i("sdasfasd","-->onError---》" + e.getMessage());
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

    public void btnClicktwo(View view) {
        try {
            String names = URLDecoder.decode("成都","utf-8");
            HashMap<String,String> params = new HashMap<>();
            params.put("cityname",names);
            params.put("dtype","json");
            params.put("format","1");
            params.put("key","dcc498b952b3f9abdd0c8dcd4e68b8d9");
            mApi.getWeather(params)
                    .filter(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String s) {
                            //这里来判断一下网络返回的错误代码
                            Gson gson = new Gson();
                            WeatherBean weatherBean = gson.fromJson(s, WeatherBean.class);
                            return (weatherBean.getResultcode().equals(NetWorkException.REQUEST_OK + ""));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            //相当于这里是获取到用户的token，然后根据token在执行第二步操作getOthers方法
                            //faltMap这个返回的是一个Observable对象，相当于可以向下面继续数据，适用于嵌套使用
                            Log.i("sdasfasd","----》这里的数据---》"  + s);
                            return mApi2.getOthers("Guolei1130");
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //在处理下一个事件之前要做的事。 在调用getOthers之前要处理的事情
                    .doOnNext(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Log.i("sdasfasd","----》先存储数据---》"  + s);
                        }
                    })
                    // 间隔600ms以内的事件将被丢弃,防止重复点击
                    .debounce(600, TimeUnit.MILLISECONDS)
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            Log.i("sdasfasd","-->onCompleted---》");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("sdasfasd","-->onError---》");
                        }

                        @Override
                        public void onNext(String s) {
                            Log.i("sdasfasd","-->成功---》" + s );
                        }
            })

            ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }




}
