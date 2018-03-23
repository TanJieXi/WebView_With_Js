package com.example.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ThreeActivity extends AppCompatActivity {
    private ImageView iv_images;
    private int reId = R.mipmap.username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);


        iv_images = findViewById(R.id.iv_images);


        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                /*BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), reId,options);
                subscriber.onNext(bitmap);
                subscriber.onCompleted();*/
                HttpHelper.getInstance().downLoadData(
                        "http://p1.so.qhimgs1.com/t01afde88e8324463e1.png",
                        new HttpHelper.CallBackListener() {
                            @Override
                            public void onFetch(byte[] bytes) {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 2;
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            }
                        }
                );
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onCompleted() {
                Log.i("dsfdasgsdf","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("dsfdasgsdf","error");
            }

            @Override
            public void onNext(Bitmap bitmap) {
                Log.i("dsfdasgsdf","error2");
                iv_images.setImageBitmap(bitmap);
            }
        });




    }
}
