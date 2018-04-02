package com.example.bluesanfang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity {

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
    private String type = "tem";
    private BleManager mBleManager;
    private String service_uuid = "0000fff0-0000-1000-8000-00805f9b34fb";
    private String c_uuid = "0000fff2-0000-1000-8000-00805f9b34fb";
    private String device_name = "JK_FR";

    private List<BleDevice> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBleManager = BleManager.getInstance();
    }


    @OnClick({R.id.btn_startSao, R.id.btn_disconnet, R.id.btn_two, R.id.btn_open})
    public void click(Button v) {
        switch (v.getId()) {
            case R.id.btn_startSao:
                BlueUtils.getInstance().startScan(
                        device_name,
                        service_uuid,
                        c_uuid,
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

                break;
            case R.id.btn_open: //体温
                service_uuid = "0000fff0-0000-1000-8000-00805f9b34fb";
                c_uuid = "0000fff2-0000-1000-8000-00805f9b34fb";
                device_name = "JK_FR";
                break;
            case R.id.btn_two: //血氧
                service_uuid = "ba11f08c-5f14-0b0d-1080-007cbe238280";
                c_uuid = "0000cd01-0000-1000-8000-00805f9b34fb";
                device_name = "iChoice";
                break;
        }
    }


}
