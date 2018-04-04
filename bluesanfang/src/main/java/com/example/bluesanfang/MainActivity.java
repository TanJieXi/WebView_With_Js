package com.example.bluesanfang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity implements DealDataListener{

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
    private String service_uuid = "";
    private String[] c_uuid;
    private String device_name = "";
    private String write_uuid = "";
    private String write_key = "";
    private boolean isWrite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_startSao, R.id.btn_disconnet, R.id.btn_two, R.id.btn_open})
    public void click(Button v) {
        switch (v.getId()) {
            case R.id.btn_startSao:
                BlueUtils.getInstance().startScan(
                        device_name,
                        service_uuid,
                        c_uuid,
                        isWrite,
                        write_uuid,
                        write_key
                        ,
                        new ConnectBlueListener() {
                            @Override
                            public void onConnectSuccess(String message) {

                            }

                            @Override
                            public void onNotiFySuccess(String type, String message) {
                                Log.i("dsfdasffgsa", message);
                            }

                            @Override
                            public void onChangeText(String message) {
                                tv_text.setText(message);
                            }
                        }
                );
                break;
            case R.id.btn_disconnet:
                BlueUtils.getInstance().stopBlue();
                break;
            case R.id.btn_open: //体温
                setSixData("JK_FR",
                        "0000fff0-0000-1000-8000-00805f9b34fb",
                        new String[]{"0000fff2-0000-1000-8000-00805f9b34fb"},
                        false,
                        "",
                        "");
                break;
            case R.id.btn_two: //血氧
                setSixData("iChoice",
                        "ba11f08c-5f14-0b0d-1080-007cbe238280",
                        new String[]{
                                "0000cd01-0000-1000-8000-00805f9b34fb",
                                "0000cd02-0000-1000-8000-00805f9b34fb",
                                "0000cd03-0000-1000-8000-00805f9b34fb",
                                "0000cd04-0000-1000-8000-00805f9b34fb",
                        },
                        true,
                        "0000cd20-0000-1000-8000-00805f9b34fb",
                        "AA5504B10000B5");

                break;
        }
    }


    public void setSixData(String device_name,String service_uuid,String[] c_uuid,boolean isWrite,String write_uuid,String write_key){
        this.device_name = device_name;
        this.service_uuid = service_uuid;
        this.c_uuid = c_uuid;
        this.isWrite = isWrite;
        this.write_uuid = write_uuid;
        this.write_key = write_key;
    }


    @Override
    public void onFetch(int code, String message) {
        Log.i("dsfdasfgdsf", "-----onFetch------->" +message);
    }
}
