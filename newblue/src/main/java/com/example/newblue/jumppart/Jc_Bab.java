package com.example.newblue.jumppart;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newblue.App;
import com.example.newblue.BlueConstants;
import com.example.newblue.R;
import com.example.newblue.blueconnect.BleWrapper;
import com.example.newblue.deviceBox;
import com.example.newblue.interfaces.BleWrapperUiCallbacks;
import com.example.newblue.scan.BluetoothScan;
import com.example.newblue.utils.Utils;
import com.example.newblue.view.BabView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NewApi")
public class Jc_Bab extends AppCompatActivity implements BleWrapperUiCallbacks, BluetoothScan.OnScanSuccessListener {
    private final static String TAG = Jc_Bab.class.getSimpleName();
    private int mDeviceID = -1;
    public TextView tvstate;
    public TextView results;
    private String mDeviceName = "";
    private String mDeviceAddress = "";
    private String mDeviceRSSI = "";
    private String bluetoothPass = "";
    // 程序步进值
    public int nownotfi = 0;
    public String NowDate = "";
    private BleWrapper mBleWrapper;

    private BluetoothGattService mBTServices;
    private ArrayList<BluetoothGattCharacteristic> mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
    private BluetoothGattCharacteristic mCharacteristicWrite = null;
    private String mLastUpdateTime = "";
    private boolean mNotificationEnabled = false;

    private ImageView touxiang;
    private TextView name;

    private String pvss = "";

    private int DTIME = 1000;
    private int BL = 0;
    private int StepNum = 0;

    private BabView babView = null;

    private boolean shaking08 = false;
    private boolean shaking07 = false;
    private boolean shaking06 = false;
    private boolean shaking05 = false;
    private boolean shaking = false;
    private boolean reply = false;

    // 握手
    public static String shake_msg1 = "08";
    public static String shake_msg2 = "070000000000";
    public static String shake_msg3 = "060000000000";
    public static String shake_msg4 = "050000000000";

    private String babResult = ""; //最终结果
    private String babXmlData = "";  //最终的xml文件

