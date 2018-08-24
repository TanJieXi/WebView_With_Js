package com.example.newblue.jumppart;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.newblue.App;
import com.example.newblue.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import serial.jni.BluConnectionStateListener;
import serial.jni.DataUtils;
import serial.jni.GLView;
import serial.jni.MyRenderer;
import serial.jni.NativeCallBack;

public class GLActivity extends AppCompatActivity {
    private GLView glView;
    private DataUtils data;
    private Context mContext;

    private String str = "";
    //	private String str = "/znjtys_img/Jc_Ecmxii/";
    private boolean iso = false;//防止链接中退出
    private boolean isp = false;//判断波形稳定

    String address = "00:0E:EA:D0:0A:AC";// GW

    private TextView textView, textLead;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private MyHandler mHandler;
    private static final int MESSAGE_UPDATE_LeadOff = 1;
    public static final int MESSAGE_CONNECT_START = 0x100;
    public static final int MESSAGE_CONNECT_SUCCESS = 0x200;
    public static final int MESSAGE_CONNECT_FAILED = 0x300;
    public static final int MESSAGE_CONNECT_INTERRUPTED = 0x400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
        setContentView(R.layout.follow_glsurfaceview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //不锁屏
        // if (getRequestedOrientation() !=
        // ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // }
// 定义文件夹目录地址
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.cd7d.zk" + File.separator + "ecm" ;
        /**
         * 创建文件目录
         */
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        mContext = this;
        Intent para = getIntent();
        address = para.getExtras().getString("device_address");
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度
        int height = dm.heightPixels;// 屏幕高度
        Log.e("Activity WxH", width + "x" + height);
        Log.e("Density", "" + dm.densityDpi);

        textView = (TextView) this.findViewById(R.id.textLeadOff);
        textLead = (TextView) this.findViewById(R.id.textLead);
        Button b1 = (Button) this.findViewById(R.id.btn01);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                if (isp) {
                Log.e("----", "b1---->保存中，请稍等...");
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "保存中，请稍等...").sendToTarget();
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                str = formatter.format(curDate);
                data.saveCase(Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/", str, 20);// 存储文件 // 参数为路径，文件名，存储秒数
//                } else {
//                    if (mHandler!=null){
//                        mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "波形未稳定，请稍等...").sendToTarget();
//                    }
//                }


            }
        });
        // data对象包含所有心电采集相关操作
        // glView负责显示

        // 蓝牙采集
        data = new DataUtils(App.getContext(), address, new BluConnectionStateListener() {
            @Override
            public void OnBluConnectionInterrupted() {
                // TODO Auto-generated method stub
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_CONNECT_INTERRUPTED,
                            -1, -1).sendToTarget();
                }
            }

            @Override
            public void OnBluConnectSuccess() {
                // TODO Auto-generated method stub
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_CONNECT_SUCCESS, -1,
                            -1).sendToTarget();
                }
            }

            @Override
            public void OnBluConnectStart() {
                // TODO Auto-generated method stub
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_CONNECT_START)
                            .sendToTarget();
                }
            }

            @Override
            public void OnBluConnectFaild() {
                // TODO Auto-generated method stub
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_CONNECT_FAILED, -1,
                            -1).sendToTarget();
                }
            }
        });

//		3、工频滤波
        data.setFilter(1);
//		4、基线滤波
        data.setFilter(4);
//		5、肌电滤波
        data.setFilter(2);
//		2、滤波全开
        data.setFilter(7);
        //走速
        data.setSpeed(DataUtils.DISPLAY_SPEED_25);
        //增益
        data.setGain(DataUtils.DISPLAY_GAIN_10);

        // 演示文件采集
        // data = new
        // DataUtils(Environment.getExternalStorageDirectory().getPath()+"/demo.ecg");

        // 以下关于glView操作为必要操作，请不要更改
        glView = (GLView) this.findViewById(R.id.GLWave);
        glView.setBackground(Color.TRANSPARENT, Color.rgb(111, 110, 110));
        glView.setGather(data);
        glView.setMsg(mHandler);
        glView.setZOrderOnTop(true);
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        textHR = (TextView) this.findViewById(R.id.textHR);


        copyFile("conclusion.cn", Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/conclusion.cn");

    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File newfile = new File(newPath);
            if (!newfile.exists()) { //文件不存在时
                Log.e("----", "---->开始复制");
                InputStream inStream = getAssets().open(oldPath);
                //InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println("字节数 文件大小" + bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            } else {
                Log.e("----", "---->文件存在");
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        shotdown();
        //CacheActivity.getInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        Log.i("----", "onBackPressed");

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
        data.gatherEnd();
    }


    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
        data.gatherStart(new nativeMsg(this));

    }

    private TextView textHR;
    private static final int MESSAGE_UPDATE_HR = 0;
    private static final int MESSAGE_UPDATE_TXT = 10;
    private static final int MESSAGE_UPDATE = 100;

    //    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_UPDATE_HR:
