package com.example.bluetoothdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluetoothdemo.anothers.BluetoothScan;
import com.example.bluetoothdemo.anothers.CommenBlueUtils;
import com.example.bluetoothdemo.anothers.Conver;
import com.example.bluetoothdemo.anothers.deviceBox;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConnectBlueToothListener {
    private Button btn_open, btn_startSao, btn_connect;
    private BluetoothAdapter mBlueAdapter;
    public static final int REQUEST_ENABLE_BT = 2;
    private Handler mHandler = new Handler();
    private BlueGetListener mBlueGetListener;
    private BluetoothGatt mBluetoothGatt;
    private String mAddress;
    private Disposable mMSubscribe,mTime1,mTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Conver.LeDevices = new ArrayList<>();
        BluetoothScan.getInstance().init(this);
        initView();
        initBlue();
        initListener();
    }

    private void initListener() {
        btn_open.setOnClickListener(this);
        btn_startSao.setOnClickListener(this);
        btn_connect.setOnClickListener(this);
    }


    private void initBlue() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            mBlueAdapter = bluetoothManager.getAdapter();
        }
    }

    private void initView() {
        btn_open = findViewById(R.id.btn_open);
        btn_startSao = findViewById(R.id.btn_startSao);
        btn_connect = findViewById(R.id.btn_connect);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                judgeIsOpen();
                break;
            case R.id.btn_startSao:
               /* if(mMSubscribe != null){
                    mMSubscribe.dispose();
                    mMSubscribe = null;
                }
                if(mTime1 != null){
                    mTime1.dispose();
                }
                if(mTime2 != null){
                    mTime2.dispose();
                }
                if(mBlueAdapter.isDiscovering()){
                    mBlueAdapter.cancelDiscovery();
                }
                mBlueAdapter.stopLeScan(mBlueCallBackListener);  //停止蓝牙扫描

                mMSubscribe = Observable.interval(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                startSao(true);
                            }
                        });*/
                CommenBlueUtils.getInstance().connectBlueTooth(this,"oxi",this);
                break;
            case R.id.btn_connect:

                if (deviceBox.mDevices != null) {
                    deviceBox.mDevices.clear();
                }

                mBlueAdapter.stopLeScan(mBlueCallBackListener);  //停止蓝牙扫描
                mMSubscribe.dispose();
                mTime1.dispose();
                mTime2.dispose();
                if(mBlueGetListener != null) {
                    mBlueGetListener.stop();
                }
                try {
                    Thread.sleep(500);
                    mAddress = "";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }


    /**
     * 开始连接
     */
    private void startConnect() {
        if (mDevice == null) {
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    private boolean isSao = false;
    private BluetoothDevice mDevice = null;
    private BlueCallBackListener mBlueCallBackListener = new BlueCallBackListener(new BlueCallBackListener.onSaoSuccess() {
        @Override
        public void onCallBack(BluetoothDevice d) {
            Toast.makeText(MainActivity.this, "扫描成功", Toast.LENGTH_SHORT).show();
            mDevice = d;
            mBlueAdapter.stopLeScan(mBlueCallBackListener);  //停止蓝牙扫描
            mTime1.dispose();
            mMSubscribe.dispose();
        }
    });


    /**
     * 是否打开蓝牙扫描
     *
     * @param enable
     */
    private void startSao(boolean enable) {
        Log.i("dfdsafgsdf","startSao");
        if (mBlueAdapter == null) {
            return;
        }
        Log.i("dfdsafgsdf","startSao111");
        if (enable) {
            if (!mBlueAdapter.isEnabled()) {
                mBlueAdapter.enable();
            }
            if (!mBlueAdapter.isDiscovering()) {

               mTime1 =  Observable.timer(2000,TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                mBlueAdapter.stopLeScan(mBlueCallBackListener);
                            }
                        });

                //蓝牙扫描比较消耗，所以不能一直开启，所以必须设置一段时间后关闭蓝牙，所以这里选择在2s后关闭
                //但是task这个handler则是4s一次开启这个，相当于2s扫描一次
                mBlueAdapter.startLeScan(mBlueCallBackListener);  //扫描全部蓝牙设备
            }
        } else {
            mBlueAdapter.stopLeScan(mBlueCallBackListener);  //停止蓝牙扫描
        }


        mTime2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return mAddress == null || mAddress.length() < 2;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mDevice == null) {
                            return;
                        }
                        //F0:C7:7F:BA:0A:06
                        mAddress = mDevice.getAddress();
                        Log.i("dfdsafgsdf", "maddress--->" + mAddress);
                        BluetoothDevice remoteDevice = mBlueAdapter.getRemoteDevice(mAddress);
                        mBlueGetListener = new BlueGetListener(remoteDevice, MainActivity.this);
                        mBlueGetListener.connect();
                    }
                });
    }

    /**
     * 判断有没有打开蓝牙
     */
    private void judgeIsOpen() {
        if (mBlueAdapter == null || mBlueAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (RESULT_OK == resultCode) {
                Toast.makeText(this, "已打开", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "打开失败", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onConnectSuccess(String message) {
        Log.i("bleWrapper", "---》--->" + "连接成功");
    }

    @Override
    public void onInterceptConnect(String message) {
        Log.i("bleWrapper", "---》--->" + "连接失败");
    }

    @Override
    public void onDataFromBlue(String type, String message) {
        Log.i("bleWrapper", "---?User拿到的数据type---》" + type);
        Log.i("bleWrapper", "---?User拿到的数据---》" + message);
        displayData(message);
    }


    private String str;
    private String pvss;

    // 显示回传数据
    private void displayData(String data) {
        Log.i("sjkljklsjadkll", "数据---》" + data);

        // 11-26 18:54:47.796: D/test(8170): data=AF6A725A0E440088

        str += data;

        if (data != null && !data.equals("")) {

            char[] chars = data.toCharArray();
            String hexStr = new String();
            // 体温
            //家康体温枪
            if (str.length() > 15 && str.trim().contains("AF6A")) {
                str = str.trim().substring(str.indexOf("AF6A"));
                if (str.length() > 15) {

                    chars = str.toCharArray();

                    hexStr = "" + chars[8] + chars[9] + chars[10] + chars[11];

                    Log.d("test", "parseInt=" + Integer.parseInt(hexStr, 16));

                    float myweight = Integer.parseInt(hexStr, 16);

                    DecimalFormat formater = new DecimalFormat("#0.#");
                    formater.setRoundingMode(RoundingMode.FLOOR);
                    if ((myweight / 100) >= 29 && (myweight / 100) <= 46) {
                        pvss = formater.format(myweight / 100);
                        Log.i("bleWrapper", "---数据?---?>" + pvss + "" + "");
                        //mTemDialog.setEt1Text(pvss + "");

                       /* String json = "action=dotem&data={\"userid\":\""
                                + UsersMod.get_id() + "\",\"temperature\":\""
                                + pvss + "\",\"source\":0}";*/

                        //HttpPost(1, json);
                    } else {
                        //ToastUtil.showShortToast("测量温度不正常,请重新测量!");
                    }
                }
                str = "";
            }

        }
    }


}