    @BindView(R.id.follow_save)
    RelativeLayout follow_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_jc_bab);

        ButterKnife.bind(this);
        follow_save.setVisibility(View.GONE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //不锁屏

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NowDate = format.format(new Date());

        tvstate = findViewById(R.id.tvstate);
        results = findViewById(R.id.results);

        babView =  findViewById(R.id.babview);
        LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        sp_params.height = Utils.Dp2Px(Jc_Bab.this, 300);
        sp_params.width = Utils.Dp2Px(Jc_Bab.this, 30 * 100 + 40);
        babView.setLayoutParams(sp_params);
        babView.invalidate();

      /*  remind_switch1 = (SwitchButton) findViewById(R.id.remind_switch1);
        String blue_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_BAB_ADDRESS);
        if (!blue_address.equals("")) {
            remind_switch1.setChecked(true);
        }
        remind_switch1.setOnCheckedChangeListener(switch_listener);*/

    }

    Handler timehandler = new Handler();
    Runnable timerunnable = new Runnable() {
        @Override
        public void run() {

            ConBlue();

        }
    };

    private void ConBlue() {

        if (mDeviceAddress.length() < 2) {

            // TODO 识别检测设备
            for (int i = 0; i < App.LeDevices.size(); i++) {
                if (App.LeDevices.get(i).Type == "bab"
                        && App.LeDevices.get(i).isOnline()
                        && App.LeDevices.get(i).device != null) {
                    mDeviceID = i;
                    //String bule_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_BAB_ADDRESS);
                    String bule_address = "";
                    if (!bule_address.equals("")) {
                        mDeviceAddress = bule_address;
                    } else {
                        mDeviceAddress = App.LeDevices.get(i).device
                                .getAddress();
                    }
                    mDeviceName = App.LeDevices.get(i).device.getName();
                    mBleWrapper.connect(mDeviceAddress);
                    break;
                }

            }

        }
        timehandler.postDelayed(timerunnable, 10000);

    }

    @Override
    public void onScanFetch(List<deviceBox> newDevice) {

    }

    // TODO 待机状态返回时激活服务
    @Override
    protected void onResume() {
        super.onResume();
        // Initializes list view adapter.
        BluetoothScan.getInstance().Start(this);

        if (mBleWrapper == null)
            mBleWrapper = new BleWrapper(this, this);

        if (mBleWrapper.initialize() == false) {
            // finish();
            BluetoothScan.getInstance().Stop();
        }
        timehandler.postDelayed(timerunnable, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BluetoothScan.getInstance().Stop();

        mBleWrapper.stopMonitoringRssiValue();
        mBleWrapper.diconnect();
        mBleWrapper.close();
        timehandler.removeCallbacks(timerunnable);
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
        BluetoothScan.getInstance().Stop();

        mBleWrapper.stopMonitoringRssiValue();
        mBleWrapper.diconnect();
        mBleWrapper.close();
        timehandler.removeCallbacks(timerunnable);
        handler.removeCallbacks(runnable);
    }

    // 显示回传数据
    private void displayData(String data) {
        if (data != null) {

            data = decodeByte(data);

            Log.e(TAG, data);

            if (data.length() < 6) {
                return;
            }


            if (shaking) {
                if (!data.startsWith("53")) {
                    return;
                }
                String cmd = data.substring(4, 6);
                if (cmd.equalsIgnoreCase("4c")) {
                    tvstate.setText("收到查询回复");
                    shaking08 = false;
                    shaking07 = true;
                } else if (cmd.equalsIgnoreCase("49")) {
                    tvstate.setText("收到ID");
                    shaking07 = false;
                    shaking06 = true;
                } else if (cmd.equalsIgnoreCase("45")) {
                    tvstate.setText("收到参数");
                    shaking06 = false;
                    shaking = true;
                    shaking05 = true;
                } else if (cmd.equalsIgnoreCase("4D")) {
                    tvstate.setText("测试开始");
                    shaking = false;
                    shaking05 = false;
                }
            } else {
                if (!reply) {
                    handler2.postDelayed(runnable2, 10000);
                    reply = true;
                }
                tvstate.setText("心率计数：" + Integer.parseInt(data.substring(8, 10), 16));
                babView.setData(Integer.parseInt(data.substring(8, 10), 16));

                babResult = Integer.parseInt(data.substring(8, 10), 16) + "";
                follow_save.setVisibility(View.VISIBLE);

                if (pvss.length() > 0) {
                    pvss += ",";
                }
                pvss += String.valueOf(Integer.parseInt(data.substring(8, 10), 16));
                if (pvss.length() > 2400) {
                    Log.e(TAG, "----------------------over");
                    /*String json = "action=dobab&data={\"userid\":\""
                            + UsersMod.get_id() + "\",\"createdate\":\""
                            + NowDate + "\",\"fileurl\":\"" + pvss + "\",\"source\":0}";
                    HttpPost(1, json);*/
                    pvss = "";

                    babXmlData = pvss;
                }
            }
        }
    }


    @OnClick({R.id.follow_save,R.id.follow_re_back})
    public void onClicks(View v) {
        int id = v.getId();
        if(id == R.id.follow_save){
            Intent intent = new Intent();
            intent.putExtra(BlueConstants.BAB_END_RESULT,babResult);
            intent.putExtra(BlueConstants.BAB_END_XMLDATA,babXmlData);
            setResult(RESULT_OK,intent);
            finish();
        }else if(id == R.id.follow_re_back){
            finish();
        }
    }


    boolean b = false;

    Handler handleraa = new Handler();

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

                tvstate.setText("设备在线,对码中..");
                Log.e("test", "连接到设备成功");
                BluetoothScan.getInstance().Stop();
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
                tvstate.setText("连接断开");
                Log.e("test", "设备断开");

                if (pvss.length() > 240) {
                   /* String json = "action=dobab&data={\"userid\":\""
                            + UsersMod.get_id() + "\",\"createdate\":\""
                            + NowDate + "\",\"fileurl\":\"" + pvss + "\"}";
                    HttpPost(1, json);*/
                    pvss = "";
                }

                StepNum = 0;
                BL = 0;
                handler.removeCallbacks(runnable);
                BluetoothScan.getInstance().Start(Jc_Bab.this);
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
                                && uuid.contains("00001000-0000-1000-8000-00805f9b34fb")) {
                            mBTServices = service;
                            mBleWrapper
                                    .getCharacteristicsForService(mBTServices);
                            setDevUUID("00001002-0000-1000-8000-00805f9b34fb",
                                    16);
                            setDevUUID("00001001-0000-1000-8000-00805f9b34fb",
                                    8);

                            // setDevUUID("00001003-0000-1000-8000-00805f9b34fb",
                            // 8);
                            // bluetoothPass =
                            // "0805000000"+Conver.Checksum("0805000000");
                            bluetoothPass = "";
                            Log.e(TAG, "bluetoothPass==" + bluetoothPass);
                            SetNotfi();

                            shaking = true;
                            shaking08 = true;
                            handler.removeCallbacks(runnable);
                            handler.postDelayed(runnable, 1000);

                            break;
                        }

                        // 10-20 11:52:28.490: E/ch.getUuid():(13961):
                        // 00001001-0000-1000-8000-00805f9b34fb:[PROPERTY_READ][PROPERTY_WRITE_NO_RESPONSE][PROPERTY_WRITE][PROPERTY_NOTIFY]
                        // 10-20 11:52:28.490: E/ch.getUuid():(13961):
                        // 00001002-0000-1000-8000-00805f9b34fb:[PROPERTY_READ][PROPERTY_WRITE_NO_RESPONSE][PROPERTY_WRITE][PROPERTY_NOTIFY][PROPERTY_READ][PROPERTY_NOTIFY]
                        // 10-20 11:52:28.490: E/ch.getUuid():(13961):
                        // 00001003-0000-1000-8000-00805f9b34fb:[PROPERTY_READ][PROPERTY_WRITE_NO_RESPONSE][PROPERTY_WRITE][PROPERTY_NOTIFY][PROPERTY_READ][PROPERTY_NOTIFY][PROPERTY_WRITE]
                        // 10-20 11:52:28.490: E/ch.getUuid():(13961):
                        // 00001004-0000-1000-8000-00805f9b34fb:[PROPERTY_READ][PROPERTY_WRITE_NO_RESPONSE][PROPERTY_WRITE][PROPERTY_NOTIFY][PROPERTY_READ][PROPERTY_NOTIFY][PROPERTY_WRITE][PROPERTY_READ]
                        // 10-20 11:52:28.490: E/ch.getUuid():(13961):
                        // 00001005-0000-1000-8000-00805f9b34fb:[PROPERTY_READ][PROPERTY_WRITE_NO_RESPONSE][PROPERTY_WRITE][PROPERTY_NOTIFY][PROPERTY_READ][PROPERTY_NOTIFY][PROPERTY_WRITE][PROPERTY_READ][PROPERTY_READ][PROPERTY_WRITE]

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

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            Log.e("胎心==", "mBleWrapper==" + mBleWrapper + "||" + "mCharacteristicWrite==" + mCharacteristicWrite + "shaking==" + shaking);
            if (mBleWrapper != null && mCharacteristicWrite != null) {

                if (shaking) {
                    if (shaking08) {
                        mBleWrapper.writeDataToCharacteristic(mCharacteristicWrite, encodeMessage(shake_msg1));
                    } else if (shaking07) {
                        mBleWrapper.writeDataToCharacteristic(mCharacteristicWrite, encodeMessage(shake_msg2));
                    } else if (shaking06) {
                        mBleWrapper.writeDataToCharacteristic(mCharacteristicWrite, encodeMessage(shake_msg3));
                    } else if (shaking05) {
                        mBleWrapper.writeDataToCharacteristic(mCharacteristicWrite, encodeMessage(shake_msg4));
                    }
                    handler.postDelayed(runnable, 1000);
                    Log.e("再次发送", "--------------");
                } else {
                    handler.removeCallbacks(runnable);
                    Toast.makeText(Jc_Bab.this, "握手成功！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private Handler handler2 = new Handler();
    private Runnable runnable2 = new Runnable() {
        public void run() {
            if (mBleWrapper != null && mCharacteristicWrite != null) {

                if (reply) {
                    mBleWrapper.writeDataToCharacteristic(mCharacteristicWrite, encodeMessage(shake_msg4));
                    handler2.removeCallbacks(runnable2);
                    Log.e("发送命令给胎心仪，证明自己还活着", "发送命令给胎心仪，证明自己还活着");
                    reply = false;
                }
            }
        }
    };

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
                mDeviceRSSI = rssi + " db";
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
                mBleWrapper.requestCharacteristicValue(mCharacteristics
                        .get(nownotfi - 1));
            } else if (nownotfi - 1 == mCharacteristics.size()) {
                if (bluetoothPass.length() > 0) {
                    mBleWrapper.WriteHexString(mCharacteristicWrite,
                            bluetoothPass);
                }
            }
        }
    }

