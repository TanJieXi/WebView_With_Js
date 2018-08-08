package com.example.newblue.server;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.newblue.event.FirstTextEvent;
import com.example.newblue.utils.CreateExplicitFromImplicitIntent;
import com.example.newblue.utils.Utils;
import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpUtils;
import com.gprinter.command.LabelCommand;
import com.gprinter.io.GpDevice;
import com.gprinter.service.GpPrintService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Bluth on 2017/11/30.
 * 描述：蓝牙打印机
 */

public class BlueToothService extends Service {
    private BluetoothAdapter mBluetoothAdapter;
    private String name = "";//蓝牙名
    private String adress = "";//蓝牙地址
    private GpService mGpService;
    private PrinterServiceConnection printerServiceConnection = null;
    private boolean portopenstate = true;
    //    private WeakHandler mHandler = new WeakHandler();
    private static final int MAIN_QUERY_PRINTER_STATUS = 0xfe;
    public static final String EVENTBUS_PRIVATE = "eventbus_private";
    private String TAG = "blewrapper";
    private int mPrinterIndex = 0;
    private static final int REQUEST_PRINT_LABEL = 0xfd;
    private static final int REQUEST_PRINT_RECEIPT = 0xfc;
    private static final String REQUEST_FLAS = "com.flashUI";
    private int mTotalCopies = 0;
    public static final int REQUEST_ENABLE_BT = 2;
    private boolean isConnectBluePriter = false;//连接蓝牙打印机状态
    private String user_name, user_sex, content;
    private boolean flag = true;
    private boolean flag2 = true;
    private int bule_page = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler.removeCallbacks(runnable);
                    mHandler.postDelayed(runnable, 5000);
                    getBlueTooth();
                }
            }).start();
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(mRunnable);
//            mTotalCopies = 0;
//            try {
//                if (mGpService != null) {
//                    mGpService.queryPrinterStatus(0, 500, MAIN_QUERY_PRINTER_STATUS);
//                }
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            if (getConnectState()) {
                isConnectBluePriter = true;
                if (flag) {
                    flag = false;
                    Log.i(TAG,"蓝牙打印机连接成功");
                }
            } else {
                if (flag2) {
                    flag2 = false;
                    Log.i(TAG,"蓝牙打印机正在连接...");
                }
            }
            mHandler.postDelayed(mRunnable, 500);
        }
    };
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("TAG", action);
            // GpCom.ACTION_DEVICE_REAL_STATUS 为广播的IntentFilter
            if (action.equals(GpCom.ACTION_DEVICE_REAL_STATUS)) {

                // 业务逻辑的请求码，对应哪里查询做什么操作
                int requestCode = intent.getIntExtra(GpCom.EXTRA_PRINTER_REQUEST_CODE, -1);
                // 判断请求码，是则进行业务操作
                if (requestCode == MAIN_QUERY_PRINTER_STATUS) {

                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    String str;
                    if (status == GpCom.STATE_NO_ERR) {
                        str = "打印机正常";
                        isConnectBluePriter = true;
                    } else {
                        str = "打印机 ";
                        if ((byte) (status & GpCom.STATE_OFFLINE) > 0) {
                            str += "脱机";
                        }
                        if ((byte) (status & GpCom.STATE_PAPER_ERR) > 0) {
                            str += "缺纸";
                        }
                        if ((byte) (status & GpCom.STATE_COVER_OPEN) > 0) {
                            str += "打印机开盖";
                        }
                        if ((byte) (status & GpCom.STATE_ERR_OCCURS) > 0) {
                            str += "打印机出错";
                        }
                        if ((byte) (status & GpCom.STATE_TIMES_OUT) > 0) {
                            str += "查询超时";
                        }
                    }
                    if (!str.contains("打印机正常")) {
                        Toast.makeText(getApplicationContext(), "打印机：" + mPrinterIndex + " 状态：" + str, Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (requestCode == REQUEST_PRINT_LABEL) {
                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    if (status == GpCom.STATE_NO_ERR) {
                        // sendLabel();
                        //testSample();
                        sendLabelWithResponse();
                    } else {
                        Toast.makeText(BlueToothService.this, "query printer status error", Toast.LENGTH_SHORT).show();
                    }
                } else if (requestCode == REQUEST_PRINT_RECEIPT) {
                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    if (status == GpCom.STATE_NO_ERR) {
                        //   sendReceipt();

                    } else {
                        Toast.makeText(BlueToothService.this, "query printer status error", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (action.equals(GpCom.ACTION_RECEIPT_RESPONSE)) {
                if (--mTotalCopies > 0) {
                    sendLabelWithResponse();
                }
            } else if (action.equals(GpCom.ACTION_LABEL_RESPONSE)) {
                byte[] data = intent.getByteArrayExtra(GpCom.EXTRA_PRINTER_LABEL_RESPONSE);
                int cnt = intent.getIntExtra(GpCom.EXTRA_PRINTER_LABEL_RESPONSE_CNT, 1);
                String d = new String(data, 0, cnt);
                /**
                 * 这里的d的内容根据RESPONSE_MODE去判断返回的内容去判断是否成功，具体可以查看标签编程手册SET
                 * RESPONSE指令
                 * 该sample中实现的是发一张就返回一次,这里返回的是{00,00001}。这里的对应{Status,######,ID}
                 * 所以我们需要取出STATUS
                 */
                Log.d("LABEL RESPONSE", d);

                if (--mTotalCopies > 0 && d.charAt(1) == 0x00) {
                    sendLabelWithResponse();
                }
            }
        }
    };

    private BroadcastReceiver PrinterStatusBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (GpCom.ACTION_CONNECT_STATUS.equals(intent.getAction())) {
                int type = intent.getIntExtra(GpPrintService.CONNECT_STATUS, 0);
                int id = intent.getIntExtra(GpPrintService.PRINTER_ID, 0);
                Log.d("打印机状态", "connect status " + type);
                /***
                 * GpDevice. STATE_NONE = 0;     //连接断开
                 * GpDevice. STATE_LISTEN = 1;   //监听状态
                 * GpDevice. STATE_CONNECTING = 2;  //正在连接
                 * GpDevice. STATE_CONNECTED = 3;  //已连接
                 * GpDevice.  STATE_INVALID_PRINTER = 4;  //无效的打印机
                 * GpDevice. STATE_VALID_PRINTER = 5; //有效的打印机
                 */
                if (type == GpDevice.STATE_CONNECTING) {
//                    state.setText("链接中...");
                } else if (type == GpDevice.STATE_NONE) {
//                    state.setText("未连接");
                } else if (type == GpDevice.STATE_VALID_PRINTER) {
//                    state.setText("链接中...");
                } else if (type == GpDevice.STATE_INVALID_PRINTER) {

                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Register for broadcasts when a device is discovered
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        this.registerReceiver(mFindBlueToothReceiver, filter);
//        // Register for broadcasts when discovery has finished
//        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        this.registerReceiver(mFindBlueToothReceiver, filter);
//        connection();
    }

    // changes the title when discovery is finished
    private final BroadcastReceiver mFindBlueToothReceiver = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed
                // already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    mNewDevicesArrayAdapter.add(device.getName() + "\n"
//                            + device.getAddress());
                    if (device.getName() != null) {
                        Log.e("BlueToothService蓝牙扫描名称2", "BlueToothService蓝牙扫描名称2:" + device.getName());
                    }
                    if (device.getName() != null && device.getName().contains("Gprinter")) {
                        Log.e("服务2", "扫描到打印机2");
                        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                            if (!adress.equals(device.getAddress())) {
                                adress = device.getAddress();//第一次扫到蓝牙名位Printer的蓝牙的地址
                                Log.e("服务2", "扫描结果2:" + device.getName());
                                connectOrDisConnectToDevice(0, adress);
                            } else {
                                mHandler.removeCallbacks(runnable);
                                Log.e("服务2", "结束扫描2");
                            }
                        } else {
                            Log.e("蓝牙打印机未配对2", "蓝牙打印机未配对2");
                            device.createBond();
                        }
                    }
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
//                setProgressBarIndeterminateVisibility(false);
//                setTitle(R.string.select_bluetooth_device);
//                Log.i("tag", "finish discovery" +mNewDevicesArrayAdapter.getCount());
//                if (mNewDevicesArrayAdapter.getCount() == 0) {
//                    String noDevices = getResources().getText(
//                            R.string.none_bluetooth_device_found).toString();
//                    mNewDevicesArrayAdapter.add(noDevices);
//                }
            }
        }
    };

    private void connection() {
        printerServiceConnection = new PrinterServiceConnection();
        Intent intent = new Intent(this, GpPrintService.class);
        bindService(intent, printerServiceConnection, Context.BIND_AUTO_CREATE); // bindService
    }

    public BlueToothService() {
        super();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        connection();
        registerBroadcast();
        // Get the local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        portopenstate = true;
        adress = "";//重置
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 5000);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        BluePriterinit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
        mHandler.removeCallbacksAndMessages(null);
        if (printerServiceConnection != null) {
            unbindService(printerServiceConnection); // unBindService
        }
        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        if (mFindBlueToothReceiver != null) {
            this.unregisterReceiver(mFindBlueToothReceiver);
        }
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        if (PrinterStatusBroadcastReceiver != null) {
            unregisterReceiver(PrinterStatusBroadcastReceiver);
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /***
     * 搜索蓝牙 name：Printer 001
     */
    @SuppressLint("NewApi")
    private void getBlueTooth() {
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        Log.e("服务", "开始扫描");
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
        if (pairedDevices.size() > 0) {
            Log.e("服务", "扫描到的设备数:" + pairedDevices.size());
            for (BluetoothDevice device : pairedDevices) {
                Log.e("扫描到的已配对设备名称", "扫描到的已配对设备名称:" + pairedDevices.size());
                if (device.getName() != null &&(device.getName().contains("Printer")||device.getName().contains("printer"))) {
                    Log.e("服务", "扫描到打印机");
                    if (!adress.equals(device.getAddress())) {
                        adress = device.getAddress();//第一次扫到蓝牙名位Printer的蓝牙的地址
                        Log.e("服务", "扫描结果:" + device.getName());
                        connectOrDisConnectToDevice(0, adress);
                    } else {
                        mHandler.removeCallbacks(runnable);
                        Log.e("服务", "结束扫描");
                    }
                }
            }
        }
    }

    /***
     * 是自动密码配对
     */
    void aiconnect(BluetoothDevice device) {
        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            Method creMethod = null;
            String str = "0000";
            try {
                Method removeBondMethod = BluetoothDevice.class.getDeclaredMethod("setPin",
                        new Class[]
                                {byte[].class});
                removeBondMethod.invoke(device,
                        new Object[]
                                {str.getBytes()});
                creMethod = BluetoothDevice.class.getMethod("createBond");
                creMethod.invoke(device);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }//完成自动密码配对

    }

    /**
     * 连接蓝牙
     */
    void connectOrDisConnectToDevice(int PrinterId, String addr) {
        Log.e("服务", "扫描成功 开始链结" + "PrinterId:" + PrinterId + "----addr:" + addr);
        int rel = 0;
        Log.e("蓝牙服务=====", String.valueOf(portopenstate));
        if (portopenstate = true) {
            if (PrinterId == 0) {
                try {
                    mGpService.closePort(PrinterId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    rel = mGpService.openPort(0, 4,
                            addr, 0);
                    Log.e("蓝牙服务=====", "编号为" + PrinterId + "蓝牙端口打开------rel" + rel);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
                Log.e("bluetooth service", "result :" + String.valueOf(r));
                if (r != GpCom.ERROR_CODE.SUCCESS) {
                    if (r == GpCom.ERROR_CODE.DEVICE_ALREADY_OPEN) {
                        portopenstate = true;
                        mHandler.removeCallbacks(runnable);
                    } else {
                        Log.i(TAG,GpCom.getErrorText(r));
                    }
                } else if (String.valueOf(r).equals("SUCCESS")) {
                    //  ToastUtil.showShortToast("连接成功");
                    Log.e("服务", "连接成功");
                    portopenstate = true;
                    mHandler.removeCallbacks(runnable);
                }
            }
        } else {
            Log.d("service bluetooth", "DisconnectToDevice ");
            Intent intent = new Intent();
            intent.setAction("com.notfoundDevice");
            sendBroadcast(intent);
//            try {
//              //  mGpService.closePort(PrinterId);
//            } catch (RemoteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }

    }

    /**
     * sdk封装内部内
     */
    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.i("service", "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
        }
    }

    /**
     * 打印机连接状态广播
     */
    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GpCom.ACTION_CONNECT_STATUS);
        this.registerReceiver(PrinterStatusBroadcastReceiver, filter);
    }

    private void BluePriterinit() {
//        setRun();//循环判断蓝牙状态
        DeterminePriteBluetooth();
        // 注册实时状态查询广播
        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_DEVICE_REAL_STATUS));
//        /**
//         * 票据模式下，可注册该广播，在需要打印内容的最后加入addQueryPrinterStatus()，在打印完成后会接收到
//         * action为GpCom.ACTION_DEVICE_STATUS的广播，特别用于连续打印，
//         * 可参照该sample中的sendReceiptWithResponse方法与广播中的处理
//         **/
//        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_RECEIPT_RESPONSE));
        /**
         * 标签模式下，可注册该广播，在需要打印内容的最后加入addQueryPrinterStatus(RESPONSE_MODE mode)
         * ，在打印完成后会接收到，action为GpCom.ACTION_LABEL_RESPONSE的广播，特别用于连续打印，
         * 可参照该sample中的sendLabelWithResponse方法与广播中的处理
         **/
        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_LABEL_RESPONSE));
        /**
         * 链接蓝牙打印机设备
         */
        ConectDevice();
    }

    /**
     * 连续获取连接打印机状态
     */
    private void DeterminePriteBluetooth() {
        mHandler.postDelayed(mRunnable, 500);
    }

    private void ConectDevice() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            Log.i(TAG,"Bluetooth is not supported by the device");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableIntent,
