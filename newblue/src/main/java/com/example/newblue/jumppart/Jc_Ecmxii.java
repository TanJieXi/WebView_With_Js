package com.example.newblue.jumppart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.bluetooth.BluetoothOpertion;
import com.creative.bluetooth.IBluetoothCallBack;
import com.example.newblue.BlueConstants;
import com.example.newblue.R;
import com.example.newblue.utils.ToastUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import serial.jni.DataUtils;


@SuppressLint("NewApi")
public class Jc_Ecmxii extends AppCompatActivity {
    @BindView(R.id.show_wave_btn)
    Button showWaveBtn;
    @BindView(R.id.tvstate)
    TextView tvstate;
    // 程序步进值
    public String PublicMsg = "";
    private ProgressDialog pb;
    private static final int REQUEST_ENABLE_BT = 3;
    private BluetoothOpertion bluetoothOper;
    private BluetoothSocket mbluetoothSocket;
    private boolean isflag = false;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DataUtils datas;
    private String resultCity = "";
    private String strload = "";
    private static String mblueAddress = "";
    @BindView(R.id.follow_save)
    RelativeLayout follow_save;
    @BindView(R.id.seek_btn)
    Button seek_btn;

    private String endXml = "";//分析心电的xml
    private String endLookXml = "";//查看心电的xml
    private String endHeartRate = ""; //心率
    private String endReulst = ""; //心电结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_jc_ecmxii);
        ButterKnife.bind(this);

        pb = new ProgressDialog(this);
        pb.setMessage("加载中...");
        pb.setCancelable(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //不锁屏
        follow_save.setVisibility(View.GONE);
     /*   remind_switch1 = (SwitchButton) findViewById(R.id.remind_switch1);
        String blue_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECMXII_ADDRESS);
        if (!blue_address.equals("")) {
            remind_switch1.setChecked(true);
        }
        remind_switch1.setOnCheckedChangeListener(switch_listener);*/

        bluetoothOper = new BluetoothOpertion(this, new myBluetoothCallBack());
        if (!bluetoothOper.isOpen()) {
            bluetoothOper.open();
        }
    }

