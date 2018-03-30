package com.example.bluetoothdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by TanJieXi on 2018/3/29.
 */


@SuppressLint("NewApi")
public class BlueCallBackListener implements BluetoothAdapter.LeScanCallback {
    private onSaoSuccess mOnSaoSuccess;
    public BlueCallBackListener(onSaoSuccess onSaoSuccess) {
        this.mOnSaoSuccess = onSaoSuccess;
    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {
        Log.i("dfdsafgsdf","---扫描成功--onLeScan- 设备信息存入Cover.LeDevices-->");

        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mOnSaoSuccess.onCallBack(device);
                    }
                });

    }


    public interface onSaoSuccess{
        void onCallBack(BluetoothDevice d);
    }


}
