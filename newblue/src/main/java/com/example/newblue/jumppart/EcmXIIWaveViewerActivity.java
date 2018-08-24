package com.example.newblue.jumppart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.contec.aecgdemo.AECGParse;
import com.contec.aecgdemo.ReviewWave;
import com.example.newblue.BlueConstants;
import com.example.newblue.R;
import com.example.newblue.view.CustomEditDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EcmXIIWaveViewerActivity extends AppCompatActivity {

    @BindView(R.id.reviewWave)
    ReviewWave reviewWave;
    private CustomEditDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity_ecm_xiiwave);
        ButterKnife.bind(this);
        initView();
        initIntentData();
    }

    private void initView() {
        dialog = new CustomEditDialog(this, "加载中...");
    }

    private void initIntentData() {
        Intent intent = getIntent();
        if (intent == null) return;
        String url = intent.getStringExtra(BlueConstants.ECMII_STRING_URL);
        if (TextUtils.isEmpty(url)) return;
        getEcgData(url);
    }


    private void getEcgData(final String ecgUrl) {
        dialog.show();
        Observer<short[]> observer = new Observer<short[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(short[] aDouble) {
                showEcgWave(aDouble);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                dialog.dismiss();
            }
        };
        Observable<short[]> observable = Observable.create(new ObservableOnSubscribe<short[]>() {
            @Override
            public void subscribe(ObservableEmitter<short[]> e) throws Exception {
                short[] data = AECGParse.parseAECGRecord(new File(ecgUrl));
                e.onNext(data);
                e.onComplete();
            }
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void showEcgWave(short[] data) {

        //为波形回顾View控件设置数据
        reviewWave.setWaveDatas(data);
        //设置波形样式12x1 6x2等
        reviewWave.setLeadDisplayStyle(ReviewWave.LEAD_DISPLAY_12X1);
        //设置走速
        reviewWave.setSpeed(25);
        //设置增益
        float[] gain = {10, 10};
        reviewWave.setGain(gain);
        //设置字体大小
        reviewWave.setLabelTextSize(30);
        //开始绘图
        reviewWave.startThread();
    }
}