    @OnClick({R.id.seek_btn,R.id.show_wave_btn,R.id.follow_save})
    public void onClicks(View v) {
        int id = v.getId();
        if(id == R.id.seek_btn){
            isflag = true;
            pb.show();
            bluetoothOper = new BluetoothOpertion(Jc_Ecmxii.this, new myBluetoothCallBack());
            if (!bluetoothOper.isOpen()) {
                bluetoothOper.open();
            }
            bluetoothOper.discovery();
        }else if(id == R.id.show_wave_btn){
           /* DBManager dbManager = new DBManager(this);
            String url = dbManager.getEcmxiiUrl(UsersMod.get_id() + "");
            if (TextUtils.isEmpty(url)) {
                ToastUtil.showShortToast("请先做检查");
                break;
            }
            startWaveActivity(url);*/
            if (TextUtils.isEmpty(endLookXml)) {
                ToastUtil.showShortToast("请先做检查");
                return;
            }
            startWaveActivity(endLookXml);
        }else if(id == R.id.follow_save){
            Intent intent = new Intent();
            intent.putExtra(BlueConstants.ECM_HEART_RATE,endHeartRate);
            intent.putExtra(BlueConstants.ECM_XML_URL,endXml);
            intent.putExtra(BlueConstants.ECM_XML_LOOK_URL,endLookXml);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    // TODO 待机状态返回时激活服务
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        bluetoothOper.stopDiscovery();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        StaticReceive.StopReceive();

        if (mbluetoothSocket != null) {
            bluetoothOper.stopDiscovery();
            bluetoothOper.disConnect(mbluetoothSocket);
            mbluetoothSocket = null;
        }

        //CacheActivity.getInstance().removeActivity(this);
    }


    private void startWaveActivity(String url) {
        Intent intent = new Intent(this, EcmXIIWaveViewerActivity.class);
        intent.putExtra(BlueConstants.ECMII_STRING_URL, url);
        startActivity(intent);
    }


    private class myBluetoothCallBack implements IBluetoothCallBack {

        @Override
        public void OnFindDevice(BluetoothDevice arg0) {
            // TODO Auto-generated method stub
            //11-21 10:44:53.528: E/----(6502): ---->OnFindDevice:ECGWS12
            Log.e("----", "---->OnFindDevice:" + arg0.getName());
            Log.e("----", "---->OnFindDevice:" + arg0.getAddress());
            //String bule_address = SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).getString(SPUtilsName.BULE_ECMXII_ADDRESS);
            String bule_address = "";
            if (((arg0.getName() + "").equals("ECGWS12") && isflag) || (!TextUtils.isEmpty(mblueAddress) && mblueAddress.length() > 5 && isflag)) {
                if (TextUtils.isEmpty(mblueAddress) || (arg0.getName() + "").equals("ECGWS12")) {
                    mblueAddress = arg0.getAddress();
                }
                if (!TextUtils.isEmpty(bule_address)) {
                    mblueAddress = bule_address;
                }
                Log.e("蓝牙搜索", "蓝牙搜索" + mblueAddress);
                //SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_ECMXII_ADDRESS, mblueAddress);
                isflag = false;
                bluetoothOper.stopDiscovery();
                Intent intent = new Intent(Jc_Ecmxii.this, GLActivity.class);
                intent.putExtra("device_address", mblueAddress);
                startActivityForResult(intent, REQUEST_ENABLE_BT);
                pb.dismiss();

            }
        }

        @Override
        public void OnException(int arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnException:" + arg0);
            pb.dismiss();
            ToastUtil.showShortToast("设备连接异常");
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

//                StaticReceive.startReceive(Jc_Ecmxii.this, arg0.getRemoteDevice().getName(), new BLUReader(
//                        arg0.getInputStream()), new BLUSender(arg0.getOutputStream()), mHandler);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void OnConnectFail(String arg0) {
            // TODO Auto-generated method stub
            Log.e("----", "---->OnConnectFail:");
            pb.dismiss();
            ToastUtil.showShortToast("设备连接失败");
        }
    }

    ;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK && data != null) {

                    // 取出Intent里的Extras数据
                    Bundle bd = data.getExtras();
                    // 取出Bundle中数据
                    resultCity = bd.getString("url");
                    strload = bd.getString("strload");
                    Log.e("strload", strload + "==");

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            readXML(resultCity);
                            datas = new DataUtils(strload);
                            datas.ecgDataToAECG(Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + strload + ".c8k",
                                    Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + strload + ".xml");

                        }
                    });

                }
        }

    }

    private void readXML(String url) {
        // TODO Auto-generated method stub

        Log.e("----", "readXML---->" + url);

        try {

            String str1 = "";

            File file = new File(url + "_advice.xml");
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                endXml = file.getAbsolutePath();
                SAXReader reader = new SAXReader();
                Document document = reader.read(file);
                // 获取根节点元素对象
                Element root = document.getRootElement();


                Element element1 = root.element("Normal").element("Lead12");
                if (element1 != null && element1.attributeValue("HR") != null) {
                    str1 += "心率 : " + element1.attributeValue("HR");
                    System.out.println(element1.attributeValue("HR"));
                }

                Element element2 = root.element("Advice");
                if (element2 != null && !element2.getText().equals("")) {
                    str1 += " ; " + element2.getText();
                    System.out.println(element2.getText());
                }

                PublicMsg = str1;

                System.out.println(str1);
                String message = element1.attributeValue("HR") + ";" + element2.getText() + ";" + "12导心电";
                Log.e("12道心电存入", message + "==");

                final String[] mValue = message.toString().split(";");
                if (mValue.length > 2) {
                    Log.e("心率3", mValue[0] + "==");
                    Log.e("心电检查结果3", mValue[1] + "==");
                    endHeartRate = mValue[0];

                }

            }

            String str2 = url + ".xml";

            if (str1.length() > 0) {

                sendXD(str1, str2);

            } else {
                Log.e("----", "---->分析错误");
                Toast.makeText(this, "分析错误", Toast.LENGTH_SHORT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvstate.setText("分析错误");
                    }
                });

            }


        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private void sendXD(final String str1, final String str2) {

        Log.e("----", "sendXD---->" + str1);
        Log.e("----", "url---->" + str2);
        endLookXml = str2;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                follow_save.setVisibility(View.VISIBLE);
                tvstate.setText(str1 + "(此结果仅供参考)");
                //showWaveBtn.setVisibility(View.VISIBLE);
            }
        });

    }

   /* CompoundButton.OnCheckedChangeListener switch_listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton view, boolean isChecked) {
            if (isChecked) {
                if (mblueAddress.length() > 5) {
                    SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_ECMXII_ADDRESS, mblueAddress);
                } else {
                    ToastUtil.showShortToast("蓝牙地址为空!");
                    remind_switch1.setChecked(false);
                }
            } else {
                SPUtils.getInstance(SPUtilsName.BULE_ALL_ADDRESS).put(SPUtilsName.BULE_ECMXII_ADDRESS, "");
                ToastUtil.showShortToast("12心电蓝牙解绑成功!");
            }
        }
    };*/

}