//
//                    textHR.setText(msg.obj.toString() + "bpm");
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_SUCCESS:
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_INTERRUPTED:
//                    Log.e("BL", "INT");
//                    // Intent interrupt = new Intent(GLActivity.this,
//                    // DeviceListActivity.class);
//                    // startActivity(interrupt);
//                    finish();
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_FAILED:
//                    Log.e("BL", "IOE");
//                    // Intent intent = new Intent(GLActivity.this,
//                    // DeviceListActivity.class);
//                    // startActivity(intent);
//                    finish();
//                    break;
//                case MESSAGE_UPDATE_TXT:
//                    textView.setText(msg.obj.toString());
//                    break;
//                case MESSAGE_UPDATE:
//
//
//                    Intent intent = getIntent();
//                    intent.putExtra("url", Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str);
//                    GLActivity.this.setResult(Activity.RESULT_OK, intent);
//                    GLActivity.this.finish();
//                    break;
//            }
//        }
//    };
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_UPDATE_HR:
//
//                    textHR.setText(msg.obj.toString() + "bpm");
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_SUCCESS:
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_INTERRUPTED:
//                    Log.e("BL", "INT");
//                    // Intent interrupt = new Intent(GLActivity.this,
//                    // DeviceListActivity.class);
//                    // startActivity(interrupt);
//                    finish();
//                    break;
//                case BluetoothConnect.MESSAGE_CONNECT_FAILED:
//                    Log.e("BL", "IOE");
//                    // Intent intent = new Intent(GLActivity.this,
//                    // DeviceListActivity.class);
//                    // startActivity(intent);
//                    finish();
//                    break;
//                case MESSAGE_UPDATE_TXT:
//                    textView.setText(msg.obj.toString());
//                    break;
//                case MESSAGE_UPDATE:
//
//
//                    Intent intent = getIntent();
//                    intent.putExtra("url", Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str);
//                    GLActivity.this.setResult(Activity.RESULT_OK, intent);
//                    GLActivity.this.finish();
//                    break;
//            }
//            return false;
//        }
//    });
    public static class MyHandler extends Handler {
        private final WeakReference<GLActivity> weakReference;

        public MyHandler(GLActivity activity) {
            weakReference = new WeakReference<GLActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GLActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                switch (msg.what) {
                    case MESSAGE_UPDATE_HR:

                        mainActivity.showtext(msg);
                        break;
                    case MESSAGE_CONNECT_SUCCESS:
                        break;
                    case MESSAGE_CONNECT_INTERRUPTED:
                        Log.e("BL", "INT");
                        mainActivity.finishs();
                        break;
                    case MESSAGE_CONNECT_FAILED:
                        Log.e("BL", "IOE");
                        mainActivity.finishs();
                        break;
                    case MESSAGE_UPDATE_TXT:
                        mainActivity.showtext2(msg);
                        break;
                    case MESSAGE_UPDATE:
                        mainActivity.intents();
                        break;
                    case MyRenderer.MESSAGE_GATHER_START://开始绘图
//                        Toast.makeText(mContext, "开始绘图", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    public void showtext(Message msg) {
        if (msg.obj.toString().contains("-100")) {
            textHR.setText("---bpm");
        } else {
            textHR.setText(msg.obj.toString() + "bpm");
        }
    }

    public void showtext2(Message msg) {
        textView.setText(msg.obj.toString());
    }

    public void finishs() {
        finish();
    }

    public void intents() {

        Intent intent = getIntent();
        intent.putExtra("url", Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str);
        intent.putExtra("strload", str);
        GLActivity.this.setResult(Activity.RESULT_OK, intent);
        GLActivity.this.finish();
    }

    static class nativeMsg extends NativeCallBack {
        //        GLActivity activity;
        WeakReference<GLActivity> weakReference;

        nativeMsg(GLActivity activity) {
//            this.activity = activity;
            weakReference = new WeakReference<GLActivity>(activity);
        }

        @Override
        public void callHRMsg(short hr) {// 心率
            GLActivity activity = weakReference.get();
            if (activity != null) {
                activity.callHRMsg(hr);
            }
//            mHandler.obtainMessage(MESSAGE_UPDATE_HR, hr).sendToTarget();
//            activity.callHRMsg(hr);
        }

        @Override
        public void callLeadOffMsg(String flagOff) {// 导联脱落
//            Log.e("LF", flagOff);

//            iso = true;
            GLActivity activity = weakReference.get();
            if (activity != null) {
                activity.callLeadOffMsg(flagOff);
            }
        }

        @Override
        public void callProgressMsg(short progress) {// 文件存储进度百分比 progress%
//            Log.e("progress", "" + progress);
            GLActivity activity = weakReference.get();
            if (activity != null) {
                activity.callProgressMsg("分析中，请稍等...(" + progress + "%)");
            }
        }

        @Override
        public void callCaseStateMsg(short state) {
            if (state == 0) {
                Log.e("Save", "start");// 开始存储文件
            } else {
                Log.e("Save", "end");// 存储完成

//				Toast.makeText(GLActivity.this, "存储完成", Toast.LENGTH_LONG).show();
                //textView.setText("存储完成");
                Log.e("----", "b2---->存储完成");
//                mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "存储完成").sendToTarget();
//
//
//                mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "分析中，请稍等...").sendToTarget();
//
//                executorService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        data.ecgDataToAECG(Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str + ".c8k",
//                                Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str + ".xml");
//
//                        data.ecgAnalyzeToXml(Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str,
//                                Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str + "_advice.xml",
//                                Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/conclusion.cn");
//
//                        mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "分析完成").sendToTarget();
//
//
//                        mHandler.obtainMessage(MESSAGE_UPDATE, null).sendToTarget();
//                    }
//                });
                GLActivity activity = weakReference.get();
                if (activity != null) {
                    activity.callCaseStateMsg();
                }
            }
        }

        @Override
        public void callHBSMsg(short hbs) {// 心率 hbs = 1表示有心跳
            // Log.e("HeartBeat", "Sound"+hbs);
        }

        @Override
        public void callBatteryMsg(short per) {// 采集盒电量
            // Log.e("Battery", ""+per);
        }

        @Override
        public void callCountDownMsg(short per) {// 剩余存储时长
            // Log.e("CountDown", ""+per);
        }

        @Override
        public void callWaveColorMsg(boolean flag) {
            Log.e("WaveColor", "" + flag);
            if (flag) {
//                // 波形稳定后颜色变为绿色
//                glView.setRendererColor(0, 1.0f, 0, 0);
//                // 以下操作可以实现自动开始保存文件
//                // SimpleDateFormat formatter = new SimpleDateFormat
//                // ("yyyyMMddHHmmss");
//                // Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//                // String str = formatter.format(curDate);
//                // data.saveCase(Environment.getExternalStorageDirectory()+"/",str,20);//存储文件
//                // 参数为路径，文件名，存储秒数
//
//
//                mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "波形稳定").sendToTarget();
//
//                isp = true;
                GLActivity activity = weakReference.get();
                if (activity != null) {
                    activity.callWaveColorMsg();
                }
            }
        }
    }

    public void callHRMsg(short hr) {
        if (mHandler != null) {
            mHandler.obtainMessage(MESSAGE_UPDATE_HR, hr).sendToTarget();
        }
    }

    public void callProgressMsg(String hr) {
        if (mHandler != null) {
            mHandler.obtainMessage(MESSAGE_UPDATE_TXT, hr).sendToTarget();
        }
    }

    public void callLeadOffMsg(final String flagOff) {
        iso = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(flagOff)) {
                    if (flagOff.contains("NO")) {
                        textLead.setText("");
                    } else {
                        textLead.setText("   " + flagOff + "导联脱落");
                    }
                } else {
                    textLead.setText("");
                }
            }
        });
    }

    public void callWaveColorMsg() {
        glView.setRendererColor(0, 1.0f, 0, 0);
        // 以下操作可以实现自动开始保存文件
        // SimpleDateFormat formatter = new SimpleDateFormat
        // ("yyyyMMddHHmmss");
        // Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        // String str = formatter.format(curDate);
        // data.saveCase(Environment.getExternalStorageDirectory()+"/",str,20);//存储文件
        // 参数为路径，文件名，存储秒数

        if (mHandler != null) {
            mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "波形稳定").sendToTarget();
        }

        isp = true;
    }

    public void callCaseStateMsg() {
        if (mHandler != null) {
            mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "存储完成").sendToTarget();
            mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "分析中，请稍等...").sendToTarget();
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                data.ecgAnalyzeToXml(Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str,
                        Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/" + str + "_advice.xml",
                        Environment.getExternalStorageDirectory() + "/com.cd7d.zk/ecm/conclusion.cn");
                if (mHandler != null) {
                    mHandler.obtainMessage(MESSAGE_UPDATE_TXT, "分析完成").sendToTarget();
                    mHandler.obtainMessage(MESSAGE_UPDATE, null).sendToTarget();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
            //dialog();

            if (!iso) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void dialog() {
        Builder builder = new Builder(GLActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GLActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void shotdown() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }
}
