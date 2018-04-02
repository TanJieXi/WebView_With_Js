package com.example.bluetoothdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * 这个是 BlueToothDevice执行connect或者connectGatt操作之后的一个结果回调
 */
@SuppressLint("NewApi")
public class BlueGetListener extends BluetoothGattCallback {

    private String bluetoothPass = "";
    private BluetoothGattService mBTServices;

    private BluetoothDevice mDevice;
    private Context mContext;
    private BluetoothGatt mBluetoothGatt;
    private int nownotfi = 0;
    private ArrayList<BluetoothGattCharacteristic> mCharacteristics;
    private BluetoothGattCharacteristic mCharacteristicWrite = null;
    private String mUuid;
    private Handler mHandler = new Handler();



    public void stop(){

        mBluetoothGatt.close();
    }


    public BlueGetListener(BluetoothDevice device, Context context) {
        mDevice = device;
        mContext = context;
    }

    public void connect() {
        mCharacteristics = new ArrayList<>();
        if(mBluetoothGatt != null){
            mBluetoothGatt.close();
        }
        mBluetoothGatt = mDevice.connectGatt(mContext, false, this);
    }


    /**
     * 这个方法运行的线程是一个Binder线程，所以不建议直接在这个线程执行耗时操作，可能造成IO堵塞
     * 在这个方法里面要执行discoverService方法，直到onServicesDiscovered被回调，才算连接成功
     * 注意：status如果为133，是因为超过Android能连接蓝牙设备个数的上限，所以断开的时候必须调用BlueToothGatt.close释放
     *
     * @param gatt     蓝牙设备的Gatt服务连接类
     * @param status   是否成功执行连接操作，BluetoothGatt.GATT_SUCCESS表示成功，第三个参数才有效
     * @param newState BlueToothProfile.STATE_CONNECTED连接成功，可以下一步，STATE_DISCONNECTED断开
     */
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        Log.i("dfdsafgsdf", "连接成功-sssssss--》");
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            //  发现该设备的RSSI，RSSI：信号强度
            Log.i("dfdsafgsdf", "连接成功---》");
            // 成功连接后调用discoverService方法，直到onServicesDiscovered被回调，才算真正连接上了

