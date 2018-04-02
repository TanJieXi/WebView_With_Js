package com.example.newblue.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.blueconnect.BleWrapper;
import com.example.newblue.deviceBox;
import com.example.newblue.interfaces.BleWrapperUiCallbacks;
import com.example.newblue.interfaces.ConnectBlueToothListener;
import com.example.newblue.scan.BluetoothScan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TanJieXi on 2018/3/28.
 */
@SuppressLint("NewApi")
public class CommenBlueUtils implements BleWrapperUiCallbacks {

    private volatile static CommenBlueUtils blue;
    private BleWrapper mBleWrapper;
    private String mDeviceAddress = "";
    private String mDeviceName = "";
    private ConnectBlueToothListener mConnectBlueToothListener;
    private ArrayList<BluetoothGattCharacteristic> mCharacteristics;
    private String mLastUpdateTime = "";
    private boolean mNotificationEnabled = false;
    private String mDeviceRSSI = "";
    // 程序步进值
    private int nownotfi = 0;
    private String bluetoothPass = "";
    private BluetoothGattCharacteristic mCharacteristicWrite = null;
    private BluetoothGattService mBTServices;
    private String type = "";  //这个标记用于区分是哪个仪器
    private Disposable mSubscribe;

    private CommenBlueUtils() {

    }

    public static CommenBlueUtils getInstance() {
        if (blue == null) {
            synchronized (CommenBlueUtils.class) {
                if (blue == null) {
                    blue = new CommenBlueUtils();
                }
            }
        }
        return blue;
    }


    public void disConnectBlueTooth() {
        BluetoothScan.getInstance().Stop();
        if(mSubscribe != null) {
            mSubscribe.dispose();
        }
        if(mBleWrapper != null) {
            mBleWrapper.stopMonitoringRssiValue();
            mBleWrapper.diconnect();
            mBleWrapper.close();
        }
        if(mConnectBlueToothListener != null) {
            mConnectBlueToothListener.onInterceptConnect("连接断开");
        }

    }


