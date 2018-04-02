package com.example.bluesanfang;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by TanJieXi on 2018/4/2.
 */

public class BlueUtils {
    private static boolean isInit = false;
    private static BleManager mBleManager;
    private static List<BleDevice> mBleDevices;
    public volatile static BlueUtils sBlueUtils;
    private String service_uuid;
    private String c_uuid;
    private ConnectBlueListener mConnectBlueListener;
    private String name = "";
    private boolean isRepet = false;
    private BlueUtils() {

    }

    public static BlueUtils getInstance(){
        if(sBlueUtils == null){
            synchronized (BlueUtils.class){
                if(sBlueUtils == null){
                    sBlueUtils = new BlueUtils();
                }
            }
        }

        if(!isInit){
            init();
        }
        return sBlueUtils;
    }

    private static void init(){
        mBleManager = BleManager.getInstance();
        if (mBleManager.isSupportBle()) {
            mBleManager.enableBluetooth();
        }
        mBleDevices = new ArrayList<>();
    }

    protected void stopBlue(){
        mBleManager.cancelScan();
        mBleManager.disconnectAllDevice();
        mBleManager.disableBluetooth();
        mBleManager.destroy();
        isInit = false;
    }

    protected void startScan(String name, String service_uuid, String c_uuid, ConnectBlueListener connectBlueListener){
        this.name = name;
        this.c_uuid = c_uuid;
        this.service_uuid = service_uuid;
        this.mConnectBlueListener = connectBlueListener;
        mBleManager.scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                if(!isRepet) {
                    mConnectBlueListener.onChangeText("扫描设备中，请稍后");
                }else{
                    mConnectBlueListener.onChangeText("请将设备靠近后进行重连");
                }
            }

            @Override
            public void onScanning(BleDevice result) {
                if(!StringUtil.isEmpty(result.getName())) {
                    Log.i("dsfdasgasdf",result.getName());
                    mBleDevices.add(result);
                }
            }


            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                mConnectBlueListener.onChangeText("扫描成功，连接中，请稍后");
                connectDevice();
            }
        });
    }

    private void connectDevice(){
        if(mBleDevices.size() <= 0){
            mConnectBlueListener.onChangeText("没有扫描到设备");
            Toast.makeText(App.getContext(),"没有扫描到设备",Toast.LENGTH_SHORT).show();
            return;
        }

        for(BleDevice s : mBleDevices){
            if(name.equals(s.getName())){
                // datas.add(result);
                connect(s);
            }
        }
    }

    private void connect(final BleDevice result) {
        mBleManager.connect(result, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                mConnectBlueListener.onChangeText("温度计连接中");
                isRepet = false;
            }

            @Override
            public void onConnectFail(BleException exception) {
                mConnectBlueListener.onChangeText("连接失败");
                isRepet = false;
            }


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                List<BluetoothGattService> services = gatt.getServices();
                for(BluetoothGattService s : services){
                    Log.i("dsfdsfgd",s.getUuid().toString() + "");
                }


                mConnectBlueListener.onChangeText("连接设备成功");
                isRepet = false;
                setNo(bleDevice);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                mConnectBlueListener.onChangeText("断开连接");
                Log.i("dsfdsfgd",isActiveDisConnected + "");
                if(!isActiveDisConnected){
                    Observable.timer(5000, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    isRepet = true;
                                    startScan(name,service_uuid,c_uuid,mConnectBlueListener);
                                }
                            });
                }
            }
        });
    }

    private void setNo(BleDevice bleDevice) {
        isRepet = false;
        mBleManager.notify(bleDevice,
                service_uuid,
                c_uuid,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Log.i("dsfdsafgdsf", "onNotifySuccess--->");

                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        Log.i("dsfdsafgdsf", "onNotifyFailure--->");
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        switch (name){
                            case "JK_FR":
                                DealDataUtils.getInstance().dealTemData(
                                        bytes2HexString(data)
                                        , new DealDataListener() {
                                            @Override
                                            public void onFetch(int code, String message) {
                                                mConnectBlueListener.onChangeText(message);
                                            }
                                        });
                                break;
                            case "iChoice":
                                    Log.i("dsfdasgasdf","--->message-" + bytes2HexString(data));
                                    break;
                        }
                    }
                });

    }
    // byte转十六进制字符串
    private String bytes2HexString(byte[] bytes) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

}
