package com.example.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TwoActivity extends AppCompatActivity {
    String[] name = {"啊","不能","你","我"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);





    }

    public void btnC(View view) {
        Observable.just(1,2,3,4).observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io())
                .subscribe(new Action1<Integer >() {
                    @Override
                    public void call(Integer  s) {
                        Log.i("asdasdfasd", s + "");
                    }
                });
    }
}
