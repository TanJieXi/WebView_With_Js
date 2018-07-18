package com.example.newblue.scan;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.deviceBox;
import com.example.newblue.utils.Utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 发现蓝牙设备，把扫描到的BluetoothDevice存入Conver.LeDevices中
 */
@SuppressLint("NewApi")
public class BluetoothScan {
    private int ScanSecond = 2000;
    private int FOUR_SCAN_SECOND = 4000;
    private boolean IsStart = false;
    private boolean isInit = false;
    private volatile static BluetoothScan instance;
    private boolean isScan = false;
    private BluetoothAdapter mBluetoothAdapter;
    private Disposable sMScanRx, sMScanCoRx, sMStopScan;

    public static BluetoothScan getInstance() {
        if (instance == null) {
            synchronized (BluetoothScan.class) {
                if (instance == null) {
                    instance = new BluetoothScan();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化，主要是获取到蓝牙的适配器，才能进行接下来的操作
     */
    public void init() {
        if (!isInit) {
            final BluetoothManager bluetoothManager = (BluetoothManager) App.getContext()
                    .getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager == null) {
                return;
            }
            mBluetoothAdapter = bluetoothManager.getAdapter();
            isInit = true;
        }
    }

    public void Stop() {
        if(!IsStart){  //判断一下是否初始化了蓝牙
            return;
        }
        IsStart = false;
        scanLeDevice(false);

        if (deviceBox.mDevices != null) {
            deviceBox.mDevices.clear();
        }

        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        if(sMScanRx != null) {
            sMScanRx.dispose();
        }
        if(sMScanCoRx != null) {
            sMScanCoRx.dispose();
        }
        if(sMStopScan != null){
            sMStopScan.dispose();
        }
    }


    public void Start() {
        IsStart = true;
        if(sMScanRx != null && !sMScanRx.isDisposed()){
            sMScanRx.dispose();
        }
        if(isScan){
            scanLeDevice(false);
        }
        sMScanRx = Observable.interval(FOUR_SCAN_SECOND, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (IsStart) {
                            scanLeDevice(true);
                        }
                    }
                });

    }

    // 扫描设备开启和关闭
    public void scanLeDevice(final boolean enable) {
        Log.e("Blue", "扫描 IsStart" + String.valueOf(IsStart));
        if (mBluetoothAdapter != null) {
            if (enable) {
                if (isScan) {
                    return;
                }
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
                if (!mBluetoothAdapter.isDiscovering()) {
                    isScan = true;
                    sMStopScan = Observable.timer(ScanSecond + ScanSecond, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    scanLeDevice(false);
                                }
                            });
                    //蓝牙扫描比较消耗，所以不能一直开启，所以必须设置一段时间后关闭蓝牙，所以这里选择在2s后关闭
                    //但是task这个handler则是4s一次开启这个，相当于2s扫描一次
                    mBluetoothAdapter.startLeScan(mLeScanCallback);  //扫描全部蓝牙设备
                }
            } else {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);  //停止蓝牙扫描
                if(sMStopScan != null) {
                    sMStopScan.dispose();
                }
                isScan = false;
            }
        }
    }


    /**
     * 一旦发现蓝牙设备，LeScanCallback就会被回调，直到StopLeScan被调用
     * 所以如果我们需要通过BlueToothDevice获取外围设备的地址需要手动过滤掉已经发现的外围设备
     * 发现设备返回，由scanLeDevice启动和停止.
     * 这里注意，stopLeScan传入的参数必须和startLeScan一样，否则无法停止
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        /**
         * @param device  代表蓝牙设备的类，可以通过这个类建立蓝牙连接获取设备一系列的东西
         * @param rssi  蓝牙的信号强弱指标,0代表没有设备
         * @param scanRecord  蓝牙广播出来的广告数据
         */
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             final byte[] scanRecord) {
            //Log.i("bleWrapper","---扫描成功--onLeScan- 设备信息存入Cover.LeDevices-->");

            sMScanCoRx = Observable.timer(1, TimeUnit.MILLISECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            deviceBox newDevice = new deviceBox();
                            newDevice.device = device;
                            newDevice.setUuidHeadWords(Utils
                                    .bytes2HexString(scanRecord));

                            Log.e("搜索蓝牙名称==", device.getName() + "");
                            Log.e("搜索蓝牙地址==", device.getAddress() + "");
                            if (newDevice.Type.length() > 0) {

                                boolean havdevice = false;

                                //判断App.LeDevices里面是否已存在该设备
                                for (int i = 0, len = App.LeDevices.size(); i < len; i++) {
                                    deviceBox d = App.LeDevices.get(i);
                                    if (newDevice.getUuidHeadWords().contains(
                                            d.getUuidHeadWords())) {
                                        havdevice = true;
                                        d.reFreshDateTime = new Date();
                                        d.device = device;
                                    }
                                }
                                if (!havdevice) {
                                    newDevice.reFreshDateTime = new Date();
                                    App.LeDevices.add(newDevice);
                                }


                            }
                        }
                    });
        }
    };

}
