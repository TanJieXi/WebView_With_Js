package com.example.newblue.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.data.BleScanState;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.clj.fastble.utils.HexUtil;
import com.example.newblue.App;
import com.example.newblue.BlueConstants;
import com.example.newblue.blueconnect.BleWrapper;
import com.example.newblue.bpm.BluetoothChatService;
import com.example.newblue.bpm.CallBack;
import com.example.newblue.bpm.Constants;
import com.example.newblue.bpm.ICallBack;
import com.example.newblue.bpm.ICallBackInfo;
import com.example.newblue.bpm.MtBuf;
import com.example.newblue.comm.ObserverManager;
import com.example.newblue.deviceBox;
import com.example.newblue.interfaces.BleWrapperUiCallbacks;
import com.example.newblue.interfaces.ConnectBlueToothListener;
import com.example.newblue.scan.BluetoothScan;
import com.hdos.blueToothIDReader.IDCardInfor;
import com.hdos.blueToothIDReader.blueToothIDReader;
import com.invs.BtReaderClient;
import com.invs.IClientCallBack;
import com.invs.InvsIdCard;
import com.invs.invswlt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by TanJieXi on 2018/3/28.
 */
@SuppressLint("NewApi")
public class CommenBlueUtils implements BleWrapperUiCallbacks, BluetoothScan.OnScanSuccessListener, ICallBack, ICallBackInfo, IClientCallBack {
    private Context context;
    private volatile static CommenBlueUtils blue;
    private BleWrapper mBleWrapper;
    private String mDeviceAddress = "";
    private String mDeviceName = "";
    private ConnectBlueToothListener mConnectBlueToothListener;
    private ArrayList<BluetoothGattCharacteristic> mCharacteristics;
    private String mLastUpdateTime = "";
    private boolean mNotificationEnabled = false;
    private BleDevice mBleDevice;
    private String mDeviceRSSI = "";
    // 程序步进值
    private int nownotfi = 0;
    private String bluetoothPass = "";
    private BluetoothGattCharacteristic mCharacteristicWrite = null;
    private BluetoothGattService mBTServices;
    private String type = "";  //这个标记用于区分是哪个仪器
    private Disposable mSubscribe;
    private CompositeDisposable dispoList;   //这是一个集合，可以拿来把disposable装进去，统一注销
    private int mDeviceID = -1;
    private boolean isClickStop = false;
    private boolean isQuitUra = false;
    private Handler timehandler1 = new Handler();
    private Runnable timerunnable1 = new Runnable() {
        @Override
        public void run() {
            if (mBleWrapper != null && mDeviceAddress.length() > 5) {
                mBleWrapper.connect(mDeviceAddress);
            }
            timehandler1.removeCallbacks(timerunnable1);
            timehandler1.postDelayed(timerunnable1, 2000);

        }
    };
    private Handler timehandler2 = new Handler();
    private Runnable timerunnable2 = new Runnable() {
        @Override
        public void run() {
            SetNotfi();
        }
    };
    private Handler handleraa = new Handler();
    //第二种血压计
    BluetoothAdapter btAdapt;
    private BluetoothChatService mChatService;
    private CallBack call;
    public MtBuf m_mtbuf = new MtBuf();
    Runnable bpmRunnable = new Runnable() {
        @Override
        public void run() {
            if (!btAdapt.isDiscovering()) {
                btAdapt.startDiscovery();
            }
            timehandler1.postDelayed(bpmRunnable, 500);
        }
    };
    boolean isBpmTwo = false;
    boolean isReader = false;
    //第二种血压计----
    private Runnable runnableaa = new Runnable() {

        @SuppressLint("LongLogTag")
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (mBleWrapper != null && mCharacteristicWrite != null) {

                if (mBTServices.getUuid().toString()
                        .contains("49535343-fe7d-4ae5-8fa9")) {

                    if (!isQuitUra) {
                        // 获取数据
                        Log.e("发送", "938E0400090411");
                        mBleWrapper.WriteHexString(mCharacteristicWrite,
                                "938E0400090411");
                        handleraa.removeCallbacks(runnableaa);
                        // handleraa.postDelayed(runnableaa, 3000);
                    } else {
                        // 关机
                        Log.e("发送", "938E0400090A17");

                        mBleWrapper.WriteHexString(mCharacteristicWrite,
                                "938E0400090A17");
                        handleraa.removeCallbacks(runnableaa);
                        // handleraa.postDelayed(runnableaa, 3000);
                        mConnectBlueToothListener.onInterceptConnect("设备断开");
                    }

                } else {

                    if (!isQuitUra) {
                        // 时钟读取指令
                        Log.e("发送", "938e040008030f");
                        isQuitUra = true;

                        mBleWrapper.WriteHexString(mCharacteristicWrite,
                                "938e040008030f");
                        handleraa.removeCallbacks(runnableaa);
                        handleraa.postDelayed(runnableaa, 3000);
                    } else {
                        // 单条数据传输指令
                        Log.e("发送", "938e0400080410");

                        mBleWrapper.WriteHexString(mCharacteristicWrite,
                                "938e0400080410");
                        handleraa.removeCallbacks(runnableaa);
                        handleraa.postDelayed(runnableaa, 3000);
                    }

                }

            } else {
                Log.e("mBleWrapper或mCharacteristicWrite==空",
                        "mBleWrapper或mCharacteristicWrite==空mBleWrapper或mCharacteristicWrite==空");
            }

        }
    };
    private Disposable disObs;


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

    public void writeHexString(String hex) {
        if (mBleWrapper != null && mCharacteristicWrite != null) {
            mBleWrapper.WriteHexString(mCharacteristicWrite, hex);
        }
    }

    public void writeBgmHexString(String uuid, String hex) {
        if (mBleWrapper != null && mCharacteristicWrite != null) {
            if (mBTServices.getUuid().toString()
                    .contains(uuid)) {
                mBleWrapper.WriteHexString(mCharacteristicWrite,
                        hex);
            }
        }
    }

    public void postUraHander(long time) {
        handleraa.removeCallbacks(runnableaa);
        handleraa.postDelayed(runnableaa, time);
    }

    public void setBTrue() {
        isQuitUra = true;
    }

    public int getDeviceId() {
        return mDeviceID;
    }

    public void disConnectBlueTooth() {
        if (!isClickConnectBlue) {
            Log.i("blewra", "请先连接在执行断开");
            return;
        }
        isClickConnectBlue = false;
        isClickStop = true;
        BluetoothScan.getInstance().Stop();
        mDeviceAddress = "";
        if (mSubscribe != null) {
            mSubscribe.dispose();
        }

        if (disObs != null) {
            disObs.dispose();
        }

        if (mBleWrapper != null) {
            mBleWrapper.stopMonitoringRssiValue();
            mBleWrapper.diconnect();
            mBleWrapper.close();
            mBleWrapper = null;
        }
        if (mConnectBlueToothListener != null) {
            mConnectBlueToothListener.onInterceptConnect("连接断开");
        }


        if (BleScanState.STATE_SCANNING == BleManager.getInstance().getScanSate()) {
            BleManager.getInstance().cancelScan();
        }
        BleManager.getInstance().disconnectAllDevice();//断开所有设备
        BleManager.getInstance().destroy();//退出使用，清理资源

        if (timehandler2 != null) {
            timehandler2.removeCallbacks(timerunnable2);
        }
        if (timehandler1 != null) {
            timehandler1.removeCallbacks(timerunnable1);
            timehandler1.removeCallbacks(bpmRunnable);
        }
        if (handleraa != null) {
            handleraa.removeCallbacks(runnableaa);
        }

        DealBlueDataUtils.getInstance().removeAllHandler();

        //第二种类型的血压计
        if (BlueConstants.BLUE_EQUIP_BPM_TWO.equals(type)) {
            if (isBpmTwo) {
                if (context != null) {
                    context.unregisterReceiver(searchDevices);
                    isBpmTwo = false;
                    if (mChatService != null) {
                        mChatService.stop();
                    }
                }
            }
        }

        if (connect) {
            if (mClient != null) {
                mClient.disconnectBt();
                connect = false;
                mClient.unregister();
            }
        }
        //第二种类型的血压计
        if (BlueConstants.BLUE_EQUIP_IDCARD_READER.equals(type)) {
            if (isReader) {
                if (context != null) {
                    context.unregisterReceiver(mBltReceiver);
                    isReader = false;
                }
            }
        }

    }

    public static final String msg = "invs.blt.readcard";
    private blueToothIDReader blueToothIDReader;
    private boolean isClickConnectBlue = false;

    public void connectBlueTooth(Activity context, final String type, ConnectBlueToothListener connectBlueToothListener) {
        isClickConnectBlue = true;
        this.type = type;
        this.context = context;
        this.mConnectBlueToothListener = connectBlueToothListener;
        //这里有两种情况，如果是第二种广播扫描的血压计，需要使用广播，其他不需要
        if (BlueConstants.BLUE_EQUIP_BPM_TWO.equals(type)) {
            mConnectBlueToothListener.onConnectSuccess("连接中，请稍后");
            isBpmTwo = true;
            call = new CallBack(this.m_mtbuf, this);
            if (mChatService == null) {
                mChatService = new BluetoothChatService(context, call);
            }
            btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
            // 注册Receiver来获取蓝牙设备相关的结果
            IntentFilter intent = new IntentFilter();
            intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
            intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
            intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            context.registerReceiver(searchDevices, intent);
            m_mtbuf.setCallBack(context, this);
            timehandler1.postDelayed(bpmRunnable, 500);
            return;
        }
        if (BlueConstants.BLUE_EQUIP_IDCARD_READER.equals(type)) {
            isReader = true;
            isflag = true;
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(msg);
            context.registerReceiver(mBltReceiver, intentFilter);
            mClient = new BtReaderClient(context);
            mClient.setCallBack(this);
            blueToothIDReader = new blueToothIDReader();
        }
        isBpmTwo = false;
        isClickStop = false;
        mDeviceAddress = "";
        mConnectBlueToothListener.onConnectSuccess("连接中，请稍后");
        mCharacteristics = new ArrayList<>();
        //BluetoothScan.IsAutoJump = false;
        BluetoothScan.getInstance().Start(this);
        Log.i("bleWrapper", "---CommenBlueUtisl--BluetoothScanStart->");
        if (mBleWrapper == null) {
            mBleWrapper = new BleWrapper(context, this);
        }
        Log.i("bleWrapper", "---CommenBlueUtisl--BleWrapper->");
        if (!mBleWrapper.initialize()) {
            BluetoothScan.getInstance().Stop();
        }

        if (disObs != null) {
            disObs.dispose();
        }


        //return;
        //每0.5s去连接一下
        /*mSubscribe = Observable.interval(500, TimeUnit.MILLISECONDS).
                observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //这里接收数据项
                        NewBlue(type);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //这里接收onError
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //这里接收onComplete。
                    }
                });*/
    }

    //接收蓝牙传回的消息
    private BroadcastReceiver mBltReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.i("dfffgdsfgh", "1");
            if (msg.equals(action)) {
                if (intent.getBooleanExtra("tag", false)) {
                    InvsIdCard invsIdCard = (InvsIdCard) intent.getSerializableExtra("InvsIdCard");
                    displayView(invsIdCard);
                } else {
                    mConnectBlueToothListener.onInterceptConnect("读卡失败");
                }
            }
        }
    };


    public void displayView(InvsIdCard card) {
        {
            mConnectBlueToothListener.onConnectSuccess("读卡成功");
            idcardTag = "1";
            String nation = card.nation;
            String UserAddr = card.address;
            byte[] szBmp = invswlt.Wlt2Bmp(card.wlt);
            Bitmap bm = null;
            if ((szBmp != null) && (szBmp.length == 38862)) {
                bm = BitmapFactory.decodeByteArray(szBmp, 0, szBmp.length);
            }
            try {
                phoneFile = new File(imgurl + new Date().getTime() + ".jpg");
                if(!phoneFile.getParentFile().exists()){
                    phoneFile.getParentFile().mkdirs();
                }
                Log.e("图片保存地址===", phoneFile + "");
                FileOutputStream out = new FileOutputStream(phoneFile);
                bm.compress(Bitmap.CompressFormat.PNG, 80, out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String imgs = phoneFile.getAbsolutePath();
            String name = card.name.trim();
            String birth = card.birth.substring(0, 4)
                    + "-" + card.birth.substring(4, 6) + "-"
                    + card.birth.substring(6, 8);
            String resAge = "";
            String personType = "";
            try {
                resAge = Utils.getAge(card.birth.substring(0, 4)
                        + "-" + card.birth.substring(4, 6) + "-"
                        + card.birth.substring(6, 8));
                Log.e("身份证时间", resAge + "");
                if (resAge.length() > 1 && resAge.contains("月")) {
                    personType = "4.儿童";
                } else if (resAge.length() > 1 && resAge.contains("天")) {
                    personType = "4.儿童";
                } else {
                    if (resAge.length() > 1) {
                        int age = Integer.parseInt(resAge.substring(0, resAge.length() - 1));
                        if (age >= 0 && age <= 6) {
                            personType = "4.儿童";
                        } else if (age >= 65) {
                            personType = "2.老年人";
                        } else {
                            personType = "请选择";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String idCard = card.idNo;
            String user_sex = "";
            if (card.sex.contains("男")) {
                user_sex = "男";
            } else if (card.sex.contains("女")) {
                user_sex = "女";
            } else {
                user_sex = "未知";
            }

            String address_hj = card.address;
            String result = nation + "," + UserAddr + ","
                    + imgs + "," + name + "," + birth + "," + resAge + "," + personType + ","
                    + idCard + "," + user_sex + "," + address_hj;
            mConnectBlueToothListener.onDataFromBlue(type, result);
        }
    }


    /**
     * 第二个血压计的操作，获取的结果
     */
    @Override
    public void ReturnData(int gao, int di, int pul) {
        Log.i("BluetoothChatService", "sssss");
        JSONObject json = new JSONObject();
        try {
            json.put("gao", gao + "");
            json.put("pul", pul + "");
            json.put("di", di + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mConnectBlueToothListener.onDataFromBlue(type, json.toString());
    }

    private BroadcastReceiver searchDevices = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            Log.i("dfffgdsfgh", "1");
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            Object[] lstName = b.keySet().toArray();
            // 显示所有收到的消息及其细节
            for (int i = 0; i < lstName.length; i++) {
                String keyName = lstName[i].toString();
                Log.e(keyName, String.valueOf(b.get(keyName)));
            }
            // 搜索设备时，取得设备的MAC地址
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() == null) {
                    return;
                }//NIBP044296
                String bule_address = "";
                Log.i("dfffgdsfgh", device.getName() + "");
                if (device.getName().contains("NIBP") || device.getName().equals(Constants.DEVICE_NAME2)) {
                    Log.e("---------", "-----------");
                    if (!bule_address.equals("")) {
                        if (device.getAddress().equals(bule_address)) {
                            if (mChatService != null)
                                mChatService.stop();
                            timehandler1.removeCallbacks(bpmRunnable);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mChatService.start();
                            mChatService.connect(device);
                            mConnectBlueToothListener.onConnectSuccess("设备在线");
                        }
                    } else {
                        if (mChatService != null)
                            mChatService.stop();
                        timehandler1.removeCallbacks(bpmRunnable);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mChatService.start();
                        mChatService.connect(device);
                        mConnectBlueToothListener.onConnectSuccess("设备在线");
                    }

                }
            }
        }
    };

    public void closeBpm() {
        mBleWrapper.stopMonitoringRssiValue();
        mBleWrapper.diconnect();
        mBleWrapper.close();
        isClickStop = true;
    }


    public void call() {
        Vector<Integer> _ver = MtBuf.m_buf;
        for (int i = 0; i < _ver.size(); i++) {
            Log.i("............", Integer.toHexString(_ver.get(i) & 0xFF));
        }
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
            for (int i = 0; i < App.LeDevices.size(); i++) {
                deviceBox deviceBox = App.LeDevices.get(i);
                if (deviceBox.Type.equals(name.trim()) && deviceBox.isOnline() && deviceBox.device != null) {
                    String bule_address = "";
                    mDeviceID = i;
                    mDeviceName = App.LeDevices.get(i).device.getName();
                    mDeviceAddress = App.LeDevices.get(i).device.getAddress();
                    String endNeedAddress = "";
                    //说明没有绑定设备
                    if (StringUtil.isEmpty(bule_address)) {
                        endNeedAddress = mDeviceAddress;
                    } else {//绑定设备，但是要与扫描的仪器比较一下地址，确保是同一个
                        if (bule_address.equals(mDeviceAddress)) {
                            endNeedAddress = bule_address;
                        }
                    }
                    if (mDeviceName.contains("C01478")) {//尿机
                        if (endNeedAddress.length() > 5) {
                            mBleWrapper.connect(endNeedAddress);
                        }
                        timehandler1.removeCallbacks(timerunnable1);
                        timehandler1.postDelayed(timerunnable1, 1000);
                    } else if (mDeviceName.contains("eBlood-Pressure")) { //血压计
                        //停止
                        CommenBlueUtils.getInstance().disConnectBlueTooth();
                        if (BleManager.getInstance().isConnected(mBleDevice)) {
                            BleManager.getInstance().disconnect(mBleDevice);
                        }
                        ThreadSleep(100);
                        setScanRule(endNeedAddress);
                        startScan();
                    } else {
                        /*mDeviceAddress = StringUtil.isEmpty(bule_address) ?
                                App.LeDevices.get(i).device.getAddress() : bule_address;*/
                        Log.i("mDeviceName", "---mDeviceName->" + mDeviceName);
                        Log.i("mDeviceName", "---mDeviceAddress->" + endNeedAddress);
                        if (!StringUtil.isEmpty(endNeedAddress)) {
                            mBleWrapper.connect(endNeedAddress);
                        }
                    }
                    break;
                }
            }
        }

    }

    private void setScanRule(String blueAddress) {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceMac(blueAddress)                  // 只扫描指定mac的设备，可选
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    private void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                // 开始扫描（UI线程）
                Log.i("sjkljklsjadkll", "--血压计>--开始扫描");
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(final BleDevice bleDevice) {
                // 扫描到一个符合扫描规则的BLE设备（UI线程）
                handleraa.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("sjkljklsjadkll", "--血压计>--扫描到一个符合扫描规则的BLE设备");
                        BleManager.getInstance().cancelScan();  //停止扫描
                        blueconnect(bleDevice);
                    }
                });
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                // 扫描结束，列出所有扫描到的符合扫描规则的BLE设备，可能为空（UI线程）
                Log.i("sjkljklsjadkll", "--血压计>--扫描结束");
            }
        });
    }

    private void blueconnect(BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                // 开始连接（UI线程）
                mConnectBlueToothListener.onInterceptConnect("开始连接");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                // 连接失败（UI线程）
                mConnectBlueToothListener.onInterceptConnect("连接失败");
//                startScan();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接成功，BleDevice即为所连接的BLE设备（UI线程）
                BleManager.getInstance().cancelScan();
                mConnectBlueToothListener.onInterceptConnect("连接成功");
                mBleDevice = bleDevice;
                getBluetoothGattService(gatt);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接中断，isActiveDisConnected表示是否是主动调用了断开连接方法（UI线程）
                if (!isActiveDisConnected) {
                    mConnectBlueToothListener.onInterceptConnect("连接中断");
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }
            }
        });
    }


    @SuppressLint("NewApi")
    private void getBluetoothGattService(BluetoothGatt gatt) {
        for (BluetoothGattService service : gatt.getServices()) {
            Log.e("获取到的服务", service.getUuid() + "--------------------------");
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                Log.e("--------获取到服务特征", characteristic.getUuid() + "");
            }
            //倍泰ePA-46B4
            if (service != null && service.getUuid().toString().contains("0000fff0-0000-1000-8000-00805f9b34fb") && mDeviceName.contains("eBlood-Pressure")) {
                ThreadSleep(300);
                BleManager.getInstance().notify(mBleDevice, service.getUuid().toString(), "0000fff4-0000-1000-8000-00805f9b34fb", new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功（UI线程）
                        Log.e("打开通知操作成功22", "打开通知操作成功");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败（UI线程）
                        Log.e("打开通知操作失败22", "打开通知操作失败");
                        //BleManager.getInstance().handleException(exception);
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现（UI线程）
                        Log.e("血压返回数据22==", HexUtil.formatHexString(data, true) + "==");
//                        ToastUtil.showShortToast(HexUtil.formatHexString(data, true) + "==");
                        if (HexUtil.formatHexString(data, true) != null) {
                            mConnectBlueToothListener.onDataFromBlue(type, HexUtil.formatHexString(data, true).replace(" ", "").toUpperCase());
                        }
                    }
                });
                break;
            }

        }
    }

    private void ThreadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uiDeviceFound(BluetoothDevice device, int rssi, byte[] record) {

    }

    /**
     * 连接到设备成功，在BleWrapper里面的mBleCallback的onConnectionStateChange连接成功后调用
     */
    @SuppressLint("CheckResult")
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
                        /*mConnectBlueToothListener.onConnectSuccess("设备在线");
                        Log.i("sjkljklsjadkll", "连接到设备成功--user_heathe");*/
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
    @SuppressLint("CheckResult")
    @Override
    public void uiDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {
        if (disObs != null) {
            disObs.dispose();
            disObs = null;
        }
        disObs = Observable.interval(0, 3, TimeUnit.SECONDS)
                .take(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.i("dfdsaffgdsfg", aLong + "");
                        if (0 == aLong) {
                            mConnectBlueToothListener.onInterceptConnect("连接断开");
                            Log.e("test", "设备断开");
                            if (!isClickStop) {
                                BluetoothScan.getInstance().Start(CommenBlueUtils.this);
                            }
                            mDeviceAddress = "";
                        } else if (1 == aLong) {
                            if (!isClickStop) {
                                mConnectBlueToothListener.onReConnectEqip("正在尝试重连中,请稍后,请确保设备已打开");
                            }
                        }
                    }
                });
       /* Observable.create(new ObservableOnSubscribe<Object>() {
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
                        if (!isClickStop) {
                            BluetoothScan.getInstance().Start(CommenBlueUtils.this);
                        }
                        mDeviceAddress = "";
                    }
                });
*/
    }

    /**
     * 设置ura的uuid
     *
     * @param service
     * @param uuid
     */
    private void setUraUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null && uuid.contains("e7810a71-73ae-499d-8c15")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    16);
            setDevUUID(service, "bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    8);
            bluetoothPass = "938E08000801434F4E5445";// 设备确认
            SetNotfi();
            handleraa.removeCallbacks(runnableaa);
            handleraa.postDelayed(runnableaa, 3000);
            return;
        }

        if (service != null && uuid.contains("49535343-fe7d-4ae5-8fa9")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(mBTServices);
            setDevUUID(service, "49535343-1e4d-4bd9-ba61-23c647249616",
                    16);
            setDevUUID(service, "49535343-8841-43f4-a8d4-ecbe34729bb3",
                    8);
            bluetoothPass = "";// 获取数据
            SetNotfi();
            handleraa.removeCallbacks(runnableaa);
            handleraa.postDelayed(runnableaa, 3000);
            return;
        }

        if (service != null && uuid.contains("0000ffe0-0000-1000-8000-00805f9b34fb")) {

            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";// 获取数据
            SetNotfi();
            handleraa.removeCallbacks(runnableaa);
            handleraa.postDelayed(runnableaa, 3000);
        }

    }

    /**
     * 设置bpm的uuid
     *
     * @param service
     * @param uuid
     */
    private void setBpmUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null
                && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb") && mDeviceName.contains("eBlood-Pressure")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000fff4-0000-1000-8000-00805f9b34fb",
                    16);
