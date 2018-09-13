package com.example.newblue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.newblue.jumppart.Jc_Bab;
import com.example.newblue.jumppart.Jc_Bcm;
import com.example.newblue.jumppart.Jc_Ecm;
import com.example.newblue.jumppart.Jc_Ecmxii;
import com.example.newblue.utils.UpDateAppUtils;
import com.healon.up20user.UI.MainActivity;

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

    @OnClick({R.id.btn_ecm, R.id.btn_ecmii,R.id.btn_bab,R.id.btn_cdus,R.id.btn_bcm,R.id.btn_update})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_ecm) {
            startActivityForResult(new Intent(this, Jc_Ecm.class), BlueConstants.ECM_JUMP_CODE);
        } else if (id == R.id.btn_ecmii) {
            startActivityForResult(new Intent(this, Jc_Ecmxii.class), BlueConstants.ECMII_JUMP_CODE);
        } else if(id ==R.id.btn_bab){
            startActivityForResult(new Intent(this, Jc_Bab.class), BlueConstants.BAB_JUMP_CODE);
        } else if(id == R.id.btn_cdus){
            startActivity(new Intent(this, MainActivity.class));
        } else if(id == R.id.btn_bcm){
            startActivity(new Intent(this, Jc_Bcm.class));
        } else if(id == R.id.btn_update){
            UpDateAppUtils.getInstance().upDataApp(this,"http://api.znjtys.com/d/index.html");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (BlueConstants.ECM_JUMP_CODE == requestCode && RESULT_OK == resultCode) {
            if (data != null) {
                String endEcmXml = data.getStringExtra(BlueConstants.ECM_XML_DATA);
                String endEcmResult = data.getStringExtra(BlueConstants.ECM_RESULT);
                String endEcmRate = data.getStringExtra(BlueConstants.ECM_HEART_RATE);
                String result = "心率：" + endEcmRate + ",结果：" + endEcmResult + ",东西：" + endEcmXml;
                tv_texts.setText(result);
            }
        } else if (BlueConstants.ECMII_JUMP_CODE == requestCode && RESULT_OK == resultCode) {
            if (data != null) {
                String endEcmXml = data.getStringExtra(BlueConstants.ECM_XML_URL);
                String endEcmRate = data.getStringExtra(BlueConstants.ECM_HEART_RATE);
                String endLookXml = data.getStringExtra(BlueConstants.ECM_XML_LOOK_URL);
                String result = "心率：" + endEcmRate + ",分析地址：" + endEcmXml + ",查看心电xml地址：" + endLookXml;
                tv_texts.setText(result);
            }
        }else if (BlueConstants.BAB_JUMP_CODE == requestCode && RESULT_OK == resultCode) {
            if (data != null) {
                String endResult = data.getStringExtra(BlueConstants.BAB_END_RESULT);
                String endXmlData = data.getStringExtra(BlueConstants.BAB_END_XMLDATA);
                String result = "心率计数：" + endResult + ", 数据：" + endXmlData;
                tv_texts.setText(result);
            }
        }

    }
}
