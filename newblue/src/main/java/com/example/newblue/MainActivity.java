package com.example.newblue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newblue.interfaces.ConnectBlueToothListener;
import com.example.newblue.interfaces.DealDataListener;
import com.example.newblue.utils.CommenBlueUtils;
import com.example.newblue.utils.DealDataUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ConnectBlueToothListener, DealDataListener {

    @BindView(R.id.tv_text)
    TextView tv_text;
    private String type = "tem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_startSao, R.id.btn_disconnet, R.id.btn_two, R.id.btn_open, R.id.btn_three, R.id.btn_bpm,R.id.btn_bmi,R.id.btn_bgm,R.id.btn_hgb,R.id.btn_glhgb,R.id.btn_bft})
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
                type = BlueConstants.BLUE_EQUIP_TEM;
                break;
            case R.id.btn_two: //血氧
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_OXI;
                break;
            case R.id.btn_three://尿机
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_URA;
                break;
            case R.id.btn_bpm: //血压计
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BPM;
                break;
            case R.id.btn_bmi: //体重体脂秤
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BMI;
                break;
            case R.id.btn_bgm: //血糖计
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BGM;
                break;
            case R.id.btn_hgb: //血红蛋白检测
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_HGB;
                break;
            case R.id.btn_glhgb://糖化血红蛋白检测
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_GLHGB;
                break;
            case R.id.btn_bft://血脂
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BFT;
                break;
            default:
                break;
        }
    }

    @Override
    public void onReConnectEqip(String message) {
        tv_text.setText(message);
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
        Log.i("bleWrapper", "---type--onDataFromBlue->" + type);
        Log.i("bleWrapper", "---数据--onDataFromBlue->" + message);
        switch (type) {
            case BlueConstants.BLUE_EQUIP_TEM: //体温
                DealDataUtils.getInstance().dealTemData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_OXI://血氧
                DealDataUtils.getInstance().dealOxiData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_URA://尿机
                DealDataUtils.getInstance().dealUraData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BPM://血压计
                DealDataUtils.getInstance().dealBpmData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BMI://体重体脂秤
                DealDataUtils.getInstance().dealBmiData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BGM://血糖计
                DealDataUtils.getInstance().dealBgmData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                DealDataUtils.getInstance().dealHgbData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_GLHGB://糖化血红蛋白检测
                DealDataUtils.getInstance().dealGlHgbData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BFT: //血脂
                DealDataUtils.getInstance().dealBftData(message, this);
                break;
            default:
                break;
        }

    }



    @Override
    public void onFetch(String type,int code, String message) {
        switch (type){
            case BlueConstants.BLUE_EQUIP_TEM://体温
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_OXI://血氧
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_URA://尿机
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_BPM://血压计
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_BMI://体重体脂秤
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_BGM://血糖计
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_GLHGB://血红蛋白检测
                setData(tv_text,message,code);
                break;
            case BlueConstants.BLUE_EQUIP_BFT: //血脂
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
        }else if(300 == code){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }
}
