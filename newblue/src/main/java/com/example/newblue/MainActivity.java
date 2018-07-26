package com.example.newblue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.newblue.interfaces.ConnectBlueToothListener;
import com.example.newblue.interfaces.DealDataListener;
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
    @BindView(R.id.btn_open)
    Button btn_open;
    @BindView(R.id.btn_two)
    Button btn_two;
    @BindView(R.id.btn_three)
    Button btn_three;
    private String type = "tem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_startSao, R.id.btn_disconnet, R.id.btn_two, R.id.btn_open, R.id.btn_three, R.id.btn_bpm,R.id.btn_bmi})
    public void click(Button v) {
        switch (v.getId()) {
            case R.id.btn_startSao:
                CommenBlueUtils.getInstance().connectBlueTooth(this, type, this);
                break;
            case R.id.btn_disconnet:
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                break;
            case R.id.btn_open: //体温
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = "tem";
                break;
            case R.id.btn_two: //血氧
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = "oxi";
                break;
            case R.id.btn_three://尿机
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = "ura";
                break;
            case R.id.btn_bpm: //血压计
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = "bpm";
                break;
            case R.id.btn_bmi: //体重体脂秤
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = "bmi";
                break;
            default:
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
        Log.i("sjkljklsjadkll", "---type--onDataFromBlue->" + type);
        Log.i("sjkljklsjadkll", "---数据--onDataFromBlue->" + message);
        switch (type) {
            case "tem":
                DealDataUtils.getInstance().dealTemData(message, this);
                break;
            case "oxi":
                DealDataUtils.getInstance().dealOxiData(message, this);
                break;
            case "ura":
                DealDataUtils.getInstance().dealUraData(message, this);
                break;
            case "bpm":
                DealDataUtils.getInstance().dealBpmData(message, this);
                break;
            case "bmi":
                DealDataUtils.getInstance().dealBmiData(message, this);
                break;
            default:
                break;
        }

    }


    @Override
    public void onFetch(String type,int code, String message) {
        switch (type){
            case "tem":
                setData(tv_text,message,code);
                break;
            case "oxi":
                setData(tv_text,message,code);
                break;
            case "ura":
                setData(tv_text,message,code);
                break;
            case "bpm":
                setData(tv_text,message,code);
                break;
            case "bmi":
                setData(tv_text,message,code);
                break;
            default:
                break;
        }
    }

    public void setData(TextView tv,String message,int code){
        if(100 == code){
            tv.setText(message);
        }else if(200 == code){
            tv.setText(message);
        }
    }
}
