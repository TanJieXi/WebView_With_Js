package com.example.newblue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.newblue.interfaces.ConnectBlueToothListener;
import com.example.newblue.interfaces.DealDataListener;
import com.example.newblue.scan.BluetoothScan;
import com.example.newblue.utils.CommenBlueUtils;
import com.example.newblue.utils.DealDataUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ConnectBlueToothListener, DealDataListener {

    @BindView(R.id.btn_startSao)
    Button btn_startSao;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.btn_disconnet)
    Button btn_disconnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothScan.getInstance().init(this);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_startSao,R.id.btn_disconnet})
    public void click(Button v) {
        switch (v.getId()){
            case R.id.btn_startSao:
                CommenBlueUtils.getInstance().connectBlueTooth(this,"oxi",this);
                break;
            case R.id.btn_disconnet:
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                break;
        }
    }


    @Override
    public void onConnectSuccess(String message) {
        tv_text.setText(message);
    }

    @Override
    public void onInterceptConnect(String message) {
        tv_text.setText(message);
    }

    @Override
    public void onDataFromBlue(String type, String message) {
        Log.i("bleWrapper", "---数据--->"+ message);
        DealDataUtils.getInstance().dealOxiData(message,this);
    }

    @Override
    public void onFetch(int code, String message) {
        switch (code){
            case 100:
                tv_text.setText(message);
                break;
            case 200:
                tv_text.setText(message);
                break;

        }
    }
}
