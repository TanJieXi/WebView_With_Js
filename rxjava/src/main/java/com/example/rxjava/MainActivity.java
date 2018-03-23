package com.example.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private Observable<String> mObservable;
    private Observer<String> mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //观察者
        mObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("asdasdfasd", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("asdasdfasd", "error");
            }

            @Override
            public void onNext(String s) {
                Log.i("asdasdfasd", "Item: " + s);
            }
        };



    }

    public void btnClick(View view) {
        //被观察者
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        
        mObservable.subscribe(mObserver);
    }
}
