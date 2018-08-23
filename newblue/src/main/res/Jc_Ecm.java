package com.cd7d.zk.activity.dev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.cd7d.overwrite.SwitchButton;
import com.cd7d.overwrite.ToastUtil;
import com.cd7d.zk.BluetoothScan;
import com.cd7d.zk.Net;
import com.cd7d.zk.NetCallBacks;
import com.cd7d.zk.R;
import com.cd7d.zk.activity.Header;
import com.cd7d.zk.activity.UsersInfo;
import com.cd7d.zk.db.DBManager;
import com.cd7d.zk.model.Person;
import com.cd7d.zk.model.SPUtilsName;
import com.cd7d.zk.model.StaticReceive;
import com.cd7d.zk.model.UsersMod;
import com.cd7d.zk.utils.CacheActivity;
import com.cd7d.zk.view.CurveChartView;
import com.creative.base.BLUReader;
import com.creative.base.BLUSender;
import com.creative.bluetooth.BluetoothOpertion;
import com.creative.bluetooth.IBluetoothCallBack;
import com.creative.ecg.StatusMsg;

import org.bluetooth.bledemo.BleWrapper;
import org.bluetooth.bledemo.BleWrapperUiCallbacks;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressLint("NewApi")
public class Jc_Ecm extends Activity implements BleWrapperUiCallbacks,
        NetCallBacks {
    private final static String TAG = com.cd7d.zk.activity.dev.Jc_Ecm.class.getSimpleName();
    private Net MyNet = new Net(this, this);
    private int mDeviceID = -1;
    private boolean isStart = false;
    public TextView tvstate, resulttxt;
    private int BL = 0;
    private String mDeviceName = "";
    private String mDeviceAddress = "";
    private String bluetoothPass = "";
    // 程序步进值
    public int nownotfi = 0;

    // private Spinner spinner1;
    public String spinstring = "";
    private BleWrapper mBleWrapper;
    public String PublicMsg = "";
    public String NowDate = "";
    private BluetoothGattService mBTServices;
    private ArrayList<BluetoothGattCharacteristic> mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
    private BluetoothGattCharacteristic mCharacteristicWrite = null;
    private String mLastUpdateTime = "";
    private boolean mNotificationEnabled = false;

    private ImageView touxiang;
    private TextView name;

    private int DTIME = 500;

    private BluetoothOpertion bluetoothOper;
    private BluetoothSocket mbluetoothSocket;
    private String ecgStr = "";
    private DBManager mgr;
    private StringBuffer stringBuffer;
    private SwitchButton remind_switch1;
    private boolean isEnd = true;
    private Header mH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jc_ecm);
        stringBuffer = new StringBuffer();
        mgr = new DBManager(this);
        // spinner1=(Spinner)findViewById(R.id.spinner1);
        // //使用数组作为数据源
        // ArrayList<String> list = new ArrayList<String>();
        // list.add("请选择测量结果");
        // list.add("节律无异常");
        // list.add("疑似心跳稍快 请注意休息");
        // list.add("疑似心跳过快 请注意休息");
        // list.add("疑似阵发性心跳过快");
        // list.add("疑似心跳稍缓 请注意休息");
        // list.add("疑似心跳过缓 请注意休息");
        // list.add("疑似心跳间期缩短");
        // list.add("疑似心跳间期不规则");
        // list.add("疑似心跳稍快伴有心跳间期缩短");
        // list.add("疑似心跳稍缓伴有心跳间期缩短");
        // list.add("疑似心跳稍缓伴有心跳间期不规则");
        // list.add("疑似心跳过缓伴有波形漂移");
        // list.add("疑似心跳间期缩短伴有波形漂移");
        // final ArrayAdapter<String> adapter = new
        // ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
        // list);
        // spinner1.setAdapter(adapter);
        //
        // spinner1.setOnItemSelectedListener(new
        // Spinner.OnItemSelectedListener(){
        // public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
        // long arg3) {
        // // TODO Auto-generated method stub
        // /* 将所选mySpinner 的值带入myTextView 中*/
        // //spinner1.setText("您选择的是："+ adapter.getItem(arg2));
        // spinstring=adapter.getItem(arg2);
        //
        //
        // /* 将mySpinner 显示*/
        // arg0.setVisibility(View.VISIBLE);
        // }
        // public void onNothingSelected(AdapterView<?> arg0) {
        // // TODO Auto-generated method stub
        // //spinner1.setText("NONE");
        // arg0.setVisibility(View.VISIBLE);
        // }
        // });