//                            setDevUUID("0000fff2-0000-1000-8000-00805f9b34fb",
//                                    8);
//                            bluetoothPass = "AA5504B10000B5";
            bluetoothPass = "FFFD0203";
            SetNotfi();
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb") && !mDeviceName.contains("eBlood-Pressure")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000fff2-0000-1000-8000-00805f9b34fb",
                    8);
//                            bluetoothPass = "AA5504B10000B5";
            bluetoothPass = "FDFDFA050D0A";
            SetNotfi();
            DealBlueDataUtils.getInstance().removeBpmRRHanler();
            Log.e("服务和特征2222222222222222", "服务和特征222222222222222222222222222");
        }

    }


    /**
     * 设置bgm的uuid
     */
    private void setBgmUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null && uuid.contains("0000ffe0-0000-1000-8000")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 16);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 8);
            bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        } else if (service != null && uuid.contains("11223344-5566-7788-99aa-bbccddeeff00")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "00004a5b-0000-1000-8000-00805f9b34fb", 16);
            setDevUUID(service, "00004a5b-0000-1000-8000-00805f9b34fb", 8);
            SetNotfi();
        } else if (service != null && uuid.contains("6e400001-b5a3-f393-e0a9-e50e24dcca9e")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "6e400002-b5a3-f393-e0a9-e50e24dcca9e",
                    16);
            setDevUUID(service, "6e400003-b5a3-f393-e0a9-e50e24dcca9e",
                    16);
            SetNotfi();
        }
    }

    /**
     * 设置bmi的uuid
     *
     * @param service
     * @param uuid
     */
    private void setBmiUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        // 改进后的体脂称
        if (service != null && uuid.contains("0000ffe0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 16);

            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("ba11f08c-5f14-0b0d-1070")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        } else if (service != null && uuid.contains("00001950-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "00002a6d-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "00002a6c-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000fff4-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000fff1-0000-1000-8000-00805f9b34fb",
                    8);

            String bp = "03";
            String bmi_sex = "男";
            Date bmi_birth = Utils.ConverToDate("1993-12-01");
            int bmi_height = 170;
            if ("男".equals(bmi_sex)) {
                bp += "01";
            } else {
                bp += "00";
            }

            bp += "00";

            if (bmi_height >= 0) {
                int h = (int) (double) bmi_height;
                bp += Utils.intToHex(h).toUpperCase();
            } else {
                bp += "AA";
            }

            if (bmi_birth != null) {
                String ag = Utils.calcAge(Utils
                        .ConverToString(bmi_birth));

                int a = (int) Integer.parseInt(ag);
                bp += Utils.intToHex(a).toUpperCase();
            } else {
                bp += "AA";
            }

            bp += "01";

            byte[] bytes = Utils.getHexBytes(bp);

            int ChkSum = 0;
            for (int i = 0; i < bytes.length; i++) {
                ChkSum = (int) (ChkSum ^ bytes[i]);
            }
            String mm = Integer.toHexString(ChkSum & 0x0FF);
            System.out.println("----------------" + mm + "H");

            bluetoothPass = "FE" + bp + mm;
            Log.e("bluetoothPass3==", bluetoothPass
                    + "zzzzzzzzzzz");

            timehandler2.removeCallbacks(timerunnable2);
            timehandler2.postDelayed(timerunnable2, 3000);
        }
    }

    /**
     * 血脂检测
     */
    private void setBftUUid(BluetoothGattService service, String uuid) {
        if (service != null
                && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000fff4-0000-1000-8000-00805f9b34fb", 16);
            setDevUUID(service, "0000fff1-0000-1000-8000-00805f9b34fb", 8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null
                && uuid.contains("0000f808-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000fa52-0000-1000-8000-00805f9b34fb", 16);
            setDevUUID(service, "0000fa18-0000-1000-8000-00805f9b34fb", 8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000ffe0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe4-0000-1000-8000-00805f9b34fb", 16);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000ff12-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ff02-0000-1000-8000-00805f9b34fb",
                    16);//0000ff02-0000-1000-8000-00805f9b34fb
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("c14d2c0a-401f-b7a9-841f-e2e93b80f631")) {//艾科血脂蓝牙
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "81eb77bd-89b8-4494-8a09-7f83d986ddc7", 16);
            setDevUUID(service, "6c1cef07-3377-410e-b231-47f76c5a39e1", 16);
            bluetoothPass = "";
            SetNotfi();
        }

    }


    /**
     * 糖化血球蛋白检测
     */
    private void setGlHgbUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null && uuid.contains("0000ffe0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 16);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000ffe1-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 16);
            bluetoothPass = "";
            SetNotfi();
        }
    }


    /**
     * 血红蛋白检测
     */
    private void setHgbUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null && uuid.contains("00001000-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "00001002-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "00001001-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";
            SetNotfi();
        }
    }


    /**
     * 设置oxi的uuid
     */
    private void setOxiUUid(BluetoothGattService service, String uuid) {
        Log.i("dsfdsafgsdf", uuid);
        if (service != null && uuid.contains("ba11f08c-5f14-0b0d-1080")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        }

    }

    /**
     * 血糖尿酸总胆固醇三合一的检测
     */
    private void setBuaUUid(BluetoothGattService service, String uuid) {
        if (service != null && uuid.contains("00001808-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "00002a18-0000-1000-8000-00805f9b34fb", 16);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDevUUID(service, "00002a34-0000-1000-8000-00805f9b34fb", 16);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDevUUID(service, "00002a52-0000-1000-8000-00805f9b34fb", 8);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // bluetoothPass = "AA5504B10000B5";
            SetNotfi();
        } else if (service != null && uuid.contains("0000ffe0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ffe1-0000-1000-8000-00805f9b34fb", 16);
            setDevUUID(service, "0000ffe2-0000-1000-8000-00805f9b34fb", 8);
            // bluetoothPass = "AA5504B10000B5";
            bluetoothPass = "AAAAAAAA";
            SetNotfi();
        } else if (service != null
                && uuid.contains("0000ff12-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            mBleWrapper.getCharacteristicsForService(mBTServices);
            setDevUUID(service, "0000ff02-0000-1000-8000-00805f9b34fb", 16);
            SetNotfi();
        }
    }

    /**
     * 设置体温计的UUid
     *
     * @param service 服务，循环的
     * @param uuid    该服务的uuid，要找到可以进行通讯的UUID
     */
    public void setTemUUid(BluetoothGattService service, String uuid) {
        Log.i("piepoidjs", "-setTemUUid-->-uuid-->" + uuid);
        // 在这里设置服务和特征
        if (service != null && uuid.contains("e7810a71-73ae-499d-8c15-faa9aef0c3f2")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    16);
            setDevUUID(service, "bef8d6c9-9c21-4c9e-b632-bd58c1009f9f",
                    8);
            bluetoothPass = "11";
            SetNotfi();
            // 福达康体温枪
        } else if (service != null && uuid.contains("00005970-6d75-4753-5053-676e6f6c7553")) {
            mBTServices = service;
            Log.i("piepoidjs", "--->-uuid-tem-uiAvailableServices->");
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "02005970-6d75-4753-5053-676e6f6c7553",
                    16);
            SetNotfi();
            // 体达体温枪
        } else if (service != null && uuid.contains("45531234-6565-7370-6f54-676e6f6c7553")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "45531236-6565-7370-6f54-676e6f6c7553",
                    16);
            SetNotfi();
            // 如果是 连续体温
        } else if (service != null && uuid.contains("ba11f08c-5f14-0b0d-10d0-ff434d4d4544")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "0000cd01-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd02-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd03-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd04-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000cd20-0000-1000-8000-00805f9b34fb",
                    8);
            bluetoothPass = "";
            SetNotfi();
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb") && mDeviceName.contains("znjtys_tem")) {
            mBTServices = service;
            mBleWrapper
                    .getCharacteristicsForService(service);
            setDevUUID(service, "0000fff1-0000-1000-8000-00805f9b34fb",
                    16);
            setDevUUID(service, "0000fff3-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();
            //家康体温计
        } else if (service != null && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
            mBTServices = service;
            setDevUUID(service, "0000fff2-0000-1000-8000-00805f9b34fb",
                    16);
            bluetoothPass = "";
            SetNotfi();

        }

    }

    /**
     * 真正意义上的连接到设备成功，在BleWrapper里面的mBleCallback的onServicesDiscovered连接成功后调用
     * onConnectionStateChange--->执行discoverService---->onServicesDiscovered---->到此方法
     *
     * @param gatt
     * @param device
     * @param services 可以通过这个方法的getCharatristic，然后通过readCharacteristic去读取特定数据
     */
    @SuppressLint("CheckResult")
    @Override
    public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device, List<BluetoothGattService> services) {
        Log.i("bleWrapper", "--->-uuid-->" + "uiAvailableServices");
        Log.i("bleWrapper", "--->-uuid-->" + services.size());
        //之前这两句话是写在startServicesDiscovery里面的，不一定连接成功，services.size >0才说明成功了
        if (services.size() > 0) {
            handleraa.post(new Runnable() {
                @Override
                public void run() {
                    mConnectBlueToothListener.onConnectSuccess("设备在线，请测量");
                    Log.i("sjkljklsjadkll", "连接到设备成功--user_heathe");
                }
            });
        }
        nownotfi = 0;
        mCharacteristics.clear();
        for (BluetoothGattService service : services) {
            if (mBTServices == null || !mBTServices.equals(service)) {
                String uuid = service.getUuid().toString();
                Log.i("bleWrapper", "--->-uuid-->" + uuid);
                //检查一下可以进行
                //如果我们无法得知这两个所需的UUID时，
                // 我们也可以通过上下一步的方法来获取（打印所有特征UUID，取出自己想要的特征）
                checkUUID(service);
                switch (type.trim()) {
                    case BlueConstants.BLUE_EQUIP_TEM://体温枪
                        DealBlueDataUtils.getInstance().setTemString();
                        setTemUUid(service, uuid);
                       /* Observable.timer(1000, TimeUnit.MICROSECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        mConnectBlueToothListener.onConnectSuccess("体温测量中，请稍后。。。");
                                    }
                                });
*/
                        break;
                    case BlueConstants.BLUE_EQUIP_OXI://血氧
                        setOxiUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_URA://尿机
                        DealBlueDataUtils.getInstance().setUraSra();
                        isQuitUra = false;
                        setUraUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_BPM://血压
                        setBpmUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_BMI://体重体脂称
                        setBmiUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_BGM://血糖计
                        setBgmUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                        DealBlueDataUtils.getInstance().setHgbFlag();
                        setHgbUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_GLHGB://糖化血红蛋白
                        DealBlueDataUtils.getInstance().setGlHgbString();
                        setGlHgbUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_BFT://血脂
                        DealBlueDataUtils.getInstance().setUraSra();
                        setBftUUid(service, uuid);
                        break;
                    case BlueConstants.BLUE_EQUIP_BUA://血糖，尿酸，总胆固醇三合一
                        DealBlueDataUtils.getInstance().setUraSra();
                        setBuaUUid(service, uuid);
                        break;
                    default:
                        break;
                }
            } else {
                Log.i("bleWrapper", "--->-uuid-tem-uiAvailableServices-nullaaaaaa>");
            }
        }
    }

    /**
     * 检查一下可以进行通讯的UUID,如果我们无法得知这两个所需的UUID时，
     * 我们也可以通过上下一步的方法来获取（打印所有特征UUID，取出自己想要的特征）
     *
     * @param service
     */
    private void checkUUID(BluetoothGattService service) {
        List<BluetoothGattCharacteristic> gattCharacteristics = service.getCharacteristics();
        for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
            int charaProp = gattCharacteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                Log.i("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid());
                Log.i("nihao", "gattCharacteristic的属性为:  可读");
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                Log.i("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid());
                Log.i("nihao", "gattCharacteristic的属性为:  可写");
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                Log.i("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid() + gattCharacteristic);
                Log.i("nihao", "gattCharacteristic的属性为:  具备通知属性");
            }

        }
    }

    /**
     * 读取数据
     *
     * @param string
     * @param chartype Characteristic是手机与BLE终端交换数据的关键，
     *                 Characteristic有较多的跟权限相关的字段，例如PERMISSION和PROPERTY，而其中最常用的是PROPERTY，
     *                 本文所用的BLE蓝牙模块竟然没有标准的Characteristic的PERMISSION。Characteristic的PROPERTY可以通过位运算符组合来设置读写属性，
     *                 例如READ|WRITE、READ|WRITE_NO_RESPONSE|NOTIFY，因此读取PROPERTY后要分解成所用的组合
     */
    private void setDevUUID(BluetoothGattService service, String string, int chartype) {
        BluetoothGattCharacteristic temch = null;
        int props;
        if (chartype != 8) {
            //获取此服务结点下的某个Characteristic对象
            temch = service.getCharacteristic(UUID
                    .fromString(string));
            if (temch != null) {
                props = temch.getProperties();
                if ((props & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {//设备具有可通知的功能，也可以判断可读可写 PROPERTY_READ和PROPERTY_WRITE
                    mCharacteristics.add(temch);
                }
            }
        } else {
            temch = service.getCharacteristic(UUID
                    .fromString(string));
            if (temch != null) {
                props = temch.getProperties();
                if ((props & (BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) > 0) {
                    mCharacteristicWrite = temch;
                }
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
     */
    @SuppressLint("CheckResult")
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


    private void newValueForCharacteristic(final BluetoothGattCharacteristic ch,
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


    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
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
     * 接收到返回数据的前提是我们设置了该特征具有Notification功能，
     * setNotificationForCharacteritic设置当指定characteristic值变化时，发出通知
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

    private BtReaderClient mClient = null;
    private boolean connect = false;
    private boolean isflag = false;

    private void onReadIDCard() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final InvsIdCard card = mClient.readCard();
                if (card != null) {
                    handleraa.post(new Runnable() {
                        @Override
                        public void run() {
                            displayView(card);
                        }
                    });
                } else {
                    handleraa.post(new Runnable() {
                        @Override
                        public void run() {
                            mConnectBlueToothListener.onInterceptConnect("读卡失败");
                        }
                    });

                }
            }
        }).start();

    }


    private File phoneFile;
    private String idcardTag = "0";

    private void showString(String string) {
        // TODO Auto-generated method stub
        Log.e("sdjklasjdklf", string);
    }

    private Calendar date = Calendar.getInstance();
    private String imgurl = Environment.getExternalStorageDirectory().getAbsoluteFile()
            + File.separator + "znjtys_Img" + File.separator
            + date.get(Calendar.YEAR) + File.separator
            + (date.get(Calendar.MONTH) + 1) + File.separator
            + date.get(Calendar.DAY_OF_MONTH) + File.separator;

    /**
     * 扫描设备之后的回调函数，在这里进行设备的连接操作，不管这个全局的集合里面有没有
     * 这个设备，只要扫描到了就会回调这个函数，只是说同样的设备在全局的集合里面不会在存在
     *
     * @param deviceBoxList 全局的设备集合
     */
    @Override
    public void onScanFetch(List<deviceBox> deviceBoxList) {
        if (StringUtil.isEmpty(type)) {
            return;
        }
        if (mDeviceAddress.length() < 2) {
            for (int i = 0; i < deviceBoxList.size(); i++) {
                deviceBox deviceBox = deviceBoxList.get(i);
                if (deviceBox.Type.equals(type.trim()) && deviceBox.isOnline() && deviceBox.device != null) {
                    String bule_address = ""; //这个可能是设备的一个地址，根据type来区分
                    mDeviceID = i;
                    mDeviceName = deviceBoxList.get(i).device.getName();
                    mDeviceAddress = deviceBoxList.get(i).device.getAddress();
                    String endNeedAddress = ""; //最终使用的地址，是绑定地址还是设备地址
                    //说明没有绑定设备
                    if (StringUtil.isEmpty(bule_address)) {
                        endNeedAddress = mDeviceAddress;
                    } else {//绑定设备，但是要与扫描的仪器比较一下地址，确保是同一个
                        if (bule_address.equals(mDeviceAddress)) {
                            endNeedAddress = bule_address;
                        } else {
                            //执行到这步，说明扫描到的设备和绑定的设备不一样,就需要提醒解除绑定后再执行操作
                            return;
                        }
                    }
                    if (type.trim().equals("idr")) {
                        if (mDeviceName.contains("INVS300")) {
                            if (!connect) {
                                if (!mClient.connectBt(mDeviceAddress)) {
                                    handleraa.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mConnectBlueToothListener.onInterceptConnect("连接失败");
                                        }
                                    });
                                }
                            } else {
                                handleraa.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onReadIDCard();//单次阅读
                                    }
                                });
                            }
                        }

                        if (mDeviceAddress.length() > 16 && isflag && !mDeviceName.contains("INVS300")) {
                            Log.e("连接蓝牙地址==", mDeviceAddress + "=====");
                            isflag = false;
                            // String bluToothMacAdd = "20:70:2B:00:2B:14";
                            String pkName = context.getPackageName();

                            String salt = "123456";
                            String bluToothMacAdd = mDeviceAddress;
                            IDCardInfor IDCardInfor;
                            try {
                                IDCardInfor = blueToothIDReader.hdosBlueToothIDReader(pkName, salt, bluToothMacAdd);

                                showString("pkName：" + pkName);
                                showString("bluToothMacAdd：" + bluToothMacAdd);


                                if (IDCardInfor.result.equals("90")) {// 读卡成功
                                    handleraa.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mConnectBlueToothListener.onConnectSuccess("读卡成功");
                                            idcardTag = "1";
                                        }
                                    });

                                    int[] colors = blueToothIDReader.convertByteToColor(IDCardInfor.bmpFile);
                                    final Bitmap bm = Bitmap.createBitmap(colors, 102, 126, Bitmap.Config.ARGB_8888);

                                    try {
                                        phoneFile = new File(imgurl + new Date().getTime() + ".jpg");
                                        Log.e("图片保存地址===", phoneFile + "");
                                        if (!phoneFile.getParentFile().exists()) {
                                            phoneFile.getParentFile().mkdirs();
                                        }
                                        FileOutputStream out = new FileOutputStream(phoneFile);
                                        bm.compress(Bitmap.CompressFormat.PNG, 80, out);
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                    final com.hdos.blueToothIDReader.IDCardInfor finalIDCardInfor = IDCardInfor;
                                    handleraa.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            byte[] ba = finalIDCardInfor.bmpFile;
                                            String nation = finalIDCardInfor.nation;
                                            String UserAddr = finalIDCardInfor.address;
                                            String imgs = phoneFile.getAbsolutePath();
                                            String name = finalIDCardInfor.name.trim();
                                            String birth = finalIDCardInfor.birth.substring(0, 4)
                                                    + "-" + finalIDCardInfor.birth.substring(4, 6) + "-"
                                                    + finalIDCardInfor.birth.substring(6, 8);
                                            String resAge = "";
                                            String personType = "";
                                            try {
                                                resAge = Utils.getAge(finalIDCardInfor.birth.substring(0, 4)
                                                        + "-" + finalIDCardInfor.birth.substring(4, 6) + "-"
                                                        + finalIDCardInfor.birth.substring(6, 8));
                                                Log.e("身份证时间", resAge + "");

                                                if (resAge.length() > 1 && resAge.contains("月")) {
                                                    personType = "4.儿童";
                                                } else if (resAge.length() > 1 && resAge.contains("天")) {
                                                    personType = "4.儿童";
                                                } else {
                                                    if (resAge.length() > 1) {
                                                        int age = Integer.parseInt(resAge.substring(0, resAge.length() - 1));
                                                        if (age >= 0 && age <= 6) {
                                                            personType = "4.儿童";
                                                        } else if (age >= 65) {
                                                            personType = "2.老年人";
                                                        } else {
                                                            personType = "请选择";
                                                        }
                                                    }
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            // setuheight.setText(UsersMod.get_uHeight()+"");
                                            // setuwaist.setText(UsersMod.get_waist());
                                            String idCard = finalIDCardInfor.iDNo;
                                            String user_sex = "";
                                            if (finalIDCardInfor.sex.contains("男")) {
                                                user_sex = "男";
                                            } else if (finalIDCardInfor.sex.contains("女")) {
                                                user_sex = "女";
                                            } else {
                                                user_sex = "未知";
                                            }
                                            String address_hj = finalIDCardInfor.address;
                                            String result = nation + "," + UserAddr + ","
                                                    + imgs + "," + name + "," + birth + "," + resAge + "," + personType + ","
                                                    + idCard + "," + user_sex + "," + address_hj;
                                            mConnectBlueToothListener.onDataFromBlue(type, result);
                                        }

                                    });


                                    // Bitmap bm1=Bitmap.createScaledBitmap(bm,
                                    // (int)(102*2),(int)(126*2),
                                    // false); //这里你可以自定义它的大小
                                    // ImageView imageView = new ImageView(this);
                                    // imageView.setScaleType(ImageView.ScaleType.MATRIX);
                                    // imageView.setImageBitmap(bm);
                                    // showGroup.addView(imageView);
                                    // showString("");
                                    // showString("名字：" + IDCardInfor.name);
                                    // showString("性别：" + IDCardInfor.sex);
                                    // showString("民族：" + IDCardInfor.nation);
                                    // showString("生日：" + IDCardInfor.birth);
                                    // showString("地址：" + IDCardInfor.address);
                                    // showString("身份证号：" + IDCardInfor.iDNo);
                                    // showString("发卡机构：" + IDCardInfor.department);
                                    // showString("有效日期：" + IDCardInfor.effectDate + "至"
                                    // + IDCardInfor.expireDate);
                                    //
                                    // showString("SDK版本：" + IDCardInfor.SDKVersion);
                                    // showString("SDKName：" + IDCardInfor.SDKName);
                                    // showString("读取类型：" + IDCardInfor.ReadType);

                                } else {// 读卡错误
                                    if (IDCardInfor.result.equals("41")) {
                                        showString("蓝牙地址为null");
                                        handleraa.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mConnectBlueToothListener.onInterceptConnect("蓝牙地址为null");
                                            }
                                        });
                                    }
                                    if (IDCardInfor.result.equals("42")) {
                                        showString("蓝牙连接失败");
                                        handleraa.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mConnectBlueToothListener.onInterceptConnect("蓝牙连接失败");
                                            }
                                        });
