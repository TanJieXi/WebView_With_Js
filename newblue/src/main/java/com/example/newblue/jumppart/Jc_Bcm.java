package com.example.newblue.jumppart;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newblue.App;
import com.example.newblue.R;
import com.example.newblue.deviceBox;
import com.example.newblue.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class Jc_Bcm extends AppCompatActivity implements OnClickListener {

    private final static String TAG = Jc_Bcm.class.getSimpleName();

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final boolean D = true;

    //private Net MyNet = new Net(this, this);

    private StringBuffer mOutStringBuffer;

    public TextView results;

    public ListView bcmdata;
    Adapter adapter;
    public static List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
    // 程序步进值
    public int nownotfi = 0;

    BluetoothAdapter BlueAdapter;
    private BluetoothChatServiceDemo mChatService = null;
    public static BluetoothSocket btSocket;

    private TextView begintime, endtime;

    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_DATAPICK_BEGIN = 0;
    private static final int SHOW_DATAPICK_END = 1;

    private static int DATE_DIALOG = 1;
    int stopThread = 0;
    Calendar date = Calendar.getInstance();
    ProgressDialog pd;
    Thread t;
    private StringBuffer stringBuffer;
    @BindView(R.id.follow_toolbar_title)
    TextView follow_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_jc_bcm);
        ButterKnife.bind(this);
        follow_toolbar_title.setText("生化分析");
        pd = new ProgressDialog(Jc_Bcm.this);
        pd.setMessage("数据获取中...");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 不锁屏
        stringBuffer = new StringBuffer();
        bcmdata = (ListView) findViewById(R.id.bcmdata);
        results = (TextView) findViewById(R.id.results);

        adapter = new Adapter();
        bcmdata.setAdapter(adapter);


        this.findViewById(R.id.sendbtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatService.getState() == BluetoothChatServiceDemo.STATE_CONNECTED) {
                    readMessage = "";
                    stringBuffer.setLength(0);
                    final String str = "MSG_M_TIMESTAMP_";

                    String st = begintime.getText().toString();
                    String et = endtime.getText().toString();

                    if (st.equals("")) {
                        st = "1900-01-01 00:00:00";
                    } else {
                        st += " 00:00:00";
                    }

                    if (et.equals("")) {
                        et = "2900-01-01 00:00:00";
                    } else {
                        et += " 23:59:59";
                    }
                    Log.e(TAG, "-----" + st + "|" + et);
                    final String str1 = getTemp(st, et);
                    pd.show();

                    final Timer timer1 = new Timer();  //创建计时器
                    timer1.schedule(new TimerTask() {
                        int sum = 0;

                        @Override
                        public void run() {
                            sendMessage(str + str1);
                            Log.e(TAG, "-----" + str + str1);
                            sum += 1;
                            if (sum >= 3) {
                                timer1.cancel();
                            }
                        }
                    }, 0, 1200);
                    count = 0;

                    t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if (stopThread == 0) {
                                pd.cancel();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(Jc_Bcm.this, "没有任何数据！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                    });
                    t.start();
                    //readMessage = "";
                } else {
                    Toast.makeText(Jc_Bcm.this, "请先连接设备", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bcmdata.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("id", arg2);
                intent.setClass(Jc_Bcm.this, Jc_bcm_content.class);
                startActivity(intent);
            }
        });

        BlueAdapter = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能

        // 注册Receiver来获取蓝牙设备相关的结果
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(searchDevices, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(searchDevices, filter);

        begintime = findViewById(R.id.begintime);
        endtime = findViewById(R.id.endtime);
        begintime.setOnClickListener(this);
        endtime.setOnClickListener(this);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        // new Date()为获取当前系统时间
        String str = df.format(new Date());

        Date endDate = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            endDate = df.parse(df.format(cal.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        begintime.setText(df.format(endDate));
        endtime.setText(str);
    }

    public String getTemp(String st, String et) {
        String timeStemp = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(st);
            st = "" + date.getTime();
            st = st.substring(0, st.length() - 3);
            date = simpleDateFormat.parse(et);
            et = "" + date.getTime();
            et = et.substring(0, et.length() - 3);
            timeStemp = st + "_" + et;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return timeStemp;
    }

    Handler timehandler = new Handler();
    Runnable timerunnable = new Runnable() {
        @Override
        public void run() {
            if (BlueAdapter.isDiscovering()) {
                BlueAdapter.cancelDiscovery();
            }
            if (mChatService.getState() != BluetoothChatServiceDemo.STATE_CONNECTED) {  //设备不是连接状态
                // Request discover from BluetoothAdapter
                BlueAdapter.startDiscovery();
                Log.d("设备扫描中", "设备扫描中..");
                results.setText("设备扫描中请稍后...");
                timehandler.postDelayed(timerunnable, 8000);
            }
        }
    };

//	private void ConBlue() {}

    @Override
    public void onStart() {
        super.onStart();
        if (D)
            Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!BlueAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null)
                setupChat();
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");
        mChatService = new BluetoothChatServiceDemo(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    // TODO 待机状态返回时激活服务
    @Override
    protected void onResume() {
        super.onResume();
        timehandler.postDelayed(timerunnable, 500);

        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mChatService.getState() == BluetoothChatServiceDemo.STATE_NONE) {
                // Start the Bluetooth chat services
                Log.e("开启蓝牙服务", "开启蓝牙服务");
                mChatService.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timehandler.removeCallbacks(timerunnable);
        Log.e("onPause", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timehandler.removeCallbacks(timerunnable);
        this.unregisterReceiver(searchDevices);
        Log.e("onDestroy", "onDestroy");
        if (mChatService != null) {
            mChatService.stop();
            mChatService = null;
        }
        if (listmap.size() > 0) {
            listmap.clear();
        }
        //CacheActivity.getInstance().removeActivity(this);
    }

    private BroadcastReceiver searchDevices = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String bule_address = "";//拿shre里面的地址
                Log.e(TAG, "----" + device.getName());
                try {
                    if ((device.getName() + "").contains("PM")) {
                        results.setText("设备连接中...");
                        timehandler.removeCallbacks(timerunnable);
                        if(StringUtil.isEmpty(bule_address)){
                            mChatService.connect(device);
                        }else{
                            if(bule_address.equals(device.getAddress())){
                                mChatService.connect(device);
                            }
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }


            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                Log.e("设备扫描完成", "设备扫描完成");
                setProgressBarIndeterminateVisibility(false);

            }

        }
    };


    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    public void sendMessage(String message) {
        // Check that we're actually connected before trying anything

        Log.e(TAG, "getState----" + mChatService.getState());

        if (mChatService.getState() != BluetoothChatServiceDemo.STATE_CONNECTED) {

            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);

        }
    }

    int count = 0;
    String readMessage;
    // The Handler that gets information back from the BluetoothChatService
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("msg", msg.obj + "|" + msg.what);
            // MESSAGE_STATE_CHANGE: 3

            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothChatServiceDemo.STATE_CONNECTED:

                            Log.e(TAG, "----链接成功");

                            results.setText("设备已连接");
                            //sendMessage("MSG_ERGODIC");
                            timehandler.removeCallbacks(timerunnable);
                            BlueAdapter.cancelDiscovery();


                            // new
                            // Jc_Bcm().sendMessage("MSG_M_TIMESTAMP_1480521600_1480694400");
                            break;
                        case BluetoothChatServiceDemo.STATE_CONNECTING:
                            Log.e("111", "2222");
                            break;
                        case BluetoothChatServiceDemo.STATE_LISTEN:
                        case BluetoothChatServiceDemo.STATE_NONE: // 连接失败
                            BlueAdapter.startDiscovery();
                            //timehandler.postDelayed(timerunnable, 500);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.e(TAG, "MESSAGE_WRITE=" + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String str = new String(readBuf, 0, msg.arg1);
                    //Log.e("返回的数据", str);
                    //new LogWriter(str); //写入日志
//                    readMessage = readMessage + str;
                    stringBuffer.append(str);
                    readMessage = stringBuffer.toString();
                    // Log.i(TAG, str+"|"+str.length());
                    Log.e("获取readMessage==", readMessage + "====");
                    if (count == 0) {
                        listmap.clear();
                        stopThread = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                adapter.notifyDataSetChanged();
                            }
                        });
                        count = 1;
                        final Timer timer = new Timer();  //创建计时器
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Log.e("总数据", readMessage);
                                msgs2(readMessage);
                                pd.cancel();
                            }
                        }, 3000);
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name

                    break;
                case MESSAGE_TOAST:
                    Log.e("111", "11111");
                    break;
            }
        }

    };

    @SuppressLint("SimpleDateFormat")
    private String frmatDatatr(String strDate) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss"); // 实例化模板对象
        Date d = null;
        try {
            d = sdf2.parse(strDate); // 将给定的字符串中的日期提取出来
            sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf2.format(d);
        } catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理

            e.printStackTrace(); // 打印异常信息
            return strDate;
        }
    }

    private void msgs2(String readMessage) {

        listmap.clear();
        // TODO Auto-generated method stub
        String[] obr = readMessage.split("OBR");
        Map<String, Object> maptime = new HashMap<String, Object>();
        for (int i = 1; i < obr.length; i++) {
            String[] splitdata = obr[i].replace("|", "`").split("`");
            if (maptime.get(splitdata[7]) == null) {
                String[] obx = readMessage.split("OBX");
                Map<String, Object> map = new HashMap<String, Object>();
                Log.e("获取时间格式==", splitdata[7] + "====");
                map.put("time", frmatDatatr(splitdata[7])); // 检测日期
                map.put("batch", splitdata[46]);// 生产批号
                map.put("id", splitdata[47]); // 序号

                maptime.put(splitdata[7], splitdata[7]);
                for (int j = 1; j < obx.length; j++) {
                    String[] splitobx = obx[j].replace("|", "`").split("`");
//				 Log.i(TAG, "obx:"+splitobx.length+"---4:"+splitobx[4]+"---5:"+splitobx[5]);
                    if (splitobx.length > 14 && splitdata[7].equals(splitobx[14])) {
                        map.put(splitobx[4], splitobx[5]); // 检测日期

                    }

                }
                listmap.add(map);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

    }



    // Adapter for holding devices found through scanning.
    private class Adapter extends BaseAdapter {

        private LayoutInflater mInflator;

        public Adapter() {
            super();
            // TODO 从数据库读设备
            if (App.LeDevices == null) {
                App.LeDevices = new ArrayList<deviceBox>();
            }
            mInflator = Jc_Bcm.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return listmap.size();
        }

        @Override
        public Object getItem(int i) {
            return listmap.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            // General ListView optimization code.
            ViewHolder holder = null;
            if (view == null || view.getTag() == null) {
                view = mInflator.inflate(R.layout.follow_jc_bcm_item, null);
                holder = new ViewHolder();
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.time = (TextView) view.findViewById(R.id.time);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.name.setText(listmap.get(i).get("batch") + "—" + listmap.get(i).get("id"));
            holder.time.setText(listmap.get(i).get("time") + "");
            return view;
        }

    }

    public class ViewHolder {
        public TextView name, time;

//        public ViewHolder(View view) {
//            name = (TextView) view.findViewById(R.id.name);
//            time = (TextView) view.findViewById(R.id.time);
//        }
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    /**
     * 更新日期
     */
    private void updateDisplay() {

        Log.e(TAG, "1----------");
        readMessage = "";
        stringBuffer.setLength(0);
        if (DATE_DIALOG == 1) {
            begintime.setText(new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                    .append((mDay < 10) ? "0" + mDay : mDay));
        }

        if (DATE_DIALOG == 2) {
            endtime.setText(new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                    .append((mDay < 10) ? "0" + mDay : mDay));
        }

        begintime.setFocusable(false);// 让EditText失去焦点，然后获取点击事件
        endtime.setFocusable(false);// 让EditText失去焦点，然后获取点击事件

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Log.e("更新", "更新1111");
        switch (id) {
            case DATE_DIALOG_ID:

                DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                DatePicker datePicker = dialog.getDatePicker();
//            datePicker.setMinDate(DateUtils.getInstance().format("1970-01-01")  
//                    .getTime());  
                datePicker.setMaxDate(new Date().getTime());

//			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                return dialog;
        }
        return null;

    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                Log.e("更新", "弹出|" + DATE_DIALOG);
                String[] datetr;
                if (DATE_DIALOG == 1) {
                    datetr = begintime.getText().toString().split("-");
                } else {
                    datetr = endtime.getText().toString().split("-");
                }
                ((DatePickerDialog) dialog).updateDate(Integer.valueOf(datetr[0]), Integer.valueOf(datetr[1]) - 1, Integer.valueOf(datetr[2]));
                break;
        }
    }


    private void setDateTime() {
        Log.e(TAG, "4----------");

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.begintime) {
            showDialog(DATE_DIALOG_ID);
            Log.e("点击事件按钮", "点击事件按钮");
            DATE_DIALOG = 1;

        }

        if (v.getId() == R.id.endtime) {
            showDialog(DATE_DIALOG_ID);

            DATE_DIALOG = 2;

        }
        setDateTime();
    }

}