//                        REQUEST_ENABLE_BT);
//                state.setText("蓝牙未开启");
//                ToastUtil.showShortToast("蓝牙未开启");
                bluetoothAdapter.enable();
            } else {
//                state.setText("链接中");
                if (!Utils.isServiceExisted(BlueToothService.this, "com.example.newblue.server.BlueToothService")) {
                    final Intent intent = new Intent();
                    intent.setAction("com.ds.bluetooth");
                    final Intent eintent = new Intent(CreateExplicitFromImplicitIntent.createExplicitFromImplicitIntent(BlueToothService.this, intent));
                    startService(eintent);
                }
            }
        }
    }

    private void sendLabelWithResponse() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSpeed(LabelCommand.SPEED.SPEED1DIV5);//设置打印速度
        tsc.addSize(40, 30); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(2); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        // 开启带Response的打印，用于连续打印
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);
        tsc.addReference(0, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区
        // 绘制一维条码
        tsc.add1DBarcode(55, 20, LabelCommand.BARCODETYPE.EAN13, 100, LabelCommand.READABEL.EANBEL, LabelCommand.ROTATION.ROTATION_0, content);
        // 绘制简体中文
        tsc.addText(25, 150, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                user_name);
        // 绘制简体中文
        tsc.addText(190, 150, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                user_sex);
        tsc.addPrint(1, bule_page); // 打印标签
        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);


        Vector<Byte> datas = tsc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mGpService.sendLabelCommand(mPrinterIndex, str);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onSetPriter() {
        try {
            int type = mGpService.getPrinterCommandType(mPrinterIndex);
            if (type == GpCom.LABEL_COMMAND) {
                mGpService.queryPrinterStatus(mPrinterIndex, 1000, REQUEST_PRINT_LABEL);
            } else {
                Toast.makeText(this, "Printer is not label mode", Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    @Subscribe
    public void onEventMainThread(FirstTextEvent event) {
//        LogUtil.e("BlueToothService收到的eventbus标记==", event.getTag() + "===");
        switch (event.getTag()) {
            case BlueToothService.EVENTBUS_PRIVATE:
                String mData = event.getmMsg();
                Log.e("BlueToote收到eventbus息", "BlueToothService收到eventbus消息" + mData);
                try {
                    JSONObject jsonObject = new JSONObject(mData);
                    user_name = jsonObject.getString("user_name");//用户名称
                    user_sex = jsonObject.getString("user_sex");//用户性别
                    content = jsonObject.getString("content");//打印条码编码
                    bule_page = jsonObject.getInt("bule_page");//一次打印页数
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (isConnectBluePriter) {
                    onSetPriter();
                }
                break;
        }
    }

    /**
     * 获取打印机链接状态
     */
    private boolean getConnectState() {
        try {
            if (mGpService != null && mGpService.getPrinterConnectStatus(0) == GpDevice.STATE_CONNECTED) {
                return true;
            } else {
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}