//                        dqsfz.setText("蓝牙连接失败");
                                    }
                                    if (IDCardInfor.result.equals("43")) {
                                        showString("寻卡失败");
                                        handleraa.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mConnectBlueToothListener.onInterceptConnect("寻卡失败");
                                            }
                                        });
//                        dqsfz.setText("寻卡失败");
                                    }
                                    if (IDCardInfor.result.equals("44")) {
                                        showString("选卡失败");
                                        handleraa.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mConnectBlueToothListener.onInterceptConnect("选卡失败");
                                            }
                                        });
//                        dqsfz.setText("选卡失败");
                                    }
                                    if (IDCardInfor.result.equals("45")) {
                                        showString("读卡失败");
                                        handleraa.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mConnectBlueToothListener.onInterceptConnect("读卡失败");
                                            }
                                        });
//                        dqsfz.setText("读卡失败");
                                    }

                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                showString("读卡设备故障!请重新启动！");
                                handleraa.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mConnectBlueToothListener.onInterceptConnect("读卡设备故障!请重新启动！");
                                    }
                                });
//                dqsfz.setText("亲！重启读卡器再试试！");
                            }
                        } else {
                            handleraa.post(new Runnable() {
                                @Override
                                public void run() {
                                    mConnectBlueToothListener.onInterceptConnect("未检测到设备");
                                }
                            });
                        }


                        return;
                    }


                    if (mDeviceName.contains("C01478")) {//尿机
                        if (endNeedAddress.length() > 5) {
                            mBleWrapper.connect(endNeedAddress);
                        }
                        timehandler1.removeCallbacks(timerunnable1);
                        timehandler1.postDelayed(timerunnable1, 1000);
                    } else if (mDeviceName.contains("eBlood-Pressure")) { //血压计
                        //停止
                        CommenBlueUtils.getInstance().disConnectBlueTooth();
                        if (BleManager.getInstance().isConnected(mBleDevice)) {
                            BleManager.getInstance().disconnect(mBleDevice);
                        }
                        ThreadSleep(100);
                        setScanRule(endNeedAddress);
                        startScan();
                    } else {
                        /*mDeviceAddress = StringUtil.isEmpty(bule_address) ?
                                App.LeDevices.get(i).device.getAddress() : bule_address;*/
                        Log.i("bleWrapper", "---mDeviceName->" + mDeviceName);
                        Log.i("bleWrapper", "---mDeviceAddress->" + endNeedAddress);
                        if (!StringUtil.isEmpty(endNeedAddress)) {
                            mBleWrapper.connect(endNeedAddress);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onBtState(boolean b) {
        if (b) {
            connect = true;
            mConnectBlueToothListener.onConnectSuccess("连接成功");
            onReadIDCard();//单次阅读
            // onReadIDCardAlways();//循环都卡
        } else {
            connect = false;
            mConnectBlueToothListener.onInterceptConnect("设备连接断开");
        }
    }
}
