package com.example.newblue.jumppart;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creative.base.BLUReader;
import com.creative.base.BLUSender;
import com.creative.bluetooth.BluetoothOpertion;
import com.creative.bluetooth.IBluetoothCallBack;
import com.creative.ecg.StatusMsg;
import com.example.newblue.BlueConstants;
import com.example.newblue.R;
import com.example.newblue.view.CurveChartView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NewApi")
public class Jc_Ecm extends AppCompatActivity {
    private String mDeviceAddress = "";
    public String NowDate = "";
    private BluetoothOpertion bluetoothOper;
    private BluetoothSocket mbluetoothSocket;
    private String ecgStr = "";
    private StringBuffer stringBuffer;
    private boolean isEnd = true;
    //连接状态
    @BindView(R.id.tvstate)
    TextView tvstate;
    //监测结果
    @BindView(R.id.resulttxt)
    TextView resulttxt;
    @BindView(R.id.follow_save)
    RelativeLayout follow_save;

    private StringBuilder endSb = new StringBuilder();//心电的xml
    private String endHeartRate = ""; //心率
    private String endReulst = ""; //心电结果


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jc_ecm);
        ButterKnife.bind(this);
        stringBuffer = new StringBuffer();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 不锁屏
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NowDate = format.format(new Date());

        follow_save.setVisibility(View.GONE);
        dc = findViewById(R.id.ccv);
        dc.setCurveStatic(false);
        dc.setCurveCount(1);
        dc.setMaxHarizonLineCount(10);
        dc.setCurveColor(new int[]{Color.RED});
        dc.setCalibrationOn(true);
        dc.setDataScope(18, 26);

        //是否已经绑定设备
        /*remind_switch1 = (SwitchButton) findViewById(R.id.remind_switch1);
        String blue_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECM_ADDRESS);
        if (!blue_address.equals("")) {
            remind_switch1.setChecked(true);
        }
        remind_switch1.setOnCheckedChangeListener(switch_listener);*/


        bluetoothOper = new BluetoothOpertion(this, new myBluetoothCallBack());

        if (!bluetoothOper.isOpen()) {
            bluetoothOper.open();
        }
    }


    Handler timehandler = new Handler();
    Runnable timerunnable = new Runnable() {
        @Override
        public void run() {
            ConBlue();
        }
    };


    private void ConBlue() {
        bluetoothOper.discovery();
        // 需要长时等待连接
        timehandler.postDelayed(timerunnable, 10000);

    }

    // TODO 待机状态返回时激活服务
    @Override
    protected void onResume() {
        super.onResume();
        timehandler.postDelayed(timerunnable, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bluetoothOper.stopDiscovery();
        timehandler.removeCallbacks(timerunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        StaticReceive.StopReceive();

        if (mbluetoothSocket != null) {
            bluetoothOper.stopDiscovery();
            bluetoothOper.disConnect(mbluetoothSocket);
            mbluetoothSocket = null;
        }
        mHandler.removeCallbacksAndMessages(null);
        //CacheActivity.getInstance().removeActivity(this);
    }


    private CurveChartView dc = null;

    private class myBluetoothCallBack implements IBluetoothCallBack {

        @Override
        public void OnFindDevice(BluetoothDevice arg0) {
            // TODO Auto-generated method stub

            Log.e("-sacdqaswc---", "---->OnFindDevice:" + arg0.getName());
            Log.e("-心电地址---", "---->OnFindAddress:" + arg0.getAddress());
            //String bule_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECM_ADDRESS);
            String bule_address = "";
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

                StaticReceive.startReceive(Jc_Ecm.this, arg0.getRemoteDevice()
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

    @SuppressLint("HandlerLeak")
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
                            if (isEnd) {
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
                                stringBuffer.delete(0, stringBuffer.length());
                            }

                            resulttxt.setText("心率:" + data.getInt("nHR") + ";"
                                    + data.getString("nResult"));
                            String message = data.getInt("nHR") + ";" + data.getString("nResult");
                            Log.e("ascda", message + "==");

                            final String[] mValue = message.toString().split(";");

                            endSb.append(ecgStr);
                            Log.e("心率", mValue[0] + "==");
                            Log.e("心电检查结果", mValue[1] + "==");
                            follow_save.setVisibility(View.VISIBLE);
                            endHeartRate = mValue[0];
                            endReulst = mValue[1];
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
    /*CompoundButton.OnCheckedChangeListener switch_listener = new CompoundButton.OnCheckedChangeListener() {

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
    };*/

    @OnClick({R.id.follow_re_back,R.id.follow_save})
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.follow_re_back){
            finish();
        }else if(id == R.id.follow_save){
            Intent intent = new Intent();
            intent.putExtra(BlueConstants.ECM_HEART_RATE,endHeartRate);
            intent.putExtra(BlueConstants.ECM_RESULT,endReulst);
            intent.putExtra(BlueConstants.ECM_XML_DATA,endSb.toString());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
