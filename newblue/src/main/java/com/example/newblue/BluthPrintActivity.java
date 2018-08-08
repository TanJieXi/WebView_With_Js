package com.example.newblue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.newblue.interfaces.ConnectBluePrintListener;
import com.example.newblue.utils.CommentBluePrintUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 蓝牙打印机
 */
public class BluthPrintActivity extends AppCompatActivity implements ConnectBluePrintListener {
    @BindView(R.id.tv_texts)
    TextView tv_texts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluth_print);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_print_connect, R.id.btn_print, R.id.btn_intercept})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print_connect:
                CommentBluePrintUtils.getInstance().connectBlue(this, this);
                break;
            case R.id.btn_print:
                JSONObject json = new JSONObject();
                try {
                    json.put(BlueConstants.BLUE_PRINT_NAME, "小熊猫");
                    json.put(BlueConstants.BLUE_PRINT_BAR_CODE, "211111111112");
                    json.put(BlueConstants.BLUE_PRINT_SEX, "男");
                    json.put(BlueConstants.BLUE_PRINT_PRINTNUM, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CommentBluePrintUtils.getInstance().startPrint(json);
                break;
            case R.id.btn_intercept:
                CommentBluePrintUtils.getInstance().disConnectBluePrint();
                break;
            default:
                break;
        }
    }



    @Override
    public void onConnectStatus(int status, String message) {
        tv_texts.setText(message);
    }

    @Override
    public void onDataFromBlue(String type, String message) {

    }
}