//        Header.SetTitle(this, "心电仪",true);
        Header.SetTitle(this, "心电仪");
//        mH = new Header();
//        mH.setOnFinishClickListener(new Header.onFinishClickListener() {
//            @Override
//            public void onClickFinish() {
//              onBackPressed();
//            }
//        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 不锁屏
        tvstate = (TextView) findViewById(R.id.tvstate);
        resulttxt = (TextView) findViewById(R.id.resulttxt);

//        ImageView imageViewpro = (ImageView) this.findViewById(R.id.imageView1);
//        imageViewpro.setImageResource(R.drawable.proecm);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NowDate = format.format(new Date());

        dc = (CurveChartView) findViewById(R.id.ccv);
        dc.setCurveStatic(false);
        dc.setCurveCount(1);
        dc.setMaxHarizonLineCount(10);
        dc.setCurveColor(new int[]{Color.RED});
        dc.setCalibrationOn(true);
        dc.setDataScope(18, 26);
        // this.findViewById(R.id.outusers).setOnClickListener(
        // new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        //
        // Intent intent = new Intent(Jc_Ecm.this,
        // com.cd7d.zk.activity.Users.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(intent);
        // finish();
        // }
        // });
        remind_switch1 = (SwitchButton) findViewById(R.id.remind_switch1);
        String blue_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECM_ADDRESS);
        if (!blue_address.equals("")) {
            remind_switch1.setChecked(true);
        }
        remind_switch1.setOnCheckedChangeListener(switch_listener);
        this.findViewById(R.id.utx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.cd7d.zk.activity.dev.Jc_Ecm.this, UsersInfo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        bluetoothOper = new BluetoothOpertion(this, new myBluetoothCallBack());

        if (!bluetoothOper.isOpen()) {
            bluetoothOper.open();
        }
    }

    // /*保存数据*/
    // public void savevalue(View view)
    // {
    // // Toast.makeText(Jc_Ecm.this, "您选择的是："+spinstring, Toast.LENGTH_LONG)
    // // .show();
    // if(!spinstring.equals("")&&!spinstring.equals("请选择测量结果")){
    // String json = "action=doecm&data={\"userid\":\""
    // + UsersMod.get_id() + "\",\"note\":\""
    // + spinstring + "\",\"createdate\":\"" + NowDate
    // + "\",\"ecg\":\"0\"}";
    // HttpPost(1, json);
    // }else
    // {
    // Toast.makeText(Jc_Ecm.this, "请选择测量结果！", Toast.LENGTH_LONG) .show();
    // }
    // }

    Handler timehandler = new Handler();
    Runnable timerunnable = new Runnable() {
        @Override
        public void run() {

            ConBlue();

        }
    };


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
        /*if(isEnd) {
            super.onBackPressed();
        }else{
            ToastUtil.showShortToast("正在检测中，请勿退出");
        }*/
