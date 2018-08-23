package com.example.newblue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.newblue.jumppart.Jc_Ecm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JumpAvtivity extends AppCompatActivity {
    @BindView(R.id.tv_texts)
    TextView tv_texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_avtivity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ecm})
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_ecm){
            startActivityForResult(new Intent(this, Jc_Ecm.class),BlueConstants.ECM_JUMP_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(BlueConstants.ECM_JUMP_CODE == requestCode && RESULT_OK == resultCode){
            if(data != null){
                String endEcmXml = data.getStringExtra(BlueConstants.ECM_XML_DATA);
                String endEcmResult = data.getStringExtra(BlueConstants.ECM_RESULT);
                String endEcmRate = data.getStringExtra(BlueConstants.ECM_HEART_RATE);
                String result = "心率：" + endEcmRate + ",结果：" + endEcmResult + ",东西："+endEcmXml;
                tv_texts.setText(result);
            }
        }

    }
}