/*------------------------------------------------------------*/

    /**
     * 艾孕尔专用解密函数
     */
    public String decodeByte(String input) {
        int[] mychar = new int[input.length() / 2];
        int header = Integer.parseInt(input.substring(0, 2), 16);
        int random = header ^ Integer.parseInt(input.substring(4, 6), 16);

        String rechar = "";
        for (int i = 6; i < input.length(); i = i + 2) {

            mychar[i / 2] = random ^ Integer.parseInt(input.substring(i, i + 2), 16);
        }

        for (int i = 3; i < mychar.length; i++) {

            rechar += intToHex(mychar[i] & 0xff);
        }

        return rechar;
    }

    /**
     * 256以下10进制数字转16进制2位字符
     */
    public static String intToHex(int intNum) {
        String output = "";
        output = Integer.toHexString(intNum);
        if (output.length() == 1) {
            output = "0" + output;
        }
        return output;
    }

	/*------------------------------------------------------------*/

    public static byte[] encodeMessage(String data) {
        byte[] srcBytes = hexToBytes(data);
        int srcLen = srcBytes.length;
        int len = srcLen + 3;

        Random random = new Random();
        int randomInt = random.nextInt() % 10;

        byte[] encodeByte = new byte[len];
        byte header = 84;

        encodeByte[0] = header;
        encodeByte[1] = (byte) (header ^ srcLen + 1);
        encodeByte[2] = (byte) (header ^ randomInt);
        for (int i = 0; i < srcLen; i++) {
            byte srcByte = srcBytes[i];
            encodeByte[i + 3] = (byte) (randomInt ^ srcByte);
        }

        return encodeByte;
    }

    /**
     * hexToBytes
     *
     * @param paramString
     * @return
     */
    public static byte[] hexToBytes(String paramString) {
        if (paramString.length() % 2 != 0) {
            String str1 = paramString.substring(0, -1 + paramString.length());
            String str2 = paramString.substring(-1 + paramString.length(), paramString.length());
            paramString = str1 + "0" + str2;
        }
        byte[] arrayOfByte = new byte[paramString.length() / 2];
        for (int i = 0; ; ++i) {
            if (i >= arrayOfByte.length)
                return arrayOfByte;
            arrayOfByte[i] = (byte) Integer.parseInt(paramString.substring(i * 2, 2 + i * 2), 16);
        }
    }

	/*------------------------------------------------------------*/

   /* CompoundButton.OnCheckedChangeListener switch_listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton view, boolean isChecked) {
            if (isChecked) {
                if (mDeviceAddress.length() > 5) {
                    SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_BAB_ADDRESS, mDeviceAddress);
                } else {
                    ToastUtil.showShortToast("蓝牙地址为空!");
                    remind_switch1.setChecked(false);
                }
            } else {
                SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_BAB_ADDRESS, "");
            }
        }
    };
*/

}