    public void connectBlueTooth(Activity context, final String type, ConnectBlueToothListener connectBlueToothListener) {
        this.type = type;
        this.mConnectBlueToothListener = connectBlueToothListener;
        mConnectBlueToothListener.onConnectSuccess("连接中，请稍后");
        mCharacteristics = new ArrayList<>();
        //BluetoothScan.IsAutoJump = false;
        BluetoothScan.getInstance().Start();
        Log.i("bleWrapper","---CommenBlueUtisl--BluetoothScanStart->");
        if (mBleWrapper == null) {
            mBleWrapper = new BleWrapper(context, this);
        }
        Log.i("bleWrapper","---CommenBlueUtisl--BleWrapper->");
        if (!mBleWrapper.initialize()) {
            BluetoothScan.getInstance().Stop();
        }


        //每0.5s去连接一下
        mSubscribe = Observable.interval(500, TimeUnit.MILLISECONDS).
                observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        NewBlue(type);
                    }
                });
    }

    /**
     * type仪器名
     *
     * @param name
     */
    private void NewBlue(String name) {
        if (StringUtil.isEmpty(name)) {
            return;
        }
        if (mDeviceAddress.length() < 2) {
            //Log.i("bleWrapper", "--NewBlue-循环中，未扫描到设备" + Conver.LeDevices.size());
            for (int i = 0; i < App.LeDevices.size(); i++) {
                deviceBox deviceBox = App.LeDevices.get(i);
                //Log.i("bleWrapper", "--NewBlue-循环中，扫描到设备");
                if (deviceBox.Type.equals(name.trim()) && deviceBox.isOnline() && deviceBox.device != null) {
                    //String bule_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_TEM_ADDRESS);
                    String bule_address = "";

                    mDeviceAddress = StringUtil.isEmpty(bule_address) ?
                            App.LeDevices.get(i).device.getAddress() : bule_address;

                    mDeviceName = App.LeDevices.get(i).device.getName();
                    Log.i("mDeviceName","---mDeviceName->"+mDeviceName);
                    mBleWrapper.connect(mDeviceAddress);
                    break;
                }
            }
        }

    }


    @Override
    public void uiDeviceFound(BluetoothDevice device, int rssi, byte[] record) {

    }

    /**
     * 连接到设备成功，在BleWrapper里面的mBleCallback的onConnectionStateChange连接成功后调用
     */
    @Override
    public void uiDeviceConnected(BluetoothGatt gatt, BluetoothDevice device) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("");
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mConnectBlueToothListener.onConnectSuccess("设备在线");
                        Log.i("sjkljklsjadkll", "连接到设备成功--user_heathe");
                        BluetoothScan.getInstance().Stop();
                    }
                });
    }

    /**
     * 设备断开
     *
     * @param gatt
     * @param device
     */
    @Override
    public void uiDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("");
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mConnectBlueToothListener.onInterceptConnect("连接断开");
                        Log.e("test", "设备断开");
                        BluetoothScan.getInstance().Start();
                        mDeviceAddress = "";
                    }
                });

    }

    /**
     * 设置oxi的uuid
     *
     * @param service
     * @param uuid
     */
    public void setOxiUUid(BluetoothGattService service, String uuid) {
        if (service != null && uuid.contains("ba11f08c-5f14-0b0d-1080")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        }

    }

    /**
     * 设置体温计的UUid
     *
     * @param service 服务，循环的
     * @param uuid   该服务的uuid，要找到可以进行通讯的UUID
     */
    public void setTemUUid(BluetoothGattService service, String uuid) {
        Log.i("piepoidjs","-setTemUUid-->-uuid-->" + uuid);
        // 在这里设置服务和特征
        if (service != null && uuid.contains("e7810a71-73ae-499d-8c15-faa9aef0c3f2")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    16);
            setDevUUID(service,"bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    8);
            bluetoothPass = "11";
            SetNotfi();
            // 福达康体温枪
        } else if (service != null && uuid.contains("00005970-6d75-4753-5053-676e6f6c7553")) {
            mBTServices = service;
            Log.i("piepoidjs","--->-uuid-tem-uiAvailableServices->");
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"02005970-6d75-4753-5053-676e6f6c7553",
                    16);
            // setDevUUID("02005970-6d75-4753-5053-676e6f6c7553",
            // 8);
            SetNotfi();
            // 体达体温枪
        } else if (service != null && uuid.contains("45531234-6565-7370-6f54-676e6f6c7553")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"45531236-6565-7370-6f54-676e6f6c7553",
                    16);
            // setDevUUID("02005970-6d75-4753-5053-676e6f6c7553",
            // 8);
            SetNotfi();
            // 如果是 连续体温
        } else if (service != null && uuid.contains("ba11f08c-5f14-0b0d-10d0-ff434d4d4544")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb") && mDeviceName.contains("Bluetooth BP")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service,"0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service,"0000fff3-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();
            //家康体温计
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            setDevUUID(service,"0000fff2-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();

        }

    }

    /**
     * 真正意义上的连接到设备成功，在BleWrapper里面的mBleCallback的onServicesDiscovered连接成功后调用
     * onConnectionStateChange--->执行discoverService---->onServicesDiscovered---->到此方法
     * @param gatt
     * @param device
     * @param services 可以通过这个方法的getCharatristic，然后通过readCharacteristic去读取特定数据
     */
    @Override
    public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device, List<BluetoothGattService> services) {
        nownotfi = 0;
        mCharacteristics.clear();
        for (BluetoothGattService service : services) {
            if (mBTServices == null || !mBTServices.equals(service)) {
                String uuid = service.getUuid().toString();
                Log.i("nihao","--->-uuid-->" + uuid);
                //检查一下可以进行
                //如果我们无法得知这两个所需的UUID时，
                // 我们也可以通过上下一步的方法来获取（打印所有特征UUID，取出自己想要的特征）
                checkUUID(service);
                switch (type.trim()) {
                    case "oxi":
                        setOxiUUid(service, uuid);
                        break;
                    case "tem":
                        Log.i("piepoidjs","--->-uuid-tem-uiAvailableServices->");
                        setTemUUid(service, uuid);
                        Observable.timer(1,TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        mConnectBlueToothListener.onConnectSuccess("体温测量中，请稍后。。。");
                                    }
                                });

                        break;
                }
            }
        }
    }

    /**
     *  检查一下可以进行通讯的UUID,如果我们无法得知这两个所需的UUID时，
     *  我们也可以通过上下一步的方法来获取（打印所有特征UUID，取出自己想要的特征）
     * @param service
     */
    private void checkUUID(BluetoothGattService service) {
        List<BluetoothGattCharacteristic> gattCharacteristics =service.getCharacteristics();
        for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
            int charaProp = gattCharacteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                 Log.i("nihao","gattCharacteristic的UUID为:"+gattCharacteristic.getUuid());
                 Log.i("nihao","gattCharacteristic的属性为:  可读");
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                 Log.i("nihao","gattCharacteristic的UUID为:"+gattCharacteristic.getUuid());
                 Log.i("nihao","gattCharacteristic的属性为:  可写");
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                 Log.i("nihao","gattCharacteristic的UUID为:"+gattCharacteristic.getUuid()+gattCharacteristic);
                 Log.i("nihao","gattCharacteristic的属性为:  具备通知属性");
            }

        }
    }

    /**
     * 读取数据
     * @param string
     * @param chartype
     *
     * Characteristic是手机与BLE终端交换数据的关键，
     * Characteristic有较多的跟权限相关的字段，例如PERMISSION和PROPERTY，而其中最常用的是PROPERTY，
     * 本文所用的BLE蓝牙模块竟然没有标准的Characteristic的PERMISSION。Characteristic的PROPERTY可以通过位运算符组合来设置读写属性，
     * 例如READ|WRITE、READ|WRITE_NO_RESPONSE|NOTIFY，因此读取PROPERTY后要分解成所用的组合
     *
     *
     */
    private void setDevUUID(BluetoothGattService service,String string, int chartype) {
        BluetoothGattCharacteristic temch = null;
        int props;
        if (chartype != 8) {
            //获取此服务结点下的某个Characteristic对象
            temch = service.getCharacteristic(UUID
                    .fromString(string));
            props = temch.getProperties();
            if ((props & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {//设备具有可通知的功能，也可以判断可读可写 PROPERTY_READ和PROPERTY_WRITE
                mCharacteristics.add(temch);
            }
        } else {
            temch = mBTServices.getCharacteristic(UUID
                    .fromString(string));
            props = temch.getProperties();
            if ((props & (BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) > 0) {

                mCharacteristicWrite = temch;

            }
        }
    }

    /**
     * 扫描Service完成后得到Service所有的Characteristic结果
     */
    @Override
    public void uiCharacteristicForService(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, final List<BluetoothGattCharacteristic> chars) {

    }

    @Override
    public void uiCharacteristicsDetails(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {
        mLastUpdateTime = "-";
        mNotificationEnabled = false;
    }

    /**
     * 在BleWrapper里面onCharacteristicRead，在手机连接设备成功以后，接到设备发过来的数据就会调用这个
     * 这个就是需要自己处理的数据
     *
     */
    @Override
    public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, final BluetoothGattCharacteristic ch, final String strValue, final int intValue, final byte[] rawValue, final String timestamp) {
        if (mCharacteristics.size() <= 0) {
            return;
        }

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("");
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        newValueForCharacteristic(ch, strValue, intValue,
                                rawValue, timestamp);
                    }
                });
    }


    public void newValueForCharacteristic(final BluetoothGattCharacteristic ch,
                                          final String strVal, final int intVal, final byte[] rawValue,
                                          final String timestamp) {
        if (!mCharacteristics.contains(ch)) {
            return;
        }

        Log.i("bleWrapper", "-CommentBlue--》-newValueForCharacteristic-->" + strVal);
        mConnectBlueToothListener.onDataFromBlue(type, strVal);

        mLastUpdateTime = timestamp;
        if (mLastUpdateTime == null)
            mLastUpdateTime = "";
    }


    @Override
    public void uiGotNotification(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, final BluetoothGattCharacteristic characteristic) {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("");
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        setNotificationEnabledForService(characteristic);
                    }
                });

    }

    public void setNotificationEnabledForService(
            final BluetoothGattCharacteristic ch) {
        if (!mCharacteristics.contains(ch) || mNotificationEnabled)
            return;
        mNotificationEnabled = true;
    }

    @Override
    public void uiSuccessfulWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {

    }

    @Override
    public void uiFailedWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {

    }

    @Override
    public void uiNewRssiAvailable(BluetoothGatt gatt, BluetoothDevice device, final int rssi) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("");
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mDeviceRSSI = rssi + " db";
                    }
                });
    }

    @Override
    public void uiNotificationForCharacteristicSuccess(UUID uuid, int status) {
        SetNotfi();
    }


    /**
     *  接收到返回数据的前提是我们设置了该特征具有Notification功能，
     *  setNotificationForCharacteritic设置当指定characteristic值变化时，发出通知
     */
    public void SetNotfi() {
        //mCharacteristics里面保存的就是可通信设备的Characteristic对象，已经配对好了的对应的UUID的service
        if (mCharacteristics.size() > 0 && nownotfi <= mCharacteristics.size()) {
            nownotfi = nownotfi + 1;
            if (nownotfi - 1 < mCharacteristics.size()) {
                mBleWrapper.setNotificationForCharacteristic(
                        mCharacteristics.get(nownotfi - 1), true);
            } else if (nownotfi - 1 == mCharacteristics.size()) {
                // TODO 如果有验证码，在这里设置
                if (bluetoothPass.length() > 0) {
                    mBleWrapper.WriteHexString(mCharacteristicWrite,
                            bluetoothPass);
                }
            }
        }
    }
}