//    }

    private void ConBlue() {

        bluetoothOper.discovery();
        Log.e(TAG, "ConBlue:  识别检测设备 识别检测设备 识别检测设备");
        // if (mDeviceAddress.length() < 2 || BluetoothScan.IsStart) {
        //
        // // TODO 识别检测设备
        // for (int i = 0; i < Conver.LeDevices.size(); i++) {
        // Log.e(TAG,
        // Conver.LeDevices.get(i).Type
        // + "="
        // + Conver.LeDevices.get(i).device.getAddress()
        // + "+++"
        // + Conver.LeDevices.get(i).isOnline()
        // + "|||"
        // + (Conver.LeDevices.get(i).Type == "ecm"
        // && Conver.LeDevices.get(i).isOnline() && Conver.LeDevices
        // .get(i).device != null));
        //
        // if (Conver.LeDevices.get(i).Type == "ecm"
        // && Conver.LeDevices.get(i).isOnline()
        // && Conver.LeDevices.get(i).device != null) {
        //
        // mDeviceID = i;
        // mDeviceAddress = Conver.LeDevices.get(i).device
        // .getAddress();
        // mDeviceName = Conver.LeDevices.get(i).device.getName();
        // Log.e(TAG, "连接：" + mDeviceAddress);
        // mBleWrapper.connect(mDeviceAddress);
        // break;
        // }
        //
        // }
        //
        // }
        // 需要长时等待连接
        timehandler.postDelayed(timerunnable, 10000);

    }

    // TODO 待机状态返回时激活服务
    @Override
    protected void onResume() {
        super.onResume();
        // Initializes list view adapter.
        BluetoothScan.IsAutoJump = false;
        // BluetoothScan.Start();
        //
        // if (mBleWrapper == null)
        // mBleWrapper = new BleWrapper(this, this);
        //
        // if (mBleWrapper.initialize() == false) {
        // // finish();
        // BluetoothScan.Stop();
        // }

        timehandler.postDelayed(timerunnable, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // BluetoothScan.Stop();
        //
        // mBleWrapper.stopMonitoringRssiValue();
        // mBleWrapper.diconnect();
        // mBleWrapper.close();
        //
        // timehandler.removeCallbacks(timerunnable);

        bluetoothOper.stopDiscovery();

        // BluetoothScan.Stop();
        //
        // mBleWrapper.stopMonitoringRssiValue();
        // mBleWrapper.diconnect();
        // mBleWrapper.close();

        timehandler.removeCallbacks(timerunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BluetoothScan.IsAutoJump = true;

        StaticReceive.StopReceive();

        if (mbluetoothSocket != null) {
            bluetoothOper.stopDiscovery();
            bluetoothOper.disConnect(mbluetoothSocket);
            mbluetoothSocket = null;
        }
        handlerdoecm.removeCallbacks(runnable);
        mHandler.removeCallbacksAndMessages(null);
        CacheActivity.getInstance().removeActivity(this);
    }

    private String HHJ = "";
    private String STR = "";

    // 显示回传数据
    private void displayData(String data) {
        if (data != null) {
            Log.e("回传数据test", "data=" + data);

            if (bluetoothPass.length() > 0) {
                isStart = true;
                if (data.contains("55AA65")) {
                    STR += data.substring(data.indexOf("55AA65") + 18);
                } else {
                    STR += data;
                }

                if (STR.length() > 100) {
                    STR = STR.substring(0, STR.length() - 2);
                    HHJ += STR;
//					stringBuffer.append();
                    // textViewweight.setText(STR);
                    if (HHJ.length() > 4182) {
                        setview(STR);
                    }
                    STR = "";
                }

                if (data.contains("55AA66")) {

                    int len = HHJ.length();
                    Log.e("test", "bbdata=" + HHJ.length());
                    if (len > 4488) {
                        HHJ = HHJ.substring(4488);

                        int t = 0;
                        String json = "action=doecm&data={\"userid\":\""
                                + UsersMod.get_id() + "\",\"createdate\":\""
                                + NowDate + "\",\"ecg\":\"";

                        while (true) {

                            if (t < HHJ.length() && HHJ.length() > 3) {
                                json += String
                                        .format("%4.2f",
                                                (float) Integer.parseInt(
                                                        HHJ.substring(t, t + 3),
                                                        16) / 100)
                                        + ",";
                                t += 3;
                            } else {
                                break;
                            }
                        }
                        json = json.substring(0, json.length() - 1);

                        json += "\"}";
                        Log.e("test", "ccdata=" + HHJ.length());

                        HttpPost(1, json);
                        HHJ = "";
                        STR = "";

                    }
                }
            } else {
                // TODO 科瑞康心电

                isStart = true;

                if (data.contains("A5DD0F")) {

                    int vHr = 0;
                    int vNote = 0;
                    if (data.length() > 36) {
                        vHr = Integer.parseInt(data.substring(32, 34), 16);
                        vNote = Integer.parseInt(data.substring(34, 36), 16);
                    }
                    int len = HHJ.length();
                    resulttxt.setText("心率:" + String.valueOf(vHr) + ";"
                            + GetEcmNote(vNote));
                    // if(vNote!=0&&vNote!=11&&vNote!=16){
                    PublicMsg = "心率:" + String.valueOf(vHr) + ";"
                            + GetEcmNote(vNote);
                    if (len > 9000) {
                        HHJ = HHJ.substring(9000);

                        int t = 0;
                        String json = "action=doecm&data={\"userid\":\""
                                + UsersMod.get_id() + "\",\"note\":\""
                                + PublicMsg + "\",\"heart_rate\":\""
                                + String.valueOf(vHr)
                                + "\",\"createdate\":\"" + NowDate
                                + "\",\"ecg\":\"";

                        while (true) {

                            if (t < HHJ.length() && HHJ.length() > 4) {
                                json += String
                                        .format("%4.2f",
                                                (float) Integer.parseInt(
                                                        HHJ.substring(t + 2,
                                                                t + 4)
                                                                + HHJ.substring(
                                                                t,
                                                                t + 2),
                                                        16) / 100)
                                        + ",";
                                t += 4;
                            } else {
                                break;
                            }
                        }
                        json = json.substring(0, json.length() - 1);

                        json += "\"}";
                        Log.e("test", "ccdata=" + HHJ.length());
                        String message = String.valueOf(vHr) + ";" + GetEcmNote(vNote);
                        Log.e("ascda22", message + "==");
                        Person person = new Person();
                        person.UIDCARD = UsersMod.get_uIDcard();
                        person.ecm = message;
                        mgr.updateUserInfo2(person);
                        final String[] mValue = message.toString().split(";");
                        Log.e("存入的心电2", UsersMod.get_ecm() + "==");
                        Log.e("心率2", mValue[0] + "==");
                        Log.e("心电检查结果2", mValue[1] + "==");
                        HttpPost(1, json);
                        HHJ = "";
                        STR = "";

                    }
                }

                if (data.contains("A5DD38")) {

                    STR = data.substring(data.indexOf("A5DD38") + 18);
                } else {
                    STR += data;
                }

                if (STR.length() > 100) {
                    STR = STR.substring(0, 100);
                    Log.e("test", "STR=" + STR);
                    HHJ += STR;

                    // textViewweight.setText(STR);
                    if (HHJ.length() > 9000) {
                        setviewLH(STR);
                    }
                    STR = "";
                }
            }
        }
    }

    private String GetEcmNote(int vNote) {
        // TODO Auto-generated method stub
        String revar = "";
        switch (vNote) {
            case 0:
                revar = "节律无异常";
                break;
            case 1:
                revar = "疑似心跳稍快 请注意休息";
                break;
            case 2:
                revar = "疑似心跳过快 请注意休息";
                break;
            case 3:
                revar = "疑似阵发性心跳过快 请咨询医生";
                break;
            case 4:
                revar = "疑似心跳稍缓 请注意休息";
                break;
            case 5:
                revar = "疑似心跳过缓 请注意休息";
                break;
            case 6:
                revar = "疑似心跳间期缩短 请咨询医生";
                break;
            case 7:
                revar = "疑似心跳间期不规则 请咨询医生";
                break;
            case 8:
                revar = "疑似心跳稍快伴有心跳间期缩短 请咨询医生";
                break;
            case 9:
                revar = "疑似心跳稍缓伴有心跳间期缩短 请咨询医生";
                break;
            case 10:
                revar = "疑似心跳稍缓伴有心跳间期不规则 请咨询医生";
                break;
            case 11:
                revar = "波形有漂移 请重新测量";
                break;
            case 12:
                revar = "疑似心跳过快伴有波形漂移 请咨询医生";
                break;
            case 13:
                revar = "疑似心跳过缓伴有波形漂移 请咨询医生";
                break;
            case 14:
                revar = "疑似心跳间期缩短伴有波形漂移 请咨询医生";
                break;
            case 15:
                revar = "疑似心跳间期不规则伴有波形漂移 请咨询医生";
                break;
            case 16:
                revar = "信号较差，请重新测量";
                break;
        }
        return revar;
    }

    private CurveChartView dc = null;

    private void setview(String str) {

        int len = str.length();
        int t = 0;

        while (true) {
            if (t + 3 < len) {
                String json = String
                        .format("%.2f",
                                (float) Integer.parseInt(
                                        str.substring(t, t + 3), 16) / 100);

                dc.appendData(new float[]{Float.valueOf(json)});

                t += 3;
            } else {
                break;
            }
        }

    }

    private void setviewLH(String str) {

        int len = str.length();
        int t = 0;

        while (true) {
            if (t + 4 < len) {
                String json = String.format(
                        "%4.2f",
                        (float) Integer.parseInt(str.substring(t + 2, t + 4)
                                + str.substring(t, t + 2), 16) / 100);

                dc.appendData(new float[]{Float.valueOf(json)});

                t += 4;
            } else {
                break;
            }
        }

    }

    public void HttpPost(final int t, final String json) {
        MyNet.Post(json, t);
    }

    private Handler handlerdoecm = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (mBleWrapper != null && mCharacteristicWrite != null) {
                if (BL == 0) {
                    Log.e(TAG, BL + "");
                    BL = 1;

                    mBleWrapper.WriteHexString(mCharacteristicWrite,
                            "55AA65000000" + ((65 + 00 + 00 + 00) % 256));
                    handlerdoecm.postDelayed(this, DTIME);
                } else if (BL == 1) {

                    if (isStart) {
                        Log.e(TAG, BL + "++");
                        BL = 2;
                        handlerdoecm.postDelayed(this, 36000);
                    } else {
                        Log.e(TAG, BL + "--");
                        BL = 0;
                        handlerdoecm.postDelayed(this, DTIME);
                    }
                } else if (BL == 2) {
                    Log.e(TAG, BL + "");
                    BL = -1;

                    mBleWrapper.WriteHexString(mCharacteristicWrite,
                            "55AA66000000" + ((66 + 00 + 00 + 00) % 256));

                    handlerdoecm.removeCallbacks(runnable);

                }
            }
        }
    };

    @Override
    public void uiDeviceFound(BluetoothDevice device, int rssi, byte[] record) {

    }

    /**
     * 连接到设备成功
     */
    @Override
    public void uiDeviceConnected(final BluetoothGatt gatt,
                                  final BluetoothDevice device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvstate.setText(R.string.connected);
                Log.e("test", "连接到设备成功");

                BluetoothScan.Stop();
                // invalidateOptionsMenu();
            }
        });
    }

    /**
     * 设备断开
     */
    @Override
    public void uiDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvstate.setText(R.string.disconnected);
                Log.e("test", "设备断开");

                BluetoothScan.Start();
                mDeviceAddress = "";
                // mServicesListAdapter.clearList();
                // mCharacteristicsListAdapter.clearList();
                // mCharDetailsAdapter.clearCharacteristic();
            }
        });
    }

    /**
     * 得到设备所有的Services结果
     */
    @Override
    public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device,
                                    List<BluetoothGattService> services) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                nownotfi = 0;
                mCharacteristics.clear();
                for (BluetoothGattService service : mBleWrapper
                        .getCachedServices()) {
                    if (mBTServices == null
                            || mBTServices.equals(service) == false) {

                        String uuid = service.getUuid().toString();

                        // TODO 在这里设置服务和特征

                        if (service != null
                                && uuid.contains("0000ff00-0000-1000-8000-00805f9b34fb")) {
                            mBTServices = service;
                            mBleWrapper
                                    .getCharacteristicsForService(mBTServices);
                            setDevUUID("0000ff01-0000-1000-8000-00805f9b34fb",
                                    16);
                            // setDevUUID("0000cd02-0000-1000-8000-00805f9b34fb",
                            // 16);
                            // setDevUUID("0000cd03-0000-1000-8000-00805f9b34fb",
                            // 16);
                            // setDevUUID("0000cd04-0000-1000-8000-00805f9b34fb",
                            // 16);
                            setDevUUID("0000ff02-0000-1000-8000-00805f9b34fb",
                                    8);
                            bluetoothPass = "55AA65000000";

                            SetNotfi();

                            handlerdoecm.removeCallbacks(runnable);
                            handlerdoecm.postDelayed(runnable, DTIME);

                            break;
                        }

                        if (service != null
                                && uuid.contains("0000fff0-0000-1000-8000-00805f9b34fb")) {
                            mBTServices = service;
                            mBleWrapper
                                    .getCharacteristicsForService(mBTServices);
                            setDevUUID("0000fff1-0000-1000-8000-00805f9b34fb",
                                    16);
                            // setDevUUID("0000cd02-0000-1000-8000-00805f9b34fb",
                            // 16);
                            // setDevUUID("0000cd03-0000-1000-8000-00805f9b34fb",
                            // 16);
                            // setDevUUID("0000cd04-0000-1000-8000-00805f9b34fb",
                            // 16);
                            setDevUUID("0000fff2-0000-1000-8000-00805f9b34fb",
                                    8);
                            bluetoothPass = "";

                            SetNotfi();

                            break;
                        }

                        System.out.println("uuid:" + uuid);
                    }
                }

            }

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
        });
    }

    /**
     * 扫描Service完成后得到Service所有的Characteristic结果
     */
    @Override
    public void uiCharacteristicForService(BluetoothGatt gatt,
                                           BluetoothDevice device, final BluetoothGattService service,
                                           final List<BluetoothGattCharacteristic> chars) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String uiCharacteristicForServicetem = "";
                for (BluetoothGattCharacteristic ch : chars) {
                    if (mCharacteristics.contains(ch) == false) {
                        uiCharacteristicForServicetem = "";
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_BROADCAST) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_BROADCAST]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_READ]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_WRITE_NO_RESPONSE]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_WRITE]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_NOTIFY]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_INDICATE]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_SIGNED_WRITE]";
                        }
                        if ((ch.getProperties() & BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS) != 0) {
                            uiCharacteristicForServicetem += "[PROPERTY_EXTENDED_PROPS]";
                        }

                        Log.e("ch.getUuid():", ch.getUuid().toString() + ":"
                                + uiCharacteristicForServicetem);

                    }
                }
            }
        });
    }

    @Override
    public void uiCharacteristicsDetails(BluetoothGatt gatt,
                                         BluetoothDevice device, BluetoothGattService service,
                                         BluetoothGattCharacteristic characteristic) {

        mLastUpdateTime = "-";
        mNotificationEnabled = false;
    }

    /**
     * 设备发送过来的信息
     */
    @Override
    public void uiNewValueForCharacteristic(final BluetoothGatt gatt,
                                            final BluetoothDevice device, final BluetoothGattService service,
                                            final BluetoothGattCharacteristic characteristic,
                                            final String strValue, final int intValue, final byte[] rawValue,
                                            final String timestamp) {
        if (mCharacteristics.size() <= 0)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newValueForCharacteristic(characteristic, strValue, intValue,
                        rawValue, timestamp);

            }
        });
    }

    @Override
    public void uiGotNotification(final BluetoothGatt gatt,
                                  final BluetoothDevice device, final BluetoothGattService service,
                                  final BluetoothGattCharacteristic ch) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // at this moment we only need to send this "signal" do
                // characteristic's details view
                setNotificationEnabledForService(ch);
            }
        });
    }

    @Override
    public void uiSuccessfulWrite(BluetoothGatt gatt, BluetoothDevice device,
                                  BluetoothGattService service, BluetoothGattCharacteristic ch,
                                  final String description) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Log.e("uiSuccessfulWrite", "Writing to " + description
                // + " was finished successfully!");
            }
        });
    }

    @Override
    public void uiFailedWrite(BluetoothGatt gatt, BluetoothDevice device,
                              BluetoothGattService service, BluetoothGattCharacteristic ch,
                              final String description) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Log.e("uiFailedWrite", "Writing to " + description +
                // " FAILED!");
            }
        });
    }

    @Override
    public void uiNewRssiAvailable(BluetoothGatt gatt, BluetoothDevice device,
                                   final int rssi) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    public void newValueForCharacteristic(final BluetoothGattCharacteristic ch,
                                          final String strVal, final int intVal, final byte[] rawValue,
                                          final String timestamp) {
        if (mCharacteristics.contains(ch) == false) {
            return;
        }

        displayData(strVal);

        mLastUpdateTime = timestamp;
        if (mLastUpdateTime == null)
            mLastUpdateTime = "";
    }

    public void setNotificationEnabledForService(
            final BluetoothGattCharacteristic ch) {
        if (mCharacteristics.contains(ch) == false
                || (mNotificationEnabled == true))
            return;
        mNotificationEnabled = true;
    }

    @Override
    public void uiNotificationForCharacteristicSuccess(UUID uuid, int status) {

        SetNotfi();
    }

    public void SetNotfi() {
        if (mCharacteristics.size() > 0 && nownotfi <= mCharacteristics.size()) {
            nownotfi = nownotfi + 1;
            if (nownotfi - 1 < mCharacteristics.size()) {
                mBleWrapper.setNotificationForCharacteristic(
                        mCharacteristics.get(nownotfi - 1), true);
                Log.e(TAG, "监听设备");
            } else if (nownotfi - 1 == mCharacteristics.size()) {
                // TODO 如果有验证码，在这里设置
                if (bluetoothPass.length() > 0) {

                    mBleWrapper.WriteHexString(mCharacteristicWrite,
                            bluetoothPass);
                    Log.e(TAG, "发送连接码到设备");
                }
            }
        }
    }

    @Override
    public void uiNetReturnData(String tUrl, String tData, int tSign) {
        // TODO Auto-generated method stub
        switch (tSign) {
            case 1:
                try {
                    // {"result":"success","msg":"","Uid":"17"}
                    JSONObject jsonObject = new JSONObject(tData);
                    String res = jsonObject.getString("result");
                    System.out.println("res:" + res);
                    if (res.equals("success")) {
                        Toast.makeText(com.cd7d.zk.activity.dev.Jc_Ecm.this, "同步成功!", Toast.LENGTH_LONG)
                                .show();

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                if (PublicMsg.length() > 0) {

                    MyNet.Post(Net.SERVLET_EXAM_ADD, "msgpush",
                            "type=msgpush&data={\"userid\":" + UsersMod.get_id()
                                    + ",\"title\":\"心电图检测提示\",\"content\":\""
                                    + PublicMsg + "\",\"createdate\":\"" + NowDate
                                    + "\",\"src\":\"\",\"state\":9}", 2);

                }

                // // 首次检测完成自动延时退出
                // if (Conver.LeDevices != null&&Conver.LeDevices.size()>0
                // && mDeviceID < Conver.LeDevices.size()
                // && Conver.LeDevices.get(mDeviceID).isUsed == 1) {
                // Conver.LeDevices.get(mDeviceID).isUsed++;
                // Conver.LeDevices.get(mDeviceID).Des += "";
                // new Handler().postDelayed(new Runnable(){
                // public void run() {
                // finish();
                // }
                // }, Conver.IsAutoCheck);
                // }
                break;
            default:
                break;
        }
    }

    private class myBluetoothCallBack implements IBluetoothCallBack {

        @Override
        public void OnFindDevice(BluetoothDevice arg0) {
            // TODO Auto-generated method stub

            Log.e("-sacdqaswc---", "---->OnFindDevice:" + arg0.getName());
            Log.e("-心电地址---", "---->OnFindAddress:" + arg0.getAddress());
            String bule_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECM_ADDRESS);
            Log.e("存入的心电地址", bule_address + "==");
            if ((arg0.getName() + "").equals("PC80B")) {
                mDeviceAddress = arg0.getAddress();
                if (!bule_address.equals("")) {
                    if ((arg0.getAddress()).equals(bule_address)) {
                        bluetoothOper.stopDiscovery();
                        bluetoothOper.connect(arg0);
                    }
                } else {
                    bluetoothOper.stopDiscovery();
                    bluetoothOper.connect(arg0);
                }
            }
        }

        @Override
        public void OnException(int arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnException:" + arg0);
        }

        @Override
        public void OnDiscoveryCompleted(List<BluetoothDevice> arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnDiscoveryCompleted:");
        }

        @Override
        public void OnConnected(BluetoothSocket arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnConnected:");
            mbluetoothSocket = arg0;
            try {

                StaticReceive.startReceive(com.cd7d.zk.activity.dev.Jc_Ecm.this, arg0.getRemoteDevice()
                                .getName(), new BLUReader(arg0.getInputStream()),
                        new BLUSender(arg0.getOutputStream()), mHandler);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void OnConnectFail(String arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnConnectFail:");
        }
    }

    ;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticReceive.MSG_DATA_BATTERY: {
                    Log.e("--00--", "---->MSG_DATA_BATTERY:" + msg.arg1);
                    tvstate.setText("已连接，请测量");

                    timehandler.removeCallbacks(timerunnable);
                }
                break;
                case StaticReceive.BATTERY_ZERO: {// 电池电量为0时的消息
                    mHandler.sendEmptyMessageDelayed(StaticReceive.BATTERY_ZERO,
                            500);
                }
                break;
                case StaticReceive.MSG_DATA_ECG_STATUS_CH: {
                    switch (msg.arg1) {
                        case StatusMsg.FILE_TRANSMIT_START: {// 接收文件
                            Log.e("----", "---->FILE_TRANSMIT_START:正在接收文件");
                        }
                        break;
                        case StatusMsg.FILE_TRANSMIT_SUCCESS: {
                            Log.e("----", "---->FILE_TRANSMIT_SUCCESS:接收完成");
                        }
                        break;
                        case StatusMsg.FILE_TRANSMIT_ERROR: {
                            Log.e("----", "---->FILE_TRANSMIT_ERROR:接收文件出错");
                        }
                        break;
                        case StaticReceive.MSG_DATA_TIMEOUT: {
                            Log.e("----", "---->MSG_DATA_TIMEOUT:接收超时");
                        }
                        break;
                        case 4: {// 准备阶段波形
                            Bundle data = msg.getData();
                            if (data.getBoolean("bLeadoff")) {
                                Log.e("----", "---->准备阶段波形:导联脱落");
                            }
                        }
                        break;
                        case 5: {// 实时测量波形
                            if(isEnd) {
                                isEnd = false;
                            }
                            Bundle data = msg.getData();
                            if (data.getBoolean("bLeadoff")) {
                                Log.e("----", "---->实时测量波形:接收超时");
                            }
                            data.getInt("nTransMode");

                            Log.e("--11--",
                                    "11---->data:" + data.getStringArrayList("nData"));
                            // setHR(data.getInt("nHR"));
                            // setGain(data.getInt("nGain"));
                            Float[] Fary = data.getStringArrayList("nData").toArray(
                                    new Float[0]);

                            float[] fary = new float[Fary.length];

                            for (int i = 0; i < Fary.length; i++) {
                                fary[i] = Fary[i].floatValue();
//						ecgStr += Fary[i].floatValue() + ",";
                                stringBuffer.append(Fary[i].floatValue() + ",");
                                dc.appendData(new float[]{Fary[i].floatValue()});
                            }
                            ecgStr = stringBuffer.toString();
                        }
                        break;
                        case 6: {// 测量结果
                            isEnd = true;
                            Bundle data = msg.getData();
                            int nTransMode = data.getInt("nTransMode");
                            String time = data.getString("time");
                            if (nTransMode == StatusMsg.TRANSMIT_MODE_QUICK
                                    && time != null) {

                                Log.e("----", "---->测量结果:" + data.getString("nResult"));
                                // data.getInt("nResult");
                            }
                            // setHR(data.getInt("nHR"));

                            if (ecgStr.length() > 0) {
                                ecgStr = ecgStr.substring(0, ecgStr.length() - 1);
                                String json = "action=doecm&data={\"userid\":\""
                                        + UsersMod.get_id() + "\",\"note\":\""
                                        + data.getString("nResult")
                                        + "\",\"heart_rate\":\""
                                        + data.getInt("nHR")
                                        + "\",\"createdate\":\"" + NowDate
                                        + "\",\"ecg\":\"" + ecgStr + "\"}";

                                HttpPost(1, json);
                                stringBuffer.delete(0, stringBuffer.length());
                            }

                            resulttxt.setText("心率:" + data.getInt("nHR") + ";"
                                    + data.getString("nResult"));
                            String message = data.getInt("nHR") + ";" + data.getString("nResult");
                            Log.e("ascda", message + "==");
                            Person person = new Person();
                            person.UIDCARD = UsersMod.get_uIDcard();
                            person.ecm = message;
                            mgr.updateUserInfo2(person);
                            final String[] mValue = message.toString().split(";");
                            Log.e("存入的心电", UsersMod.get_ecm() + "==");
                            Log.e("心率", mValue[0] + "==");
                            Log.e("心电检查结果", mValue[1] + "==");

                        }
                        break;
                        case 7: {// 传输设置
                            int nSmoothingMode = msg.arg2;// 滤波模式
                            int nTransMode = (Integer) msg.obj;// 传输模式
                            if (nTransMode == StatusMsg.TRANSMIT_MODE_FILE) {
                                Log.e("----", "---->传输设置:正在接收文件");

                            } else if (nTransMode == StatusMsg.TRANSMIT_MODE_CONTINUOUS) {
                                // setSmooth(nSmoothingMode ==
                                // StatusMsg.SMOOTHMODE_ENHANCE);
                            }
                        }
                        break;
                    }
                }
                break;
                case StaticReceive.MSG_DATA_PULSE: {
                    // showPulse(true);
                }
                break;
                case StaticReceive.RECEIVEMSG_PULSE_OFF: {
                    // showPulse(false);
                }
                break;
            }
        }

    };
    CompoundButton.OnCheckedChangeListener switch_listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton view, boolean isChecked) {
            if (isChecked) {
                if (mDeviceAddress.length() > 5) {
                    SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_ECM_ADDRESS, mDeviceAddress);
                } else {
                    ToastUtil.showShortToast("蓝牙地址为空!");
                    remind_switch1.setChecked(false);
                }
            } else {
                SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_ECM_ADDRESS, "");
            }
        }
    };
}
