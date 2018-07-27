package com.example.newblue.blueconnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.interfaces.BleWrapperUiCallbacks;
import com.example.newblue.scan.BluetoothScan;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 主要负责连接断开蓝牙设备，结果回调等一系列在扫描到蓝牙设备之后的操作，
 * BlueToothScan负责扫描设备
 * BleWrapper负责连接蓝牙处理数据
 */
@SuppressLint("NewApi")
public class BleWrapper {
    /*  RSSI 刷新时间 */
    private static final int RSSI_UPDATE_TIME_INTERVAL = 15000; // 1.5 seconds

    /* 回调对象,通过它我们把结果返回给调用者 */
    private BleWrapperUiCallbacks mUiCallback = null;
    /* 为UI定义空对象回调 */
    private static final BleWrapperUiCallbacks NULL_CALLBACK = new BleWrapperUiCallbacks.Null();

    /* 创建BleWrapper对象,设置它的父程序和回调对象 */
    public BleWrapper(Activity parent, BleWrapperUiCallbacks callback) {
//    	this.mParent = parent;
        this.mParent = App.getContext();
        mUiCallback = callback;
        if (mUiCallback == null) mUiCallback = NULL_CALLBACK;
    }

    public BluetoothManager getManager() {
        return mBluetoothManager;
    }

    public BluetoothAdapter getAdapter() {
        return mBluetoothAdapter;
    }

    public BluetoothDevice getDevice() {
        return mBluetoothDevice;
    }

    public BluetoothGatt getGatt() {
        return mBluetoothGatt;
    }

    public BluetoothGattService getCachedService() {
        return mBluetoothSelectedService;
    }

    public List<BluetoothGattService> getCachedServices() {
        return mBluetoothGattServices;
    }

    public boolean isConnected() {
        return mConnected;
    }

    /* run test and check if this device has BT and BLE hardware available */
    public boolean checkBleHardwareAvailable() {
        // First check general Bluetooth Hardware:
        // get BluetoothManager...
        final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager == null) return false;
        // .. and then get adapter from manager
        final BluetoothAdapter adapter = manager.getAdapter();
        if (adapter == null) return false;

