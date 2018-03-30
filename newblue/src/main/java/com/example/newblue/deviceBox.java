package com.example.newblue;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.example.newblue.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

@SuppressLint("DefaultLocale")
public class deviceBox {
    public BluetoothDevice device;
    public String uuidHeadWords = "";
    public static String uuidHeadWordsTemp = "";
    private boolean _isOnline = false;
    public int isUsed = 0;
    /**
     * 设备标识： 空.未知设备;
     * tem.室温体温枪;bmi.体重秤;ecm.心电计;bgm.血糖计;oxi.血氧仪;bpm.血压计;ura.半自动尿液检测仪
     * ;bft.血脂检测;bua.血尿酸;rem.睡眠记录;bab.胎心仪;
     * 1.室温体温枪;2.体重秤;3.心电计;4.血糖计;5.血氧仪;6.血压计;7.半自动尿液检测仪
     */
    public int ID = 0;
    public String Type = "";
    public Date reFreshDateTime = new Date();
    public String Name = "";
    public String Des = "";
    public static ArrayList<BluetoothDevice> mDevices = new ArrayList<>();

    public boolean isOnline() {

        if ((new Date().getTime() - reFreshDateTime.getTime()) < 10000) {

            _isOnline = true;
        } else {
            _isOnline = false;
        }
        return _isOnline;
    }

    public void set_isOnline(boolean _isOnline) {
        this._isOnline = _isOnline;
    }

    public String getUuidHeadWords() {
        return uuidHeadWords;
    }

    /**
     * 通过UUIDHEAD生成设备类型
     */
    public void setUuidHeadWords(String uuidHeadWords) {
        CharSequence cs1 = "--";
        CharSequence cs2 = "--";
        uuidHeadWords = Utils.convertHexToString(uuidHeadWords);

        // UuidHeadWords:(2655): ðÿ ÿ����JêL[ Electronic
        // Scale������������������������������������������������������
        cs1 = "ÿ����JêL";
        cs2 = "[	Electronic Scale";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "[	Electronic Scale";
            this.Name = "新铁片体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据";
            this.Type = "bmi";
        }

        cs1 = "iChoice";
        cs2 = "òØ!¾|ÿp";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "iChoiceòØ!¾|ÿp";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据";
            this.Type = "bmi";
        }

        // 改进前的体脂称
        cs1 = "CARE SCALE";
