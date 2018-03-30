package com.example.newblue.scan;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.deviceBox;
import com.example.newblue.utils.Utils;

import java.util.Date;

/**
 * 发现蓝牙设备，把扫描到的BluetoothDevice存入Conver.LeDevices中
 */
@SuppressLint("NewApi")
public class BluetoothScan {
    public Context myContext;
    private static int ScanSecond = 2000;
    private static int ScanSeStop = 2000;
    public static boolean IsStart = false;
    public static boolean IsAutoJump = true;
    public static boolean IsInit = false;
    private static BluetoothScan instance;
    // TODO 扫描设备1
    public static BluetoothAdapter mBluetoothAdapter;

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

    public void init(Context con) {
        Log.i("bleWrapper","---bletoothScan--init--->");
        if (!IsInit) {
            myContext = con;
            final BluetoothManager bluetoothManager = (BluetoothManager) myContext
                    .getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
            //-
            //mHandler.postDelayed(task, ScanSeStop + ScanSecond);
            IsInit = true;
        }
    }

    public static void Stop() {
        IsStart = false;
        scanLeDevice(IsStart);
        //+
 		mHandler.removeCallbacks(task);
        if (deviceBox.mDevices != null) {
            deviceBox.mDevices.clear();
        }

        //+
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    public void Start(Context con) {
        if (!IsStart) {
            init(con);
            Start();
        }
    }

    public static void Start() {
        IsStart = true;
        mHandler.postDelayed(task, ScanSeStop + ScanSecond);
    }

    // 扫描设备开启和关闭
    public static void scanLeDevice(final boolean enable) {
        Log.e("Blue", "扫描 IsStart" + String.valueOf(IsStart));
        if (mBluetoothAdapter != null) {
            if (enable) {
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
                if (!mBluetoothAdapter.isDiscovering()) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        }
                    }, ScanSecond);
                    //蓝牙扫描比较消耗，所以不能一直开启，所以必须设置一段时间后关闭蓝牙，所以这里选择在2s后关闭
                    //但是task这个handler则是4s一次开启这个，相当于2s扫描一次
                    mBluetoothAdapter.startLeScan(mLeScanCallback);  //扫描全部蓝牙设备
                }
            } else {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);  //停止蓝牙扫描
            }
        }
    }

    public static Runnable task = new Runnable() {
        public void run() {
            if (IsStart) {
                scanLeDevice(true);
            }
            // 需要执行的代码
            mHandler.postDelayed(this, ScanSeStop + ScanSecond);
        }
    };
    public static Handler mHandler = new Handler();
    public static Handler mHandlerRun = new Handler();
    /**
     *   一旦发现蓝牙设备，LeScanCallback就会被回调，直到StopLeScan被调用
     *   所以如果我们需要通过BlueToothDevice获取外围设备的地址需要手动过滤掉已经发现的外围设备
     *   发现设备返回，由scanLeDevice启动和停止.
     *   这里注意，stopLeScan传入的参数必须和startLeScan一样，否则无法停止
     */
    public static BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        /**
         * @param device  代表蓝牙设备的类，可以通过这个类建立蓝牙连接获取设备一系列的东西
         * @param rssi  蓝牙的信号强弱指标
         * @param scanRecord  蓝牙广播出来的广告数据
         */
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             final byte[] scanRecord) {
            //Log.i("bleWrapper","---扫描成功--onLeScan- 设备信息存入Cover.LeDevices-->");
            mHandlerRun.postDelayed(new Runnable() {
                @Override
                public void run() {
                    deviceBox newdevice = new deviceBox();
                    newdevice.device = device;
//					newdevice.setUuidHeadWords(Conver.convertHexToString(Conver
//							.bytes2HexString(scanRecord)));
                    newdevice.setUuidHeadWords(Utils
                            .bytes2HexString(scanRecord));

//                    Log.e("搜索蓝牙名称==", Conver.convertHexToString(Conver
//                            .bytes2HexString(scanRecord)
//                    ));
                    Log.e("搜索蓝牙名称==", device.getName() + "");
                    Log.e("搜索蓝牙地址==", device.getAddress() + "");
                    if (newdevice.Type.length() > 0) {

                        boolean havdevice = false;

                        for (int i = 0; i < App.LeDevices.size(); i++) {
                            if (newdevice.getUuidHeadWords().contains(
                                    App.LeDevices.get(i).getUuidHeadWords())) {
                                havdevice = true;
                                App.LeDevices.get(i).reFreshDateTime = new Date();
                                App.LeDevices.get(i).device = device;
                            }
//                            if (newdevice.mDevices.contains(device) == false) {
////                                havdevice = true;
//                                newdevice.reFreshDateTime = new Date();
//                                Conver.LeDevices.add(newdevice);
//                                newdevice.mDevices.add(device);
//                            }
                        }
                        if (!havdevice) {
                            newdevice.reFreshDateTime = new Date();
                            App.LeDevices.add(newdevice);
                        }

//						// 首次进入自动进入检测
//						Log.e("BluetoothScan","首次进入自动进入检测"+IsStart+IsAutoJump+myContext.getClass().getName());
//						if (IsStart
//								&& IsAutoJump
//								&& myContext.getClass().getName()
//										.contains("Index")
//								|| myContext.getClass().getName()
//										.contains("Equipment")) {
//							Log.e("BluetoothScan","for");
//							for (int i = 0; i < Conver.LeDevices.size(); i++) {
//								Log.e("BluetoothScan","infor"+i);
//								if (Conver.LeDevices.get(i).isUsed == 0
//										&& Conver.LeDevices.get(i).isOnline()) {
//									Log.e("BluetoothScan","if");
//									if (deviceBox.Begin_Jc(myContext, i)) {
//
//										scanLeDevice(false);
//									}
//								}
//							}
//						}

                    }
                }
            }, 1);
        }
    };

}
