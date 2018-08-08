package com.example.newblue.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
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
import android.util.Log;
import android.widget.Toast;

import com.example.newblue.BlueConstants;
import com.example.newblue.event.FirstTextEvent;
import com.example.newblue.interfaces.ConnectBluePrintListener;
import com.example.newblue.server.BlueToothService;
import com.gprinter.aidl.GpService;
import com.gprinter.command.GpCom;
import com.gprinter.io.GpDevice;
import com.gprinter.service.GpPrintService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by TanJieXi on 2018/8/7.
 */
public class CommentBluePrintUtils {
    private volatile static CommentBluePrintUtils blue;

    private CommentBluePrintUtils() {

    }

    public static CommentBluePrintUtils getInstance() {
        if (blue == null) {
            synchronized (CommentBluePrintUtils.class) {
                if (blue == null) {
                    blue = new CommentBluePrintUtils();
                }
            }
        }
        return blue;
    }

    private GpService mGpService = null;
    private PrinterServiceConnection printerServiceConnection = null;
    private Context mContext;
    private ConnectBluePrintListener mConnectBluePrintListener;
    private static final int MAIN_QUERY_PRINTER_STATUS = 0xfe;
    private static final int REQUEST_PRINT_LABEL = 0xfd;
    private static final int REQUEST_PRINT_RECEIPT = 0xfc;
    private static final int PRIVATE_CONNECT_SUCCESS = 0x123;
    private static final int PRIVATE_CONNECT_NOE = 0x124;
    private boolean isConnectBluePriter = false;//连接蓝牙打印机状态
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRIVATE_CONNECT_SUCCESS:
                    mConnectBluePrintListener.onConnectStatus(1, "蓝牙打印机连接成功");
                    break;
                case PRIVATE_CONNECT_NOE:
                    mConnectBluePrintListener.onConnectStatus(0, "蓝牙打印机正在连接...");
                    break;
            }

        }
    };

    public boolean getConnectBluePrint() {
        return isConnectBluePriter;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(mRunnable);
            if (getConnectState()) {
//                    ToastUtil.showShortToast("蓝牙打印机连接成功!");
                mHandler.sendEmptyMessage(PRIVATE_CONNECT_SUCCESS);
                isConnectBluePriter = true;
            } else {
//                ToastUtil.showShortToast("蓝牙打印机正在连接...");
                mHandler.sendEmptyMessage(PRIVATE_CONNECT_NOE);
                isConnectBluePriter = false;
            }
            mHandler.postDelayed(mRunnable, 500);
        }
    };

    public void disConnectBluePrint() {
        if(!isConnect){
            Toast.makeText(mContext,"请先连接在断开",Toast.LENGTH_SHORT).show();
            return;
        }
        isConnect = false;
        if (printerServiceConnection != null) {
            mContext.unbindService(printerServiceConnection); // unBindService
        }
        mContext.unregisterReceiver(PrinterStatusBroadcastReceiver);
        mHandler.removeCallbacks(mRunnable);
        mHandler.removeCallbacksAndMessages(null);
      /*  if (!SPUtils.getInstance(SPUtilsName.BULEPRITER_SETTING).getString(SPUtilsName.BULEPRITER_OPEN).equals("开")) {

        }*/
        mConnectBluePrintListener.onConnectStatus(2, "已断开");
        if (Utils.isServiceExisted(mContext, "com.example.newblue.server.BlueToothService")) {
            mContext.stopService(new Intent(mContext, BlueToothService.class));
        }
        addBarCode();
    }

    public void connectBlue(Context context, ConnectBluePrintListener connectBluePrintListener) {
        this.mConnectBluePrintListener = connectBluePrintListener;
        this.mContext = context;
        mConnectBluePrintListener.onConnectStatus(0, "蓝牙打印机正在连接...");
        connection();
        registerBroadcast();
        BluePriterinit();
    }

    private int bule_pages = 1;  //页数

    /**
     * json格式  里面为四个参数   ，打印数量，打印姓名，打印条码，打印性别
     * @param json   BlueConstants.BLUE_PRINT_PRINTNUM  打印数量
     *               BlueConstants.BLUE_PRINT_NAME      打印姓名
     *               BlueConstants.BLUE_PRINT_BAR_CODE  打印条码
     *               BlueConstants.BLUE_PRINT_SEX       打印性别
     */
    public void startPrint(JSONObject json) {
        if (json == null) {
            Toast.makeText(mContext, "参数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String printNum = json.optString(BlueConstants.BLUE_PRINT_PRINTNUM);  //打印数量
        String printName = json.optString(BlueConstants.BLUE_PRINT_NAME);  //打印姓名
        String printBarCode = json.optString(BlueConstants.BLUE_PRINT_BAR_CODE);  //打印条码
        String printSex = json.optString(BlueConstants.BLUE_PRINT_SEX);   //打印性别

        if (StringUtil.isEmpty(printNum) || StringUtil.isEmpty(printName) || StringUtil.isEmpty(printBarCode) || StringUtil.isEmpty(printSex)) {
            Toast.makeText(mContext, "四个参数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        int bule_page_num = 0;
        boolean isnum = false;
        try {
            bule_page_num = Integer.parseInt(printNum);
            if (bule_page_num > 0 && bule_page_num <= 10) {
                isnum = true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!isnum) {
            Toast.makeText(mContext, "打印页数输入不正确或者超出范围,1-10张!", Toast.LENGTH_SHORT).show();
            return;
        }

        String blue_number = SPUtils.getInstance(BlueConstants.BULEPRITER_SETTING).getString(BlueConstants.BULEPRITER_NUMBER);
            /*if (!SPUtils.getInstance(SPUtilsName.BULEPRITER_SETTING).getString(SPUtilsName.BULEPRITER_OPEN).equals("开")) {
                blue_number = temperature_tv.getText().toString();
                if (blue_number.length() > 12) {
                    blue_number = blue_number.substring(0, blue_number.length() - 1);
                }
            }*/
//                            String mData = "";
        int e1 = 0;

          /*  if (blue_number.length() != 12) {
                blue_number = "211111111110";
            }*/
        try {
            char[] chars = blue_number.toCharArray();
            int a = Integer.parseInt(chars[0] + "")
                    + Integer.parseInt(chars[2] + "")
                    + Integer.parseInt(chars[4] + "")
                    + Integer.parseInt(chars[6] + "")
                    + Integer.parseInt(chars[8] + "")
                    + Integer.parseInt(chars[10] + "");
            int b = Integer.parseInt(chars[1] + "")
                    + Integer.parseInt(chars[3] + "")
                    + Integer.parseInt(chars[5] + "")
                    + Integer.parseInt(chars[7] + "")
                    + Integer.parseInt(chars[9] + "")
                    + Integer.parseInt(chars[11] + "");
            String c = (b * 3 + a) + "";
            String d = c.substring(c.length() - 1);
            e1 = 10 - Integer.parseInt(d);
            if (e1 == 10) {
                e1 = 0;
            }
            blue_number = printBarCode;  //打印条码
            if((blue_number + e1).length() != 13){
                Toast.makeText(mContext, "打印条码不正确", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        bule_pages = bule_page_num;

        if (CommentBluePrintUtils.getInstance().getConnectBluePrint()) {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject();
                jsonObject.put("user_name", "姓名:" + printName);
                jsonObject.put("user_sex", "性别:" + printSex);
                jsonObject.put("content", blue_number);//打印条码编码
                jsonObject.put("bule_page", bule_page_num);//一次打印页数
            } catch (JSONException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(new FirstTextEvent(jsonObject.toString(), BlueToothService.EVENTBUS_PRIVATE, 0));

        } else {
            Toast.makeText(mContext, "蓝牙打印机未连接成功!", Toast.LENGTH_SHORT).show();
        }

    }


    private void BluePriterinit() {
        DeterminePriteBluetooth();//循环判断蓝牙状态
        // 注册实时状态查询广播
//        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_DEVICE_REAL_STATUS));
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
//        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_LABEL_RESPONSE));
        /**
         * 链接蓝牙打印机设备
         */
        ConectDevice();
    }

    private void addBarCode() {
        String mData = SPUtils.getInstance(BlueConstants.BULEPRITER_SETTING).getString(BlueConstants.BULEPRITER_NUMBER);
        if (mData.equals("")) {
            mData = "211111111110";
        }
        mData = new BigInteger(mData).add(new BigInteger("1")).toString();
        SPUtils.getInstance(BlueConstants.BULEPRITER_SETTING).put(BlueConstants.BULEPRITER_NUMBER, mData);
    }

    private void ConectDevice() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            Toast.makeText(mContext, "Bluetooth is not supported by the device", Toast.LENGTH_SHORT).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableIntent,
//                        REQUEST_ENABLE_BT);
//                state.setText("蓝牙未开启");
                mConnectBluePrintListener.onConnectStatus(2, "蓝牙未开启");
                bluetoothAdapter.enable();
            } else {
//                state.setText("链接中");
                if (!Utils.isServiceExisted(mContext, "com.example.newblue.server.BlueToothService")) {
                    final Intent intent = new Intent(mContext, BlueToothService.class);
//                    intent.setAction("com.d.bluetooth");
//                    final Intent eintent = new Intent(CreateExplicitFromImplicitIntent.createExplicitFromImplicitIntent(BloodSampleCollection.this, intent));
//                    startService(eintent);
                    mContext.startService(intent);
                }
            }
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


    /**
     * 连续获取连接打印机状态
     */
    private void DeterminePriteBluetooth() {
        mHandler.postDelayed(mRunnable, 500);//循环判断蓝牙状态
    }

    private boolean isConnect = false;
    private void connection() {
        isConnect = true;
        printerServiceConnection = new PrinterServiceConnection();
        Intent intent = new Intent(mContext, GpPrintService.class);
        mContext.bindService(intent, printerServiceConnection, Context.BIND_AUTO_CREATE); // bindService
    }

    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
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
        mContext.registerReceiver(PrinterStatusBroadcastReceiver, filter);
    }

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


}