//		cs2 = "À";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "CARE SCALE";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据";
            this.Type = "bmi";
        }
        // 改进后的体脂称
        cs1 = "CARE SCALE";
        cs2 = "À";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "CARE SCALE";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据";
            this.Type = "bmi";
        }

        cs1 = "AMICCOM-BLE";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "AMICCOM-BLE";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据";
            this.Type = "bmi";

        }

        cs1 = "BT-RS100";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BT-RS100";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据。";
            this.Type = "bmi";
        }

        cs1 = "Electronic Scale";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "Electronic Scale";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据。";
            this.Type = "bmi";
        }

        cs1 = "Eagle BMI";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "Eagle BMI";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据。";
            this.Type = "bmi";
        }
        //小体脂称
        cs1 = "eBody-Fat-Scale";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "eBody-Fat-Scale";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据。";
            this.Type = "bmi";
        }
        //海尔体脂称
        cs1 = "000FatScale01";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "000FatScale01";
            this.Name = "体重体脂称";
            this.Des = "用于检测身体 体重、水分含量、肌肉含量、体脂含量、卡路里、骨量、BMI数据。";
            this.Type = "bmi";
        }
        cs1 = "ichoice";
        cs2 = "¾|";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "ichoice>)2¾|";
            this.Name = "血氧饱和度脉搏仪";
            this.Des = "用于检测人体呼吸功能及氧含量是否正常和脉搏跳动频率。";
            this.Type = "oxi";
        }

        cs1 = "iChoice";
        cs2 = "¾|";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "ichoice>)U/¾";
            this.Name = "血氧饱和度脉搏仪";
            this.Des = "用于检测人体呼吸功能及氧含量是否正常和脉搏跳动频率。";
            this.Type = "oxi";
        }
        // 04-01 14:10:29.052: E/未识别 UuidHeadWords:(23570):  Quintic
        // BLEbÅ'¾|��
        cs1 = "Quintic";
        cs2 = "¾|";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "Quintic BLEbÅ'¾";
            this.Name = "血氧饱和度脉搏仪";
            this.Des = "用于检测人体呼吸功能及氧含量是否正常和脉搏跳动频率。";
            this.Type = "oxi";
        }
        /*
         * 11-21 16:07:11.188: E/未识别 UuidHeadWords:(6225):  @ Bluetooth
		 * BP����������������������������������������������������������
		 * ����������������
		 */

        cs1 = "Bluetooth BP";
        cs2 = "";// ðÿ
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "Bluetooth BP";
            this.Name = "血压计";
            this.Des = "监测血压的变化，起到了预防脑出血 、心功能衰竭等疾病猝发的作用。";
            this.Type = "bpm";
        }
        cs1 = "eBlood-Pressure";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "eBlood-Pressure";
            this.Name = "血压计";
            this.Des = "监测血压的变化，起到了预防脑出血 、心功能衰竭等疾病猝发的作用。";
            this.Type = "bpm";
        }

        // 03-30 09:15:35.240: E/未识别 UuidHeadWords:(27941): ðÿ iCT@BP
        // iCT@BP������������������������������������������������������������������������������

        cs1 = "BLE to UART_2";
        cs2 = "ðÿ";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "BLE to UART_2";
            this.Name = "血压计";
            this.Des = "监测血压的变化，起到了预防脑出血 、心功能衰竭等疾病猝发的作用。";
            this.Type = "bpm";
        }

        cs1 = "iCT@BP	iCT@BP";
        cs2 = "ðÿ";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "iCT@BP	iCT@BP";
            this.Name = "血压计";
            this.Des = "监测血压的变化，起到了预防脑出血 、心功能衰竭等疾病猝发的作用。";
            this.Type = "bpm";
        }

        // cs1 = "BP036413";
        // cs2 = "";
        // if (uuidHeadWords.contains(cs1)) {
        // this.uuidHeadWords = "BP036413";
        // this.Name = "血压计";
        // this.Des = "监测血压的变化，起到了预防脑出血 、心功能衰竭等疾病猝发的作用。";
        // this.Type = "bpm";
        // }

        cs1 = "Sinocare";
        cs2 = "ÿHM";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "Sinocare";
            this.Name = "血糖仪";
            this.Des = "监控血糖了解体内血糖变化，用血更少，测量更快。";
            this.Type = "bgm";
        }
        cs1 = "OGM_112404X2004E2A";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "OGM_112404X2004E2A";
            this.Name = "血糖仪";
            this.Des = "监控血糖了解体内血糖变化，用血更少，测量更快。";
            this.Type = "bgm";
        }

        cs1 = "P10-B 3CAE";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "P10-B 3CAE";
            this.Name = "心电仪";
            this.Des = "记录心电波形、心率。";
            this.Type = "ecm";
        }

        // 大心电

        cs1 = "PC80B";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "PC80B";
            this.Name = "心电仪";
            this.Des = "记录心电波形、心率。";
            this.Type = "ecm";
        }

        cs1 = "BF4030";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BF4030";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }

        cs1 = "JKFR";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "JKFR";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }
        cs1 = "JK_FR";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "JK_FR";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }
        cs1 = "JKBGM";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "JKBGM";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }
        // 福达康体温枪
        cs1 = "SulongSPSGumpY";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "SulongSPSGumpY";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }
        // 体达体温枪
        cs1 = "E-Smoke";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "E-Smoke";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }
        // Tida体温枪
        cs1 = "Bluetooth BP";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "Bluetooth BP";
            this.Name = "体温枪";
            this.Des = "测量人体额头温度，环境温度、额头温度动态。";
            this.Type = "tem";
        }

        cs1 = "iChoiceT1";
        cs2 = "DEMMCÿÐ";
        if (uuidHeadWords.contains(cs1) && uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "iChoiceT1DEMMCÿÐ";
            this.Name = "连续体温计";
            this.Des = "连续测量身体的温度变化";
            this.Type = "tem";
        }

        cs1 = "BLE-EMP-Ui";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BLE-EMP-Ui";
            this.Name = "尿液检测仪";
            this.Des = "11项尿液分析，检测包括尿蛋白、尿葡萄糖、尿pH、尿酮体、尿胆红质、尿胆原、尿潜血、亚硝酸盐、尿白细胞、尿比重、维生素C和浊度。";
            this.Type = "ura";
        }

        cs1 = "BC014787";
        cs2 = "C01478";
        if (uuidHeadWords.contains(cs1) || uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "BC014787";
            this.Name = "尿液检测仪";
            this.Des = "12项尿液分析，检测包括尿蛋白、尿葡萄糖、尿pH、尿酮体、尿胆红质、尿胆原、尿潜血、亚硝酸盐、尿白细胞、尿比重、维生素C和浊度。";
            this.Type = "ura";
        }

        cs1 = "CardioChek";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "CardioChek Meter";
            this.Name = "血脂仪";
            this.Des = "检测包括总胆固醇、甘油三酯、血低密度胆固醇（LDL）和血高密度胆固醇（HDL）";
            this.Type = "bft";

        }

        cs1 = "BeneCheck-1447";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BeneCheck-1447";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        cs1 = "BeneCheck-0867";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BeneCheck-0867";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        cs1 = "BeneCheck-";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BeneCheck-";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        //中生康血糖尿酸胆固醇分析仪
        cs1 = "ZS";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "ZS";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        cs1 = "BLE Device-";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BLE Device-";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        cs1 = "BLE Device-A";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BLE Device-A";
            this.Name = "血脂仪";
            this.Des = "检测包括总胆固醇、甘油三酯、血低密度胆固醇（LDL）和血高密度胆固醇（HDL）";
            this.Type = "bft";
        }
        cs1 = "iGate";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "iGate";
            this.Name = "血脂仪";
            this.Des = "检测包括总胆固醇、甘油三酯、血低密度胆固醇（LDL）和血高密度胆固醇（HDL）";
            this.Type = "bft";
        }
        // if (uuidHeadWords.contains("--")) {
        // this.uuidHeadWords = "--";
        // this.Name = "睡眠监护仪";
        // this.Des = "记录并分析睡眠时生理参数。";
        // this.Type = "rem";
        // }

        cs1 = "Benecheck TC-B";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "Benecheck TC-B";
            this.Name = "血红蛋白";
            this.Des = "血红蛋白";
            this.Type = "hgb";
        }
        cs1 = "BeneCheck TC-B DONGLE";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BeneCheck TC-B DONGLE";
            this.Name = "血红蛋白";
            this.Des = "血红蛋白";
            this.Type = "hgb";
        }
        //外置蓝牙三合一
        cs1 = "BeneCheck GL-0FD5A9";
        cs2 = "BeneCheck GL-0F";
        if (uuidHeadWords.contains(cs1) || uuidHeadWords.contains(cs2)) {
            this.uuidHeadWords = "BeneCheck TC-B DONGLE";
            this.Name = "血尿酸仪";
            this.Des = "检测包括血尿酸，血糖和总胆固醇";
            this.Type = "bua";
        }
        cs1 = "HbA1c-101A004476";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "HbA1c-101A004476";
            this.Name = "血红蛋白";
            this.Des = "血红蛋白";
            this.Type = "glhgb";
        }
        //糖化血红蛋白
        cs1 = "HbA1c-101A019101";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "HbA1c-101A019101";
            this.Name = "血红蛋白";
            this.Des = "血红蛋白";
            this.Type = "glhgb";
        }

        cs1 = "iyuner v1.0";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "iyuner v1.0";
            this.Name = "胎心仪";
            this.Des = "监查胎儿胎动是否异常，计数胎心率和胎动。";
            this.Type = "bab";
        }

        cs1 = "HD_Reader";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "HD_Reader";
            this.Name = "身份证阅读机";
            this.Des = "用于读取身份证信息。";
            this.Type = "idr";
        }
        cs1 = "HD-Reader";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "HD-Reader";
            this.Name = "身份证阅读机";
            this.Des = "用于读取身份证信息。";
            this.Type = "idr";
        }
        cs1 = "iDR2303712F";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "iDR2303712F";
            this.Name = "身份证阅读机";
            this.Des = "用于读取身份证信息。";
            this.Type = "idr2303712f";
        }

        cs1 = "BC04";
        cs2 = "--";
        if (uuidHeadWords.contains(cs1)) {
            this.uuidHeadWords = "BC04";
            this.Name = "多参数健康检测仪";
            this.Des = "检查心电、血压、血氧、体温。";
            this.Type = "mfhd";
        }
        if (this.uuidHeadWords.length() <= 0) {
            if (uuidHeadWordsTemp.equals(uuidHeadWords)) {
                //过滤重复的未知设备日志
            } else {
                uuidHeadWordsTemp = uuidHeadWords;
                Log.e("未识别 UuidHeadWords:", uuidHeadWords);
            }
        } else {
            Log.e("已识别的UuidHeadWords:", uuidHeadWords);
        }
    }

    public int ImgResourceId() {
        // TODO Auto-generated method stub

      /*  if (this.Type.equals("tem")) {
            return R.drawable.instrument01;
        } else if (this.Type.equals("bmi")) {
            return R.drawable.instrument04;
        } else if (this.Type.equals("ecm")) {
            return R.drawable.instrument06;
        } else if (this.Type.equals("ecmxii")) {
            return R.drawable.instrument07;
        } else if (this.Type.equals("bgm")) {
            return R.drawable.instrument10;
        } else if (this.Type.equals("oxi")) {
            return R.drawable.instrument05;
        } else if (this.Type.equals("bpm")) {
            return R.drawable.instrument02;
        } else if (this.Type.equals("bpmkt")) {
            return R.drawable.instrument14;
        } else if (this.Type.equals("ura")) {
            return R.drawable.instrument08;
        } else if (this.Type.equals("bft")) {
            return R.drawable.instrument09;
        } else if (this.Type.equals("bua")) {
            return R.drawable.instrument03;
        } else if (this.Type.equals("bab")) {
            return R.drawable.instrument13;
        } else if (this.Type.equals("rem")) {
            return R.drawable.prorem;
        } else if (this.Type.equals("hgb")) {
            return R.drawable.instrument11;
        } else if (this.Type.equals("glhgb")) {
            return R.drawable.instrument12;
        } else if (this.Type.equals("BCM")) {
            return R.drawable.instrument15;
        } else if (this.Type.equals("cdu")) {
            return R.drawable.procdu;
        } else if (this.Type.equals("cdus")) {
            return R.drawable.instrument16;
        } else if (this.Type.equals("mfhd")) {
            return R.drawable.instrument17;
        } else if (this.Type.equals("bsc")) {
            return R.drawable.instrument18;
        } else if (this.Type.equals("cdus2")) {
            return R.drawable.instrument19;
        } else {
            return R.drawable.pro00;
        }*/
        return 1;
    }

    // public static boolean Begin_Jc(Context contthis, int arg2) {
    // if (Conver.LeDevices.get(arg2).device == null) {
    // return false;
    // }
    // Conver.LeDevices.get(arg2).isUsed++;// 检测次数
    // Intent intent = null;
    //
    // if (Conver.LeDevices.get(arg2).Type.equals("bgm")) {
    // intent = new Intent(contthis, Jc_Bgm.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("bpm")) {
    // intent = new Intent(contthis, Jc_Bpm.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("oxi")) {
    // intent = new Intent(contthis, Jc_Oxi.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("bmi")) {
    // intent = new Intent(contthis, Jc_Bmi.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("ecm")) {
    // intent = new Intent(contthis, Jc_Ecm.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("tem")) {
    // intent = new Intent(contthis, Jc_Tem.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("ura")) {
    // intent = new Intent(contthis, Jc_Ura.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("bft")) {
    // intent = new Intent(contthis, Jc_Bft.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("bua")) {
    // intent = new Intent(contthis, Jc_Bua.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("rem")) {
    // intent = new Intent(contthis, Jc_Rem.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("hgb")) {
    // intent = new Intent(contthis, Jc_Rem.class);
    // } else if (Conver.LeDevices.get(arg2).Type.equals("bab")) {
    // intent = new Intent(contthis, Jc_Bab.class);
    // }
    // if (intent != null) {
    // intent.putExtra(Conver.EXTRAS_DEVICE_INDEX, arg2);
    // contthis.startActivity(intent);
    // return true;
    // } else {
    // return false;
    // }
    // }

    public static boolean Begin_Jc(Context contthis, int arg2, deviceBox[] myDev) {
      /*  if (myDev[arg2].isUsed < 1) {
            myDev[arg2].isUsed = 1;// 手动模式
        }
        myDev[arg2].isUsed++;// 检测次数
        Intent intent = null;
        Log.e("type", myDev[arg2].Type);
        if (myDev[arg2].Type.equals("bgm")) {
            intent = new Intent(contthis, Jc_Bgm.class);
        } else if (myDev[arg2].Type.equals("bpm")) {
            intent = new Intent(contthis, Jc_Bpm.class);
        } else if (myDev[arg2].Type.equals("bpmkt")) {
            intent = new Intent(contthis, Jc_Bpmkt.class);
        } else if (myDev[arg2].Type.equals("oxi")) {
            intent = new Intent(contthis, Jc_Oxi.class);
        } else if (myDev[arg2].Type.equals("bmi")) {
            intent = new Intent(contthis, Jc_Bmi.class);
        } else if (myDev[arg2].Type.equals("ecm")) {
            intent = new Intent(contthis, Jc_Ecm.class);
        } else if (myDev[arg2].Type.equals("ecmxii")) {
            intent = new Intent(contthis, Jc_Ecmxii.class);
        } else if (myDev[arg2].Type.equals("tem")) {
            intent = new Intent(contthis, Jc_Tem.class);
        } else if (myDev[arg2].Type.equals("ura")) {
            intent = new Intent(contthis, Jc_Ura.class);
        } else if (myDev[arg2].Type.equals("bft")) {
            intent = new Intent(contthis, Jc_Bft.class);
        } else if (myDev[arg2].Type.equals("bua")) {
            intent = new Intent(contthis, Jc_Bua.class);
        } else if (myDev[arg2].Type.equals("rem")) {
            intent = new Intent(contthis, Jc_Rem.class);
        } else if (myDev[arg2].Type.equals("hgb")) {
            intent = new Intent(contthis, Jc_Hgb.class);
        } else if (myDev[arg2].Type.equals("glhgb")) {
            intent = new Intent(contthis, Jc_Glhgb.class);
        } else if (myDev[arg2].Type.equals("bab")) {
            intent = new Intent(contthis, Jc_Bab.class);
        } else if (myDev[arg2].Type.equals("BCM")) {
            intent = new Intent(contthis, Jc_Bcm.class);
        } else if (myDev[arg2].Type.equals("cdu")) {
            intent = new Intent(contthis, Jc_Cdu.class);
        } else if (myDev[arg2].Type.equals("cdus")) {
            intent = new Intent(contthis, SmartVUSView.class);
//            intent = new Intent(contthis, MainActivity.class);
        } else if (myDev[arg2].Type.equals("mfhd")) {
            intent = new Intent(contthis, Jc_Mfhd.class);
        } else if (myDev[arg2].Type.equals("bsc")) {
            intent = new Intent(contthis, BloodSampleCollection.class);
        } else if (myDev[arg2].Type.equals("cdus2")) {
            intent = new Intent(contthis, MainActivity.class);
        }
        if (intent != null) {
            intent.putExtra(Conver.EXTRAS_DEVICE_INDEX, arg2);
            contthis.startActivity(intent);
            return true;
        } else {
            return false;
        }*/
      return false;
    }

}