        // and then check if BT LE is also available
        boolean hasBle = mParent.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        return hasBle;
    }


    /* before any action check if BT is turned ON and enabled for us
     * call this in onResume to be always sure that BT is ON when Your
     * application is put into the foreground */
    public boolean isBtEnabled() {
        final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager == null) return false;

        final BluetoothAdapter adapter = manager.getAdapter();
        if (adapter == null) return false;

        return adapter.isEnabled();
    }

    /* start scanning for BT LE devices around */
    public void startScanning() {
        mBluetoothAdapter.startLeScan(mDeviceFoundCallback);
    }

    /* stops current scanning */
    public void stopScanning() {
        mBluetoothAdapter.stopLeScan(mDeviceFoundCallback);
    }

    /* initialize BLE and get BT Manager & Adapter */
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                return false;
            }
        }

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }
        return mBluetoothAdapter != null;
    }

    /**
     * 涉及到几个方法：
     * connectGatt(context,是否需要自动连接,结果回调)
     * connect(context,true,结果回调)   ture:会不断的执行断线重连  false:连接一次
     *
     * @param deviceAddress 连接的蓝牙设备地址
     * @return
     */
    public boolean connect(final String deviceAddress) {
        Log.i("bleWrapper", "---connect--->");
        if (mBluetoothAdapter == null || deviceAddress == null) {
            return false;
        }
        mDeviceAddress = deviceAddress;


        //停止扫描，连接前，必须停止扫描，不然，失败率很高
        BluetoothScan.getInstance().scanLeDevice(false);

        // 检查我们是否需要从头开始连接或只是重新连接到以前的设备
        if (mBluetoothGatt != null && mBluetoothGatt.getDevice().getAddress().equals(deviceAddress)) {
            // 这个方法相当于connectGatt的第二个参数为true，一直连接
            return mBluetoothGatt.connect();
        } else {
            // 从头开始连接
            // 获取指定地址的BluetoothDevice对象
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
            if (mBluetoothDevice == null) {
                // 我们收到了错误的地址 - 该设备不可用！
                return false;
            }
            //BlueToothDevice的ConnectGatt是连接蓝牙设备
            //第二个参数标识是否需要自动连接，true如果设备断开会不断连接，false只会连接一次，第三个参数数据回调
            mBluetoothGatt = mBluetoothDevice.connectGatt(mParent, false, mBleCallback);
        }
        return true;
    }

    /* disconnect the device. It is still possible to reconnect to it later with this Gatt client */
    public void diconnect() {
        if (mBluetoothGatt != null) mBluetoothGatt.disconnect();
        mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
    }

    /* close GATT client completely */
    public void close() {
        if (mBluetoothGatt != null) mBluetoothGatt.close();
        //refreshDeviceCache();
        mBluetoothGatt = null;
    }
    /**
     * 清理蓝牙缓存
     */
    public boolean refreshDeviceCache() {
        if (mBluetoothGatt != null) {
            try {
                Method localMethod = mBluetoothGatt.getClass().getMethod(
                        "refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(
                            mBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
                Log.e("BleWrapper", "An exception occured while refreshing device");
            }
        }
        return false;
    }
    /* request new RSSi value for the connection*/
    public void readPeriodicalyRssiValue(final boolean repeat) {
        mTimerEnabled = repeat;
        // check if we should stop checking RSSI value
        if (mConnected == false || mBluetoothGatt == null || mTimerEnabled == false) {
            mTimerEnabled = false;
            return;
        }

        mTimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBluetoothGatt == null ||
                        mBluetoothAdapter == null ||
                        mConnected == false) {
                    mTimerEnabled = false;
                    return;
                }

                // request RSSI value
                mBluetoothGatt.readRemoteRssi();
                // add call it once more in the future 延时循环调用
                readPeriodicalyRssiValue(mTimerEnabled);
            }
        }, RSSI_UPDATE_TIME_INTERVAL);
    }

    /* starts monitoring RSSI value 开始监听RSSI */
    public void startMonitoringRssiValue() {
        readPeriodicalyRssiValue(true);
    }

    /* stops monitoring of RSSI value */
    public void stopMonitoringRssiValue() {
        readPeriodicalyRssiValue(false);
    }

    /* 扫描服务
     * request to discover all services available on the remote devices
     * results are delivered through callback object */
    public void startServicesDiscovery() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.discoverServices();
        }
    }

    /* gets services and calls UI callback to handle them
     * before calling getServices() make sure service discovery is finished! */
    public void getSupportedServices() {
        if (mBluetoothGattServices != null && mBluetoothGattServices.size() > 0)
            mBluetoothGattServices.clear();
        // keep reference to all services in local array:
        if (mBluetoothGatt != null) mBluetoothGattServices = mBluetoothGatt.getServices();
        Log.i("bleWrapper", "--服务数量--" + mBluetoothGatt.getServices().size());
        mUiCallback.uiAvailableServices(mBluetoothGatt, mBluetoothDevice, mBluetoothGattServices);
    }

    /* get all characteristic for particular service and pass them to the UI callback */
    public void getCharacteristicsForService(final BluetoothGattService service) {
        if (service == null) return;
        List<BluetoothGattCharacteristic> chars = null;

        chars = service.getCharacteristics();
        mUiCallback.uiCharacteristicForService(mBluetoothGatt, mBluetoothDevice, service, chars);
        // keep reference to the last selected service
        mBluetoothSelectedService = service;
    }

    /* request to fetch newest value stored on the remote device for particular characteristic */
    public void requestCharacteristicValue(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

        mBluetoothGatt.readCharacteristic(ch);
        // new value available will be notified in Callback Object
    }

    /* get characteristic's value (and parse it for some types of characteristics) 
     * before calling this You should always update the value by calling requestCharacteristicValue() */

    /**
     * 在调用这个之前你应该总是通过调用requestCharacteristicValue（）
     */
    public void getCharacteristicValue(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        byte[] rawValue = ch.getValue();
        Log.i("bleWrapper", "---getCharacteristicValue--->" + Arrays.toString(rawValue));
        String strValue = null;
        int intValue = 0;

        // lets read and do real parsing of some characteristic to get meaningful value from it 

        if (rawValue != null && rawValue.length > 0) {
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
                timestamp);
    }

    // byte转十六进制字符串
    public String bytes2HexString(byte[] bytes) {
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


    /* reads and return what what FORMAT is indicated by characteristic's properties
     * seems that value makes no sense in most cases */
    public int getValueFormat(BluetoothGattCharacteristic ch) {
        int properties = ch.getProperties();

        if ((BluetoothGattCharacteristic.FORMAT_FLOAT & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_FLOAT;
        if ((BluetoothGattCharacteristic.FORMAT_SFLOAT & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_SFLOAT;
        if ((BluetoothGattCharacteristic.FORMAT_SINT16 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_SINT16;
        if ((BluetoothGattCharacteristic.FORMAT_SINT32 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_SINT32;
        if ((BluetoothGattCharacteristic.FORMAT_SINT8 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_SINT8;
        if ((BluetoothGattCharacteristic.FORMAT_UINT16 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_UINT16;
        if ((BluetoothGattCharacteristic.FORMAT_UINT32 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_UINT32;
        if ((BluetoothGattCharacteristic.FORMAT_UINT8 & properties) != 0)
            return BluetoothGattCharacteristic.FORMAT_UINT8;

        return 0;
    }

    /* set new value for particular characteristic */
    public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        // first set it locally....
        ch.setValue(dataToWrite);
        // ... and then "commit" changes to the peripheral
        mBluetoothGatt.writeCharacteristic(ch);
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
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

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

    public void readCharacteristic(BluetoothGattCharacteristic ch, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, enabled);

        if (!success) {

            Log.e("------", "Seting proper notification status for characteristic failed!");
        }


    }


    /* defines callback for scanning results */
    private BluetoothAdapter.LeScanCallback mDeviceFoundCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            mUiCallback.uiDeviceFound(device, rssi, scanRecord);
        }
    };

    /**
     * 这个是 BlueToothDevice执行connect或者connectGatt操作之后的一个结果回调
     */
    private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {
        /**
         * 这个方法运行的线程是一个Binder线程，所以不建议直接在这个线程执行耗时操作，可能造成IO堵塞
         * 在这个方法里面要执行discoverService方法，直到onServicesDiscovered被回调，才算连接成功
         * 注意：status如果为133，是因为超过Android能连接蓝牙设备个数的上限，所以断开的时候必须调用BlueToothGatt.close释放
         * @param gatt  蓝牙设备的Gatt服务连接类
         * @param status 是否成功执行连接操作，BluetoothGatt.GATT_SUCCESS表示成功，第三个参数才有效
         * @param newState BlueToothProfile.STATE_CONNECTED连接成功，可以下一步，STATE_DISCONNECTED断开
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("bleWrapper", "---onConnectionStateChange--->"+ newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnected = true;

                //  发现该设备的RSSI，RSSI：信号强度
                mBluetoothGatt.readRemoteRssi();

                // 成功连接后调用discoverService方法，直到onServicesDiscovered被回调，才算真正连接上了
                try {
                    Thread.sleep(200);
                    startServicesDiscovery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mUiCallback.uiDeviceConnected(mBluetoothGatt, mBluetoothDevice);

                // 定期更新RSSI值
                startMonitoringRssiValue();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnected = false;
                mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
            }
        }

        /**
         * 这个回调执行在mBluetoothGatt.discoverServices()之后，在onCoonecttionStateChange中调用
         * 当这个方法被调用后，才能说明手机和蓝牙设备真正意义上的连接上了
         * 到这一步，说明已经成功和蓝牙设备连接上了，可以进行蓝牙操作了，例如写入数据，读取数据等等
         *
         * 需要执行：
         *          1.getService获取BlueToothGattService
         *          2.getCharactristic获取BluetoothGattCharactristic
         *          3.通过BluetoothGattCharactristic的readCharacteristic去读取数据
         *
         *
         * @param gatt
         * @param status
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i("bleWrapper", "---onServicesDiscovered--状态->" + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // 现在，当服务发现完成时，我们可以为Gatt调用getServices（）
                getSupportedServices();
            }
        }

        /**
         *  如果通过readCharacteristic通知系统读取数据，如果系统读取到了蓝牙设备就会调用这个方法
         *  通过BluetoothGattCharacterstic的getValue可以读取到蓝牙设备的数据
         *  其中的getVlue就是蓝牙返回给我们需要自己处理的数据
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.i("bleWrapper", "---onCharacteristicRead--->");
            // 我们对于获取特征值的要求得到了回应
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // and it success, so we can get the value
                getCharacteristicValue(characteristic);
            }
        }

        /**
         * 当我们执行了gatt.setCharacteristicNotification或写入特征的时候，结果会回调在此
         * 当我们决定用通知的方式获取外设特征值的时候，每当特征值发生变化，程序就会回调到此处。
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            // characteristic's value was updated due to enabled notification, lets get this value
            // the value itself will be reported to the UI inside getCharacteristicValue
            getCharacteristicValue(characteristic);
            // also, notify UI that notification are enabled for particular characteristic
            mUiCallback.uiGotNotification(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic);
        }

        /**
         * 调用BluetoothGattCharactristic的setValue写入数据(单次1-20字节)
         * 调用BluetoothGattCharactristic的writeCharacteristic方法通知系统异步往设置写入数据
         * 系统回调onCharacteristicWrite方法数据已经写入，这时候用getValue检查数据是否正确，如果不对重发
         * @param gatt
         * @param characteristic
         * @param status
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            String deviceName = gatt.getDevice().getName();
            String serviceName = BleNamesResolver.resolveServiceName(characteristic.getService().getUuid().toString().toLowerCase(Locale.getDefault()));
            String charName = BleNamesResolver.resolveCharacteristicName(characteristic.getUuid().toString().toLowerCase(Locale.getDefault()));
            String description = "Device: " + deviceName + " Service: " + serviceName + " Characteristic: " + charName;

            // we got response regarding our request to write new value to the characteristic
            // let see if it failed or not
            if (status == BluetoothGatt.GATT_SUCCESS) {
                mUiCallback.uiSuccessfulWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description);
            } else {
                mUiCallback.uiFailedWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description + " STATUS = " + status);
            }
        }

        ;

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // we got new value of RSSI of the connection, pass it to the UI
                mUiCallback.uiNewRssiAvailable(mBluetoothGatt, mBluetoothDevice, rssi);
            }
        }

        ;


        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {

            System.out.println("onDescriptorWrite = " + status
                    + ", descriptor =" + descriptor.getUuid().toString());

            UUID uuid = descriptor.getCharacteristic().getUuid();
            mUiCallback.uiNotificationForCharacteristicSuccess(uuid, status);

        }

    };

    private Context mParent = null;
    private boolean mConnected = false;
    private String mDeviceAddress = "";

    private BluetoothManager mBluetoothManager = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice mBluetoothDevice = null;
    private BluetoothGatt mBluetoothGatt = null;
    private BluetoothGattService mBluetoothSelectedService = null;
    private List<BluetoothGattService> mBluetoothGattServices = null;

    private Handler mTimerHandler = new Handler();
    private boolean mTimerEnabled = false;

    public void WriteHexString(BluetoothGattCharacteristic mCharacteristicWrite, String string) {
        // TODO Auto-generated method stub
        if (mCharacteristicWrite != null) {
            byte[] dataToWrite = getHexBytes(string);
            writeDataToCharacteristic(mCharacteristicWrite,
                    dataToWrite);
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

}
