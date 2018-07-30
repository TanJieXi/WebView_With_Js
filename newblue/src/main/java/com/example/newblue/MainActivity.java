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
import com.example.newblue.utils.DealBlueDataUtils;
import com.example.newblue.utils.StringUtil;

import org.json.JSONObject;

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

    /**
     * 切换的时候，先断开，在设置type，在执行连接操作
     * @param v
     */
    @OnClick({R.id.btn_startSao, R.id.btn_disconnet, R.id.btn_two, R.id.btn_open,
            R.id.btn_three, R.id.btn_bpm, R.id.btn_bmi, R.id.btn_bgm, R.id.btn_hgb,
            R.id.btn_glhgb, R.id.btn_bft, R.id.btn_bua, R.id.btn_bpm_two})
    public void click(Button v) {
        switch (v.getId()) {
            case R.id.btn_startSao: //开始连接
                CommenBlueUtils.getInstance().connectBlueTooth(this, type, this);
                break;
            case R.id.btn_disconnet: //断开连接
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
            case R.id.btn_glhgb: //糖化血红蛋白检测
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_GLHGB;
                break;
            case R.id.btn_bft: //血脂
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BFT;
                break;
            case R.id.btn_bua: //血糖尿酸总胆固醇
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BUA;
                break;
            case R.id.btn_bpm_two: //第二种血压计
                CommenBlueUtils.getInstance().disConnectBlueTooth();
                type = BlueConstants.BLUE_EQUIP_BPM_TWO;
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
                DealBlueDataUtils.getInstance().dealTemData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_OXI://血氧
                DealBlueDataUtils.getInstance().dealOxiData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_URA://尿机
                DealBlueDataUtils.getInstance().dealUraData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BPM://血压计
                DealBlueDataUtils.getInstance().dealBpmData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BMI://体重体脂秤
                DealBlueDataUtils.getInstance().dealBmiData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BGM://血糖计
                DealBlueDataUtils.getInstance().dealBgmData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                DealBlueDataUtils.getInstance().dealHgbData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_GLHGB://糖化血红蛋白检测
                DealBlueDataUtils.getInstance().dealGlHgbData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BFT: //血脂
                DealBlueDataUtils.getInstance().dealBftData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BUA://血糖尿酸总胆固醇
                DealBlueDataUtils.getInstance().dealBuaData(message, this);
                break;
            case BlueConstants.BLUE_EQUIP_BPM_TWO://第二种血压计
                DealBlueDataUtils.getInstance().dealTwoBpmData(message, this);
                break;
            default:
                break;
        }

    }

    @Override
    public void onStatusFetch(String type, int code, String message) {
        switch (type) {
            case BlueConstants.BLUE_EQUIP_TEM://体温
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_OXI://血氧
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_URA://尿机
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BPM://血压计
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BMI://体重体脂秤
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BGM://血糖计
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_GLHGB://血红蛋白检测
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BFT: //血脂
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BUA: //血糖尿酸总胆固醇检测
                setData(tv_text, message, code);
                break;
            case BlueConstants.BLUE_EQUIP_BPM_TWO://第二种血压计
                setData(tv_text, message, code);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFetch(String type, int code, JSONObject resultJson) {
        String result = "";
        switch (type) {
            case BlueConstants.BLUE_EQUIP_TEM://体温
                result = "j体温结果:" + resultJson.optString("tem");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_OXI://血氧
                result = "j血氧： " + resultJson.optString("oxi")
                        + "脉率：" + resultJson.optString("pul") + "次每分钟";
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_URA://尿机
                result = "j白细胞 LEU:" + resultJson.optString("LEU") + "\t\t";
                result += "尿潜血 BLD:" + resultJson.optString("BLD") + "\n";
                result += "亚硝酸盐 NIT:" + resultJson.optString("NIT") + "\t\t";
                result += "酮体 KET:" + resultJson.optString("KET") + "\n";
                result += "尿胆原 UBG:" + resultJson.optString("UBG") + "\t\t";
                result += "胆红素 BIL:" + resultJson.optString("BIL") + "\n";
                result += "尿蛋白质 PRO:" + resultJson.optString("PRO") + "\t\t";
                result += "葡萄糖 GLU:" + resultJson.optString("GLU") + "\n";
                result += "尿PH值 PH:" + resultJson.optString("PH") + "\t\t";
                result += "维生素 C:" + resultJson.optString("VC") + "\n";
                result += "尿比重 SG:" + resultJson.optString("SG");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BPM://血压计
                result = "j舒张压 (mmHg):" + resultJson.optString("dia") +
                        " 收缩压 (mmHg):" + resultJson.optString("sys") +
                        " 心率(次/分钟):" + resultJson.optString("pul");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BMI://体重体脂秤
                result = "j体重:" + resultJson.optString("weight")
                        + ",脂肪:" + resultJson.optString("fat")
                        + ",水分:" + resultJson.optString("water")
                        + ",肌肉:" + resultJson.optString("muscle")
                        + ",骨量:" + resultJson.optString("bone")
                        + ",卡路里:" + resultJson.optString("calorie")
                        + ",BMI:" + resultJson.optString("BMI") + "Kg/㎡"
                        + ",身高:" + resultJson.optString("height") + "m";
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BGM://血糖计
                result = "j血糖结果：" + resultJson.optString("bgm")
                        + "单位：" + resultJson.optString("unit");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_HGB://血红蛋白检测
                result = "j血红蛋白结果：" + resultJson.optString("hgb")
                        + "单位：" + resultJson.optString("unit");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_GLHGB://血红蛋白检测
                result = "j糖化血红蛋白结果：" + resultJson.optString("glHgb")
                        + "单位：" + resultJson.optString("unit");
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BFT: //血脂
                result = "j总胆固醇(CHOL):" + resultJson.optString("CHOL") + " (mmol/L) "
                        + "高密度脂蛋白(HDL CHOL):" + resultJson.optString("HDLCHOL") + " (mmol/L) "
                        + "甘油三脂(TRIG):" + resultJson.optString("TRIG") + " (mmol/L) "
                        + "低密度脂蛋白:" + resultJson.optString("LDL")
                        + " (mmol/L) ";
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BUA: //血糖尿酸总胆固醇检测
                String bgm = resultJson.optString("bgm");
                String bua = resultJson.optString("bua");
                String tchol = resultJson.optString("tchol");
                if (!StringUtil.isEmpty(bgm)) {
                    result += "血糖：" + bgm + "mmol/L,";
                }
                if (!StringUtil.isEmpty(bua)) {
                    result += "血尿酸：" + bua + "umol/L,";
                }
                if (!StringUtil.isEmpty(tchol)) {
                    result += "总胆固醇：" + tchol + "mmol/L,";
                }
                setData(tv_text, result, 200);
                break;
            case BlueConstants.BLUE_EQUIP_BPM_TWO://第二种血压计
                String gao = resultJson.optString("gao");
                String di = resultJson.optString("di");
                String pul = resultJson.optString("pul");
                if (!StringUtil.isEmpty(gao)) {
                    result += "收缩压 (mmHg)：" + gao + ",";
                }
                if (!StringUtil.isEmpty(di)) {
                    result += "舒张压 (mmHg)：" + di + ",";
                }
                if (!StringUtil.isEmpty(pul)) {
                    result += "心率(次/分钟):：" + pul + ",";
                }
                setData(tv_text, result, 200);
                break;
            default:
                break;
        }
    }

    public void setData(TextView tv, String message, int code) {
        if (100 == code) {
            tv.setText(message);
        } else if (200 == code) {
            tv.setText(message);
        } else if (300 == code) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