            mBluetoothGatt.readRemoteRssi();
            mBluetoothGatt.discoverServices();
            // 定期更新RSSI值
            startMonitoringRssiValue();

        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.i("dfdsafgsdf", "连接失败---》");
        }



    }

    /* starts monitoring RSSI value 开始监听RSSI */
    public void startMonitoringRssiValue() {
        readPeriodicalyRssiValue(true);
    }

    private boolean mTimerEnabled = false;
    private boolean mConnected = false;

    /* request new RSSi value for the connection*/
    public void readPeriodicalyRssiValue(final boolean repeat) {
        mTimerEnabled = repeat;
        // check if we should stop checking RSSI value
        if (mConnected == false || mBluetoothGatt == null || mTimerEnabled == false) {
            mTimerEnabled = false;
            return;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                // request RSSI value
                mBluetoothGatt.readRemoteRssi();
                // add call it once more in the future 延时循环调用
                readPeriodicalyRssiValue(mTimerEnabled);
            }
        }, 15000);
    }

    /**
     * 这个回调执行在mBluetoothGatt.discoverServices()之后，在onCoonecttionStateChange中调用
     * 当这个方法被调用后，才能说明手机和蓝牙设备真正意义上的连接上了
     * 到这一步，说明已经成功和蓝牙设备连接上了，可以进行蓝牙操作了，例如写入数据，读取数据等等
     * <p>
     * 需要执行：
     * 1.getService获取BlueToothGattService
     * 2.getCharactristic获取BluetoothGattCharactristic
     * 3.通过BluetoothGattCharactristic的readCharacteristic去读取数据
     *
     * @param gatt
     * @param status
     */
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);

        nownotfi = 0;
        mCharacteristics.clear();
        Log.i("dfdsafgsdf", "连接成功--onServicesDiscovered-》");
        Log.i("dfdsafgsdf", "连接成功--onServicesDiscovered-status00" + status) ;
        if (status == BluetoothGatt.GATT_SUCCESS) {
            List<BluetoothGattService> services = gatt.getServices();
            Log.i("dfdsafgsdf","===数量=>" + services.size() );
            for (BluetoothGattService s : services) {
                mUuid = s.getUuid().toString();
                Log.i("dfdsafgsdf", "连接成功-uuid--》" + mUuid);
                //setOxiUUid(s, mUuid);
                setTemUUid(s,mUuid);

                /*if (mBTServices == null || !mBTServices.equals(s)) {
                    mUuid = s.getUuid().toString();
                    Log.i("asdkljklfasd", "--->-uuid-->" + mUuid);
                    setOxiUUid(s, mUuid);
                }*/


            }


        }
    }



    /**
     * 如果通过readCharacteristic通知系统读取数据，如果系统读取到了蓝牙设备就会调用这个方法
     * 通过BluetoothGattCharacterstic的getValue可以读取到蓝牙设备的数据
     * 其中的getVlue就是蓝牙返回给我们需要自己处理的数据
     */
    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.i("dfdsafgsdf", "---onCharacteristicRead--->");
        super.onCharacteristicRead(gatt, characteristic, status);



    }
    /* request to fetch newest value stored on the remote device for particular characteristic */
    public void requestCharacteristicValue(BluetoothGattCharacteristic ch) {
        mBluetoothGatt.readCharacteristic(ch);
        // new value available will be notified in Callback Object
    }
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt,
                                        BluetoothGattCharacteristic characteristic) {
        // characteristic's value was updated due to enabled notification, lets get this value
        // the value itself will be reported to the UI inside getCharacteristicValue
        Log.i("dfdsafgsdf", "---onCharacteristicChanged--->");
        getCharacteristicValue(characteristic);
        // also, notify UI that notification are enabled for particular characteristic
    }


    public void getCharacteristicValue(BluetoothGattCharacteristic ch) {


        byte[] rawValue = ch.getValue();
        Log.i("bleWrapper", "---getCharacteristicValue--->" + Arrays.toString(rawValue));
        String strValue = null;
        int intValue = 0;

        // lets read and do real parsing of some characteristic to get meaningful value from it
        UUID uuid = ch.getUuid();

      /*  if (rawValue != null && rawValue.length > 0) {
            strValue = bytes2HexString(rawValue);
        }
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date());
        Log.i("bleWrapper", "--getCharacteristicValue-->" + strValue);
        //strvalue就是需要自己处理的数据
        mUiCallback.uiNewValueForCharacteristic(mBluetoothGatt,
                mBluetoothDevice,
                mBluetoothSelectedService,
                ch,
                strValue,
                intValue,
                rawValue,
                timestamp);*/
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

            setDevUUID("0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        }


    }
    /**
     * 设置体温计的UUid
     *
     * @param service
     * @param uuid
     */
    public void setTemUUid(BluetoothGattService service, String uuid) {
        // 在这里设置服务和特征
        if (service != null && uuid.contains("e7810a71-73ae-499d-8c15-faa9aef0c3f2")) {
            mBTServices = service;
            setDevUUID("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    16);
            setDevUUID("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    8);
            bluetoothPass = "11";
            SetNotfi();
            // 福达康体温枪
        } else if (service != null && uuid.contains("00005970-6d75-4753-5053-676e6f6c7553")) {
            mBTServices = service;
            setDevUUID("02005970-6d75-4753-5053-676e6f6c7553",
                    16);
            // setDevUUID("02005970-6d75-4753-5053-676e6f6c7553",
            // 8);
            SetNotfi();
            // 体达体温枪
        } else if (service != null && uuid.contains("45531234-6565-7370-6f54-676e6f6c7553")) {
            mBTServices = service;
            setDevUUID("45531236-6565-7370-6f54-676e6f6c7553",
                    16);
            // setDevUUID("02005970-6d75-4753-5053-676e6f6c7553",
            // 8);
            SetNotfi();
            // 如果是 连续体温
        } else if (service != null && uuid.contains("ba11f08c-5f14-0b0d-10d0-ff434d4d4544")) {
            mBTServices = service;
            setDevUUID("0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            setDevUUID("0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000fff3-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();
        }else if (service != null && uuid.contains("0000fee7-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            setDevUUID("0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID("0000fff3-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();
        }
        else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            setDevUUID("0000fff2-0000-1000-8000-00805f9b34fb",
                    16);
            // setDevUUID("0000fff2-0000-1000-8000-00805f9b34fb",
            // 8);
            bluetoothPass = "";
            SetNotfi();
        }

    }
    public void SetNotfi() {
        Log.i("dfdsafgsdf","---setNotfi-");
        if (mCharacteristics.size() > 0 && nownotfi <= mCharacteristics.size()) {
            Log.i("dfdsafgsdf","---setNotfi1-");
            nownotfi = nownotfi + 1;
            if (nownotfi - 1 < mCharacteristics.size()) {
                Log.i("dfdsafgsdf","---setNotfi2-");
                setNotificationForCharacteristic(
                        mCharacteristics.get(nownotfi - 1), true);
            } else if (nownotfi - 1 == mCharacteristics.size()) {
                Log.i("dfdsafgsdf","---setNotfi3-");
                // TODO 如果有验证码，在这里设置
                if (bluetoothPass.length() > 0) {
                    WriteHexString(mCharacteristicWrite,
                            bluetoothPass);
                }
            }
        }
    }

    public byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }


    public void WriteHexString(BluetoothGattCharacteristic mCharacteristicWrite, String string) {
        // TODO Auto-generated method stub
        if (mCharacteristicWrite != null) {
            byte[] dataToWrite = getHexBytes(string);
            writeDataToCharacteristic(mCharacteristicWrite,
                    dataToWrite);
        }
    }

    public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {

        // first set it locally....
        ch.setValue(dataToWrite);
        // ... and then "commit" changes to the peripheral
        mBluetoothGatt.writeCharacteristic(ch);
    }

    /**
     * 读取数据
     *
     * @param string
     * @param chartype
     */
    private void setDevUUID(String string, int chartype) {
        BluetoothGattCharacteristic temch = null;
        int props;
        if (chartype != 8) {
            temch = mBTServices.getCharacteristic(UUID
                    .fromString(string));
            props = temch.getProperties();
            if ((props & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                mCharacteristics.add(temch);
            }
        } else {
            temch = mBTServices.getCharacteristic(UUID
                    .fromString(string));
            props = temch.getProperties();
            if ((props & (BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) != 0) {
                mCharacteristicWrite = temch;

            }
        }
    }

    /**
     * 1.向蓝牙设备注册监听实现实时读取蓝牙设备的数据
     * 2.BLE app通常需要获取设备中characteristic变化的通知。
     * 3.除了通过setCharacteristicNotification开启Android端接受通知的开关
     * 还需要往Characteristic的Descriptor属性写入开启通知的数据开关硬件数据改变，主动往手机发数据
     *
     * @param ch
     * @param enabled
     */
    public void setNotificationForCharacteristic(BluetoothGattCharacteristic ch, boolean enabled) {
        Log.i("dsfdsgdasfg", ch.toString());

        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, enabled);

        if (!success) {

            Log.e("------", "Seting proper notification status for characteristic failed!");
        }

        //可以通过这个描述符来确定和设置配置客户端。默认值为客户机配置描述符是0 x00特征。
        //This is also sometimes required (e.g. for heart rate monitors) to enable notifications/indications
        // see: https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
        BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        if (descriptor != null) {
            byte[] val = enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
            descriptor.setValue(val);
            mBluetoothGatt.writeDescriptor(descriptor);
        }


    }

}
