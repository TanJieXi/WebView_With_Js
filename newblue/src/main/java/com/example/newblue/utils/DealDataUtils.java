package com.example.newblue.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.BlueConstants;
import com.example.newblue.interfaces.DealDataListener;
import com.holtek.libHTBodyfat.HTDataType;
import com.holtek.libHTBodyfat.HTPeopleGeneral;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by TanJieXi on 2018/3/30.
 */

public class DealDataUtils {
    private DealDataListener mDealDataListener;
    private static volatile DealDataUtils instance = null;
    private String type = "";
    Handler hh = new Handler();
    Runnable rr = new Runnable() {

        @Override
        public void run() {
            Log.i("sddasfasdgg", "这里2");
            CommenBlueUtils.getInstance().writeHexString("FDFDFA050D0A");
            hh.postDelayed(this, 3000);

        }
    };
    Runnable rr2 = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            iscf = false;
            CommenBlueUtils.getInstance().closeBpm();
            //BluetoothScan.getInstance().Start();
            Log.e("7秒之后执行",
                    "7秒之后执行7秒之后执行7秒之后执行7秒之后执行7秒之后执行7秒之后执行");
        }
    };

    Runnable timerunnable2 = new Runnable() {
        @Override
        public void run() {
            CommenBlueUtils.getInstance().writeBgmHexString("11223344-5566-7788-99aa-bbccddeeff00","2644312031200632373838340D");
            hh.removeCallbacks(timerunnable2);
            hh.postDelayed(timerunnable2, 1000);
        }
    };


    private boolean iscf2 = false;
    private boolean iscf = false;

    private DealDataUtils() {

    }

    public void removeAllHandler(){
        if(hh != null){
            hh.removeCallbacks(rr);
            hh.removeCallbacks(rr2);
            hh.removeCallbacks(timerunnable2);
        }
    }


    public static DealDataUtils getInstance() {
        if (instance == null) {
            synchronized (DealDataUtils.class) {
                if (instance == null) {
                    instance = new DealDataUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 处理血氧计的数据
     *
     * @param data
     */
    @SuppressLint("DefaultLocale")
    public void dealOxiData(String data, DealDataListener listener) {
        type = BlueConstants.BLUE_EQUIP_OXI;
        this.mDealDataListener = listener;
        if (data != null) {
            System.out.println("data:" + data);
            char[] chars = data.toCharArray();
            String hexStr = "";

            if (data.contains("55AA03")) {
                if (data.contains("55AA03B100B4")) {
                    mDealDataListener.onFetch(type, 100, "血氧脉率测量中，请稍候...");
                } else {
                    if (data.length() > 11) {
                        // 07-05 18:03:37.544: D/test(8103): data=55AA03624BB0
                        hexStr = "" + chars[6] + chars[7];
                        int myoxi = Integer.parseInt(hexStr, 16);
                        hexStr = "" + chars[8] + chars[9];
                        int mypulse = Integer.parseInt(hexStr, 16);
                        String result = "";
                        result = String.format("血氧：%d ;脉率：%d 次每分钟；", myoxi,
                                mypulse);
                        mDealDataListener.onFetch(type, 200, result);
                        // 检测成功给界面组件赋值 袁明全加 20151001
                    }
                }
            }
        }
    }

    public int getSexType(String sex) {
        if (sex.equals("男")) {
            return HTDataType.SexTypeMale;
        } else {
            return HTDataType.SexTypeFemale;
        }

    }
    int tt = 0;
    private String mDates = "";
    /**
     * 处理血糖的数据
     */
    public void dealBgmData(String data, DealDataListener listener){
        type = BlueConstants.BLUE_EQUIP_BGM;
        this.mDealDataListener = listener;
        if (data != null) {
            mDates += data;
            if (data.contains("534E")) {
                String _date = data.substring(data.indexOf("534E") + 4);
                char[] chars = _date.toCharArray();
                String ml = "" + chars[6] + chars[7];
                if (ml.equals("01")) {// 测试连接命令 需要仪器在开机状态且非倒计时的情况时才可测试连接
                    mDealDataListener.onFetch(type,100,"等待滴血..." + (tt++));
                } else if (ml.equals("02")) {// 错误状态
                    mDealDataListener.onFetch(type,100,"血糖计异常!");
                    CommenBlueUtils.getInstance().writeHexString("534E0600040B000015");
                } else if (ml.equals("03")) {// 滴血符号闪烁
                    mDealDataListener.onFetch(type,100,"滴血符号闪烁!");
                    CommenBlueUtils.getInstance().writeHexString("534E0800040153494E4F46");
                } else if (ml.equals("04")) {// 读当前结果命令
                    if (_date.length() > 21) {
                        String _bgm;
                        _bgm = "" + chars[18] + chars[19] + chars[20]
                                + chars[21];
                        float bgm = Integer.parseInt(_bgm, 16);
                        bgm = bgm / 10;
                        String result = bgm + " (mmol/L)";
                        mDealDataListener.onFetch(type,200,result);
                    }
                    CommenBlueUtils.getInstance().writeHexString("534E0600040B020017");
                } else if (ml.equals("05")) {// 读历史数据命令

                } else if (ml.equals("06")) {// 设置时间命令

                } else if (ml.equals("07")) {// 读 ID 号命令

                } else if (ml.equals("08")) {// 清空历史数据命令

                } else if (ml.equals("09")) {// 修改校正码命令

                } else if (ml.equals("0A")) {// 开始测试命令
                    mDealDataListener.onFetch(type,300,"开始测试命令，倒计时中!");
                } else if (ml.equals("0B")) {// 仪器关机命令
                    mDealDataListener.onFetch(type,300,"关机成功!");
                } else if (ml.equals("0C")) {// 仪器关蓝牙

                }
            }

            if (mDates.contains("26445A") && mDates.length() > 41) {//艾科蓝牙
                mDates = mDates.substring(mDates.indexOf("26445A"));
                String mDate = Utils.convertHexToString(mDates);
                if (mDate.startsWith("&DZ")) {
                    String[] array = mDate.split(" ");
                    if (array.length > 4) {
                        float datas = (float) (Float.parseFloat(array[3]) / 18 + 0.05);
                        if (Float.parseFloat(array[3]) == 9) {
                            mDealDataListener.onFetch(type,100,"测量值低于仪器检测范围!");
                        } else if (Float.parseFloat(array[3]) == 601) {
                            mDealDataListener.onFetch(type,100,"测量值高于仪器检测范围!");
                        } else {
                            DecimalFormat formater = new DecimalFormat("#0.#");
                            formater.setRoundingMode(RoundingMode.FLOOR);
                            String bgm = formater.format(datas);
                            String result = bgm + " (mmol/L)";
                            mDealDataListener.onFetch(type,200,result);
                            mDealDataListener.onFetch(type,300,"艾科血糖测试结果:" + formater.format(datas));
                        }
                        hh.postDelayed(timerunnable2, 1000);
                    }

                }
            }

            if (!TextUtils.isEmpty(mDates) && mDates.length() > 32 && mDates.startsWith("F10E")) {//民康血糖
                char[] chars = mDates.toCharArray();
                float n = Integer.parseInt(chars[30] + "", 16);//当 0 <= n <= 7：N*10的n次方    当 n >= 8：N*10的n次方-16
                System.out.println("民康血糖测试n=" + n);
                if (n >= 0 && n <= 7) {
                    float myweight = (float) ((float) Integer.parseInt("" + chars[28] + chars[29], 16) * Math.pow(10, n));
                    double a = Math.pow(10, n - 16);
                    double myweight2 = (myweight * a) / 10000;
                    System.out.println("myweight11=" + myweight + "  " + myweight2 + String.format("%4.1f", myweight2) + "mmol/L");
                    String bgm = String.format("%4.1f", myweight2);
                    try {
                        double bgms = Double.parseDouble(bgm);
                        if (bgms < 1.1) {
                            mDealDataListener.onFetch(type,300,"测量值低于仪器检测范围");
                        } else if (bgms > 33.3) {
                            mDealDataListener.onFetch(type,300,"测量值高于仪器检测范围");
                        } else {
                            String result = bgm + " (mmol/L)";
                            mDealDataListener.onFetch(type,200,"民康血糖测试结果:" + result);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (n >= 8) {
                    float myweight = (float) Integer.parseInt("" + chars[28] + chars[29], 16);
                    double a = Math.pow(10, n - 16);
                    double myweight2 = myweight * a * 1000;
                    System.out.println("myweight22==" + myweight + "  " + myweight2 + String.format("%4.1f", myweight2) + "mmol/L");
                    String bgm = String.format("%4.1f", myweight2);
                    try {
                        double bgms = Double.parseDouble(bgm);
                        if (bgms < 1.1) {
                            mDealDataListener.onFetch(type,300,"测量值低于仪器检测范围");
                        } else if (bgms > 33.3) {
                            mDealDataListener.onFetch(type,300,"测量值高于仪器检测范围");
                        } else {
                            String result = bgm + " (mmol/L)";
                            mDealDataListener.onFetch(type,200,"民康血糖测试结果:" + result);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                mDates = "";
            }
        }
    }

    public void setGlHgbString(){
        strDA = "";
    }
    private String strDA = "";
    /**
     * 处理糖化血红蛋白的数据
     */
    public void dealGlHgbData(String data, DealDataListener listener){
        type = BlueConstants.BLUE_EQUIP_GLHGB;
        this.mDealDataListener = listener;
        Log.e("test", "data=" + data);
        if (data != null) {
            strDA += data;
            if (strDA.length() > 100) {
                if (data.startsWith("4842")) {
                } else {
                    strDA = "";
                    return;
                }
                Log.e("strDA", "strDA=" + strDA);
                char[] chars = strDA.toCharArray();
                String s = chars[chars.length - 32] + "" + chars[chars.length - 31]
                        + "" + chars[chars.length - 34] + "" + chars[chars.length - 33]
                        + chars[chars.length - 36] + "" + chars[chars.length - 35]
                        + "" + chars[chars.length - 38] + "" + chars[chars.length - 37];
                Float value = Float.intBitsToFloat(Integer.valueOf(s.trim(), 16));
                String reuslt = "糖化血红蛋白：" +  value + "%";
                mDealDataListener.onFetch(type,200,reuslt);
                strDA = "";
            }

        }
    }

    private boolean flag = false;
    public void setHgbFlag(){
        flag = false;
    }
    /**
     * 处理血红蛋白检测器的数据
     */
    public void dealHgbData(String data, DealDataListener listener){
        type = BlueConstants.BLUE_EQUIP_HGB;
        this.mDealDataListener = listener;
        Log.e("test", "data=" + data);
        if (data != null) {
            char[] chars = data.toCharArray();
            Log.e("chars长度==", chars.length + "----------------------------");
            String hexStr = "";
            if (!flag) {
                if (chars.length >= 20) {
                    hexStr = "" + chars[8] + chars[9];
                    if (Integer.parseInt(hexStr, 16) == 225) {
                        hexStr = "" + chars[16] + chars[17];
                        if (Integer.parseInt(hexStr, 16) == 9) {
                            hexStr = "" + chars[chars.length - 4]
                                    + chars[chars.length - 3]
                                    + chars[chars.length - 6]
                                    + chars[chars.length - 5];
                            float str = (float) (Integer.parseInt(hexStr, 16));
                            if (str < 70) {
                                mDealDataListener.onFetch(type,100,"测量结果低于检测范围,请重新测量！");
                            } else if (str > 260) {
                                mDealDataListener.onFetch(type,100,"测量结果高于检测范围,请重新测量！");
                            } else {
                                String result = str + "g/L";
                                mDealDataListener.onFetch(type,200,"测量结果：" + result);
                                flag = true;
                            }
                        }
                    }

                }
            }

        }
    }


    private float tempweight = 0;
    /**
     * 处理体重秤的数据
     */
    public void dealBmiData(String data, DealDataListener listener) {
        type = BlueConstants.BLUE_EQUIP_BMI;
        this.mDealDataListener = listener;
        Double uHeight = 170.0;
        int age = 20;
        Log.e("dealBmiData", "--->" + data);
        if (data != null && data.length() > 20) {
            System.out.println("data:" + data);
            Log.e("dealBmiData", "ssssss" + data);
            char[] chars = data.toCharArray();
            String hexStr = new String();
            // 计算体重 还要加个效验码验证，确保数据正确
            if (data.substring(0, 10).equals("55AA054001")) {
                hexStr = "" + chars[12] + chars[13] + chars[10] + chars[11];
                Log.d("test", "parseInt=" + Integer.parseInt(hexStr, 16));
                float myweight = Integer.parseInt(hexStr, 16);

                String pvss = String.format("%4.2f kg (%4.2f 斤)", myweight / 10,
                        myweight * 2 / 10);
                sb.append(pvss);
                // 检测成功给界面组件赋值
                String result = "体重:" + (myweight / 10) + ",脂肪:0" + ",水分:0"+ ",肌肉:0"+ ",骨量:0"+ ",卡路里:0";
                sb.append(result);
                float mybmi = 0;
                //Double uHeight = UsersMod.get_uHeight();
                if (uHeight == 0.0 || uHeight == 0) {
                    mybmi = 0;
                } else {
                    mybmi = (float) (((float) myweight / 10) / (float) ((uHeight / 100) * (uHeight / 100)));
                }
                sb.append("BMI：").append(mybmi).append("Kg/㎡");

                App.LeDevices.get(CommenBlueUtils.getInstance().getDeviceId()).Des += ",上次检测结果是："
                        + pvss;
                mDealDataListener.onFetch(type, 200, sb.toString());
            }

            // 小体脂称
            if (data.substring(0, 4).equals("02DD")
                    && data.substring(data.length() - 2).equals("AA")) {
                if (data.length() >= 28) {
                    // 02DD02A000C9D7DC022602801BAA
                    // // 体重
                    int Unit_Weight_H = Integer.parseInt(data.substring(4, 6),
                            16);
                    int Weight_L = Integer.parseInt(data.substring(6, 8), 16);// 体重低字节
                    int weight_h = (Unit_Weight_H & 0x3f) * 256;// 体重高字节
                    Log.e("tag", "-------weight = " + weight_h + "  "
                            + Weight_L);
                    double W = (double) ((weight_h + Weight_L) / 10.0);// 体重数值
                    Log.e("tag", "-------weight = " + W + "KG");
                    hexStr = "" + chars[8] + chars[9] + chars[10] + chars[11] + chars[12] + chars[13] + chars[14] + chars[15];
                    int resistance = Integer.parseInt(hexStr, 16);// 电阻值
                    Log.e("电阻系数resistance=", resistance + "=====");
                    HTPeopleGeneral bodyfats = new HTPeopleGeneral(W, uHeight,
                            getSexType("男"), age, resistance);
                    int errorType = bodyfats.getBodyfatParameters();
                    Log.e("-----", "w=" + W + "||height=" + uHeight + "||getSexType()=" + getSexType("男") + "||age=" + age + "||resistance=" + resistance);
                    if (errorType == HTDataType.ErrorNone) {
                        // 正常计算
                        Log.e("计算结果===",
                                "阻抗:"
                                        + bodyfats.ZTwoLegs
                                        + "Ω  BMI:"
                                        + String.format("%.1f", bodyfats.BMI)
                                        + "  BMR:"
                                        + (int) bodyfats.BMR
                                        + "  内脏脂肪:"
                                        + (int) bodyfats.VFAL
                                        + "  骨量:"
                                        + String.format("%.1fkg",
                                        bodyfats.boneKg)
                                        + "  脂肪率:"
                                        + String.format("%.1f%%",
                                        bodyfats.bodyfatPercentage)
                                        + "  水分:"
                                        + String.format("%.1f%%",
                                        bodyfats.waterPercentage)
                                        + "  肌肉:"
                                        + String.format("%.1fkg",
                                        bodyfats.muscleKg) + "\r\n");
                    } else if (errorType == HTDataType.ErrorAge) {
                        Log.e("年龄错误", "年龄错误年龄错误年龄错误");
                        mDealDataListener.onFetch(type, 100,"年龄错误");
                    } else if (errorType == HTDataType.ErrorHeight) {
                        Log.e("身高错误", "身高错误身高错误身高错误身");
                        mDealDataListener.onFetch(type, 100,"身高错误");
                    } else if (errorType == HTDataType.ErrorWeight) {
                        Log.e("体重错误", "体重错误体重错误体重错误体重错");
                        mDealDataListener.onFetch(type, 100,"体重错误");
                    } else {
                        mDealDataListener.onFetch(type, 100,"未知错误");
                    }
                    String fat = String.format("%.1f",
                            bodyfats.bodyfatPercentage);
                    String humidity = String.format("%.1f",
                            bodyfats.waterPercentage);
                    double humiditys = Double.parseDouble(humidity);
                    String muscl = String.format("%.1f", bodyfats.muscleKg);
                    double musclea = Double.parseDouble(muscl) / W * 100 - humiditys;
                    String muscles = String.format("%.1f",
                            musclea);
                    String bone = String.format("%.1f", bodyfats.boneKg);
                    int BMR = (int) bodyfats.BMR;
                    String bmis = String.format("%.1f", bodyfats.BMI);

                    // 检测成功给界面组件赋值
                    String result = "体重:" + W + ",脂肪:" + fat + ",水分:" + humidity
                            + ",肌肉:" + muscles + ",骨量:" + bone + ",卡路里:" + BMR
                            + ",BMI:" + bmis + "Kg/㎡" + ",身高:" + uHeight + "m" ;

                    mDealDataListener.onFetch(type, 200, result);
                }
            }

            // 体脂秤1
            if (data.substring(0, 4).equals("A100")) {
                // 09-05 11:40:26.072: E/Bmi(5204): A10019 05F1 0114 0214 016B
                // 001F 0A79 00000000E9

                // kg 体脂% 水分% 肌肉% 骨量% 卡路里
                if (data.length() > 13) {
                    hexStr = "" + chars[6] + chars[7] + chars[8] + chars[9];

                    Log.e("test",
                            "parseInt=" + hexStr + "++++"
                                    + Integer.parseInt(hexStr, 16));

                    float myweight = Integer.parseInt(hexStr, 16);
                    float mybodyfat = Integer.parseInt("" + chars[10]
                            + chars[11] + chars[12] + chars[13], 16);
                    float mywater = Integer.parseInt("" + chars[14] + chars[15]
                            + chars[16] + chars[17], 16);
                    float mymuscle = Integer.parseInt("" + chars[18]
                            + chars[19] + chars[20] + chars[21], 16);
                    float mybonemass = Integer.parseInt("" + chars[22]
                            + chars[23] + chars[24] + chars[25], 16);
                    float mycalorie = Integer.parseInt("" + chars[26]
                            + chars[27] + chars[28] + chars[29], 16);
                    float mybmi = (float) (((float) myweight / 2 / 10) / (float) ((uHeight / 100) * (uHeight / 100)));
                    if (myweight > 0) {

                        BigDecimal bd = new BigDecimal(mybmi);
                        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);

                        String pvss = String.format("%4.2f kg (%4.2f 斤)",
                                myweight / 2 / 10, myweight / 10);


                        // 检测成功给界面组件赋值
                        String result = "体重:" + (myweight / 2 / 10) + ",脂肪:" + (mybodyfat / 10) + ",水分:" + (mywater / 10)
                                + ",肌肉:" + (mymuscle / 10) + ",骨量:" + (mybonemass / 10) + ",卡路里:" + (mycalorie)
                                + ",BMI:" + bd + "Kg/㎡" + ",身高:" + uHeight + "m" ;

                        mDealDataListener.onFetch(type, 200, pvss + "<>"+ result);

                    }
                }
            }
            Log.e("test", data.substring(4, 6));
            // 体脂秤2
            if (data.substring(0, 10).equals("5A000028AA")
                    || (data.substring(0, 2).equals("55") && data.substring(6,
                    8).equals("00"))) {
                if (data.length() > 13) {

                    if (data.substring(0, 10).equals("5A000028AA")) {
                        hexStr = "" + chars[10] + chars[11] + chars[12]
                                + chars[13];

                        Log.e("test",
                                "parseInt=" + hexStr + "++++"
                                        + Integer.parseInt(hexStr, 16));

                        float myweight = Integer.parseInt(hexStr, 16);
                        if (myweight == 0 && tempweight > 0) {
                            myweight = tempweight;
                        }
                        float mybodyfat = Integer.parseInt("" + chars[16]
                                + chars[17] + chars[18] + chars[19], 16);
                        float mywater = Integer.parseInt("" + chars[20]
                                + chars[21] + chars[22] + chars[23], 16);
                        float mymuscle = Integer.parseInt("" + chars[24]
                                + chars[25] + chars[26] + chars[27], 16);
                        float mybonemass = Integer.parseInt("" + chars[28]
                                + chars[29] + chars[30] + chars[31], 16);
                        float mycalorie = Integer.parseInt("" + chars[32]
                                + chars[33] + chars[34] + chars[35], 16);
                        float mybmi = (float) (((float) myweight / 10) / (float) ((uHeight / 100) * (uHeight / 100)));
                        if (myweight > 0) {
                            // results.setText(String
                            // .format("体重：%4.1f kg 体脂：%4.1f %% "
                            // + "身体水分：%4.1f %% 肌肉：%4.1f %% 骨量：%4.1f kg 卡路里：%5f
                            // KCAL BMI：%4.1f ",
                            // myweight / 10, mybodyfat / 10,
                            // mywater / 10, mymuscle / 10,
                            // mybonemass / 10, mycalorie, mybmi));
                            String result = String.format("体重：%4.1f kg 体脂：%4.1f %% 身体水分：%4.1f %% 肌肉：%4.1f %% 骨量：%4.1f kg 卡路里：%5f KCAL BMI：%4.1f "
                            ,myweight / 10, mybodyfat / 10, mywater / 10, mymuscle / 10,mybonemass / 10, mycalorie, mybmi);
                            mDealDataListener.onFetch(type, 200, result);

                        }
                    } else if ((data.substring(0, 2).equals("55") && data
                            .substring(6, 8).equals("00"))) {
                        hexStr = "" + chars[2] + chars[3] + chars[4] + chars[5];
                        float myweight = Integer.parseInt(hexStr, 16);
                        tempweight = myweight;
                        String result = String.format("%4.2f kg ", myweight / 10 + "Kg/㎡");
                        mDealDataListener.onFetch(type, 200, result);
                    }
                }
            }

            byte[] bytes = Utils.getHexBytes(data);
            // CF 03 15 A8 01 C1 00 40 11 01 72 01 02 5B 05 5F
            // CF 03 99 AA 02 41 00 60 18 01 D1 01 02 59 05 FB
            // CF 03 99 AA 02 49 00 00 00 00 00 00 00 00 00 00
            if ((bytes[0] & 0xFF) == 207) {
                //timehandler.removeCallbacks(timerunnable);

                String myweight = String.format("%.1f",
                        (((bytes[4] & 0xFF) * 256) + (bytes[5] & 0xFF)) / 10.0);
                String mybodyfat = String.format("%.1f",
                        (((bytes[6] & 0xFF) * 256) + (bytes[7] & 0xFF)) / 10.0);
                String mywater = String
                        .format("%.1f",
                                (((bytes[12] & 0xFF) * 256) + (bytes[13] & 0xFF)) / 10.0);
                String mymuscle = String
                        .format("%.1f",
                                (((bytes[9] & 0xFF) * 256) + (bytes[10] & 0xFF)) / 10.0);
                String mybonemass = String.format("%.1f",
                        ((bytes[8] & 0xFF) / 10.0));
                String mycalorie = String.format("%.1f",
                        ((bytes[14] & 0xFF) * 256) + (bytes[15] & 0xFF) * 1.0);

                float mybmift = (float) ((Float.valueOf(myweight) / 10) / (float) ((uHeight / 100) * (uHeight / 100)));

                String mybmi = String.format("%.1f", mybmift);

                if (Float.valueOf(myweight) > 0) {
                    // 检测成功给组件赋值
                    String result = String.format("体重：%4.1f kg 体脂：%4.1f %% 身体水分：%4.1f %% 肌肉：%4.1f %% 骨量：%4.1f kg 卡路里：%5f KCAL BMI：%4.1f "
                            ,myweight, mybodyfat,mywater,mymuscle,mybonemass, mycalorie, mybmi);
                    mDealDataListener.onFetch(type, 200, result);
                }
            } else if ((bytes[0] & 0xFF) == 253) {
                //timehandler.removeCallbacks(timerunnable);
                mDealDataListener.onFetch(type, 100, "测量错误，请重新测量.");
            }

        }
        if (data.startsWith("FC42") && data.length() > 9) {


        }
    }

    /**
     * 处理血压计的数据
     */
    public void dealBpmData(String data, DealDataListener listener) {
        type = BlueConstants.BLUE_EQUIP_BPM;
        this.mDealDataListener = listener;
        if (data != null) {
            Log.e("data", data);
            if (data.contains("FDFDFD")) {
                String _date = data.substring(data.indexOf("FDFDFD") + 6);
                if (_date.contains("0D0A")) {
                    _date = _date.substring(0, _date.indexOf("0D0A"));
                    if (_date.equals("0E")) {
                        mDealDataListener.onFetch(type, 100, "血压计异常,联系你的经销商!");
                    } else if (_date.equals("01")) {
                        mDealDataListener.onFetch(type, 100, "人体心跳信号太小或压力突降!");
                    } else if (_date.equals("02")) {
                        mDealDataListener.onFetch(type, 100, "杂讯干扰!");
                    } else if (_date.equals("03")) {
                        mDealDataListener.onFetch(type, 100, "充气时间过长!");
                    } else if (_date.equals("05")) {
                        mDealDataListener.onFetch(type, 100, "测得的结果异常!");
                    } else if (_date.equals("0C")) {
                        mDealDataListener.onFetch(type, 100, "校正异常!");
                    } else if (_date.equals("0B")) {
                        mDealDataListener.onFetch(type, 100, "电源低电压!");
                    }
                }
                CommenBlueUtils.getInstance().writeHexString("FDFDFE060D0A");
            } else if (data.contains("FDFD06")) {
                String _date = data.substring(data.indexOf("FDFD06") + 6);
                if (_date.contains("0D0A")) {
                    _date = _date.substring(0, _date.indexOf("0D0A"));
                    System.out.println("str:" + _date);
                    if (_date.equals("")) {
                        mDealDataListener.onFetch(type, 100, "血压启动!");
                    }
                }

                hh.removeCallbacks(rr);

            } else if (data.contains("FDFD07")) {
                String _date = data.substring(data.indexOf("FDFD07") + 6);
                if (_date.contains("0D0A")) {
                    _date = _date.substring(0, _date.indexOf("0D0A"));
                    System.out.println("str:" + _date);
                    if (_date.equals("")) {
                        Log.e("data", "测试完毕");
                        // Toast.makeText(Jc_Bpm.this, "血压计异常,联系你的经销商!",
                        // Toast.LENGTH_LONG)
                        // .show();
                        if (!iscf2) {
                            iscf2 = true;
                            hh.postDelayed(rr2, 15000);
                        }
                    }

                }
            } else if (data.contains("FDFDFC")) {
                String _date = data.substring(data.indexOf("FDFDFC") + 6);
                if (_date.contains("0D0A")) {
                    _date = _date.substring(0, _date.indexOf("0D0A"));
                    System.out.println("str:" + _date);
                    char[] chars = _date.toCharArray();
                    if (_date.length() > 5) {
                        String _sys, _dia, _pul;
                        _sys = "" + chars[0] + chars[1];
                        _dia = "" + chars[2] + chars[3];
                        _pul = "" + chars[4] + chars[5];

                        int sys = Integer.parseInt(_sys, 16);
                        int dia = Integer.parseInt(_dia, 16);
                        int pul = Integer.parseInt(_pul, 16);

                        String result = dia + " ++舒张压 (mmHg)," + sys + " 收缩压 (mmHg)," + pul + " 心率(次/分钟)";
                        mDealDataListener.onFetch(type, 200, result);
                        if (!iscf) {
                            iscf = true;
                        }
                        App.LeDevices.get(CommenBlueUtils.getInstance().getDeviceId()).Des += ",上次检测结果是："
                                + dia + " 舒张压 (mmHg)" + sys + " 收缩压 (mmHg)"
                                + pul + " 脉率(次/分钟)";
                    }
                }
                CommenBlueUtils.getInstance().writeHexString("FDFDFE060D0A");
            } else if (data.contains("FDFDFB")) {
                String _dates = data.substring(data.indexOf("FDFDFB") + 6);
                if (_dates.contains("0D0A")) {
                    _dates = _dates.substring(0, _dates.indexOf("0D0A"));
                    char[] chars = _dates.toCharArray();
                    System.out.println("str:" + _dates);
                    String result = "压力:"
                            + Integer.parseInt("" + chars[0] + chars[1], 16)
                            + Integer.parseInt("" + chars[2] + chars[3], 16);
                    Log.i("dfasdfdfsgsdf", "压力:" + result);
                    mDealDataListener.onFetch(type, 100, result);
                }
                hh.removeCallbacks(rr);
            } else if (data.contains("FDFDFA050D0A")) {
                CommenBlueUtils.getInstance().writeHexString("FDFDFA050D0A");
                hh.removeCallbacks(rr);
            } else if (data.contains("FDFDFB")) {
                CommenBlueUtils.getInstance().writeHexString("FDFDFA050D0A");
                hh.removeCallbacks(rr);
            } else if (data.contains("A5")) {
                CommenBlueUtils.getInstance().writeHexString("FDFDFA050D0A");
            } else if (data.startsWith("20")) {
                char[] chars = data.toCharArray();
                System.out.println("str:" + data);
                if (data.length() > 3) {
                    String result = "压力:" + Integer.parseInt("" + chars[2] + chars[3], 16);
                    mDealDataListener.onFetch(type, 100, result);
                }
            } else if (data.startsWith("1C00")) {

                char[] chars = data.toCharArray();
                int sys = Integer.parseInt("" + chars[4] + chars[5], 16);
                int dia = Integer.parseInt("" + chars[8] + chars[9], 16);
                int pul = Integer.parseInt("" + chars[16] + chars[17], 16);

                String result = dia + " --舒张压 (mmHg)," + sys + " 收缩压 (mmHg)," + pul + " 心率(次/分钟)";
                mDealDataListener.onFetch(type, 200, result);
            }
        }
    }


    public void removeBpmRRHanler() {
        hh.removeCallbacks(rr);
        Log.i("sddasfasdgg", "这里1");
        hh.postDelayed(rr, 3000);
    }



    String str = "";

    /**
     * 处理尿机的数据
     *
     * @param data
     * @param listener
     */
    public void dealUraData(String data, DealDataListener listener) {
        this.mDealDataListener = listener;
        type = BlueConstants.BLUE_EQUIP_URA;
        String[] LEU = new String[]{"-", "+-", "+", "++", "+++", "/", "/", "/"};
        String[] NIT = new String[]{"-", "+", "/", "/", "/", "/", "/", "/"};
        String[] UBG = new String[]{"-", "+", "++", "+++", "/", "/", "/", "/"};
        String[] PRO = new String[]{"-", "+-", "+", "++", "+++", "++++", "/", "/"};
        String[] PH = new String[]{"5.0", "6.0", "6.5", "7.0", "7.5", "8.0",
                "8.5", "/"};
        String[] BLD = new String[]{"-", "+-", "+", "++", "+++", "/", "/", "/"};
        String[] SG = new String[]{"1.000", "1.005", "1.010", "1.015", "1.020",
                "1.025", "1.030", "/"};
        String[] KET = new String[]{"-", "+-", "+", "++", "+++", "++++", "/", "/"};
        String[] BIL = new String[]{"-", "+", "++", "+++", "/", "/", "/", "/"};
        String[] GLU = new String[]{"-", "+-", "+", "++", "+++", "++++", "/", "/"};
        String[] VC = new String[]{"-", "+-", "+", "++", "+++", "/", "/", "/"};
        String allpvss = "";
        String pvsTime = "";
        if (data != null) {

            if (data.substring(0, 4).equals("938E")) {// 收到包头数据
                char[] chars = data.toCharArray();
                String ml = "" + chars[10] + chars[11];

                if (ml.contains("04")) {
                    str = data;
                    if (data.contains("FFFFFFFFFFFFFF")) {
                        CommenBlueUtils.getInstance().postUraHander(3000);
                    } else {

                    }

                } else if (ml.contains("01")) {
                    mDealDataListener.onFetch(type, 100, "尿液数据测量中，请稍候...");
                }
            } else {
                str += data;
            }

            if (str.contains("FFFFFFFFFFFFFFFFFFFFFFFF10")) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String DataChar = "";
                DataChar = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                        + calendar.get(Calendar.MINUTE) + ":"
                        + calendar.get(Calendar.SECOND);
                mDealDataListener.onFetch(type, 100, "等待新数据中，当前时间：" + DataChar);
                str = "";
                return;
            }

            if (str.contains("FF7FFFFFFF073F00FF3FFF7FFF7F")) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String DataChar = "";
                DataChar = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                        + calendar.get(Calendar.MINUTE) + ":"
                        + calendar.get(Calendar.SECOND);
                mDealDataListener.onFetch(type, 100, "等待新数据中，当前时间：" + DataChar);
                str = "";
                return;
            }

            if (str.length() >= 30) {

                char[] chars = str.toCharArray();
                String ml = "" + chars[10] + chars[11];
                String m2 = "" + chars[8] + chars[9];
                if (ml.contains("04")) {
                    String _date = str.substring(str.indexOf("938E") + 12);

                    _date = _date.substring(0, _date.length() - 2);

                    _date = Utils.hexString2binaryString(_date);

                    chars = _date.toCharArray();

                    if (chars.length == 96 || chars.length == 112) {

                        if (m2.contains("08")) {

                            String nowTime = "日"
                                    + Utils.twototen("" + chars[32]
                                    + chars[33] + chars[34] + chars[35]
                                    + chars[36])
                                    + "月"
                                    + Utils.twototen("" + chars[37]
                                    + chars[38] + chars[39] + chars[40])
                                    + "年"
                                    + Utils.twototen("" + chars[41]
                                    + chars[42] + chars[43] + chars[44]
                                    + chars[45] + chars[46] + chars[47]);

                            String res = "白细胞 LEU:"
                                    + LEU[Utils.twototen("" + chars[50]
                                    + chars[51] + chars[52])]
                                    + "\t\t\t\t";
                            res += "尿潜血 BLD:"
                                    + BLD[Utils.twototen("" + chars[65]
                                    + chars[66] + chars[67])] + "\n";
                            res += "亚硝酸盐 NIT:"
                                    + NIT[Utils.twototen("" + chars[77]
                                    + chars[78] + chars[79])]
                                    + "\t\t\t\t";
                            res += "酮体 KET:"
                                    + KET[Utils.twototen("" + chars[90]
                                    + chars[91] + chars[92])] + "\n";
                            res += "尿胆原 UBG:"
                                    + UBG[Utils.twototen("" + chars[74]
                                    + chars[75] + chars[76])]
                                    + "\t\t\t\t";
                            res += "胆红素 BIL:"
                                    + BIL[Utils.twototen("" + chars[87]
                                    + chars[88] + chars[89])] + "\n";
                            res += "尿蛋白质 PRO:"
                                    + PRO[Utils.twototen("" + chars[71]
                                    + chars[72] + chars[73])]
                                    + "\t\t\t\t";
                            res += "葡萄糖 GLU:"
                                    + GLU[Utils.twototen("" + chars[84]
                                    + chars[85] + chars[86])] + "\n";
                            res += "尿PH值 PH:"
                                    + PH[Utils.twototen("" + chars[68]
                                    + chars[69] + chars[70])]
                                    + "\t\t\t\t";
                            res += "维生素 C:"
                                    + VC[Utils.twototen("" + chars[81]
                                    + chars[82] + chars[83])] + "\n";
                            res += "尿比重 SG:"
                                    + SG[Utils.twototen("" + chars[93]
                                    + chars[94] + chars[95])];

                            String ressss = "白细胞 LEU:/\t\t\t\t";
                            ressss += "尿潜血 BLD:/\n";
                            ressss += "亚硝酸盐 NIT:/\t\t\t\t";
                            ressss += "酮体 KET:/\n";
                            ressss += "尿胆原 UBG:/\t\t\t\t";
                            ressss += "胆红素 BIL:/\n";
                            ressss += "尿蛋白质 PRO:/\t\t\t\t";
                            ressss += "葡萄糖 GLU:/\n";
                            ressss += "尿PH值 PH:/\t\t\t\t";
                            ressss += "维生素 C:/\n";
                            ressss += "尿比重 SG:/";

                            if (ressss.equals(res)) {
                                mDealDataListener.onFetch(type, 100, "数据为空");
                                return;
                            }

                            if (res.equals(allpvss) && nowTime.equals(pvsTime)) {
                                mDealDataListener.onFetch(type, 100, "数据重复");
                                // 清数据
                                CommenBlueUtils.getInstance().writeHexString("938e0400080612");
                            } else {
                                mDealDataListener.onFetch(type, 200, res);

                              /*  String json = "action=doura&data="
                                        + "{\"userid\":\""
                                        + UsersMod.get_id()
                                        + "\",\"leu\":\""
                                        + LEU[Conver.twototen("" + chars[50]
                                        + chars[51] + chars[52])]
                                        + "\",\"nit\":\""
                                        + NIT[Conver.twototen("" + chars[77]
                                        + chars[78] + chars[79])]
                                        + "\",\"ubg\":\""
                                        + UBG[Conver.twototen("" + chars[74]
                                        + chars[75] + chars[76])]
                                        + "\",\"pro\":\""
                                        + PRO[Conver.twototen("" + chars[71]
                                        + chars[72] + chars[73])]
                                        + "\",\"ph\":\""
                                        + PH[Conver.twototen("" + chars[68]
                                        + chars[69] + chars[70])]
                                        + "\",\"bld\":\""
                                        + BLD[Conver.twototen("" + chars[65]
                                        + chars[66] + chars[67])]
                                        + "\",\"sg\":\""
                                        + SG[Conver.twototen("" + chars[93]
                                        + chars[94] + chars[95])]
                                        + "\",\"ket\":\""
                                        + KET[Conver.twototen("" + chars[90]
                                        + chars[91] + chars[92])]
                                        + "\",\"bil\":\""
                                        + BIL[Conver.twototen("" + chars[87]
                                        + chars[88] + chars[89])]
                                        + "\",\"glu\":\""
                                        + GLU[Conver.twototen("" + chars[84]
                                        + chars[85] + chars[86])]
                                        + "\",\"vc\":\""
                                        + VC[Conver.twototen("" + chars[81]
                                        + chars[82] + chars[83])]
                                        + "\",\"source\":0}";

                                HttpPost(1, json);*/
                                CommenBlueUtils.getInstance().writeHexString("938e04000806" + Utils.Checksum("04000806"));

                                pvsTime = nowTime;
                                allpvss = res;

                                App.LeDevices.get(CommenBlueUtils.getInstance().getDeviceId()).Des += ",上次检测结果是："
                                        + allpvss;

                                CommenBlueUtils.getInstance().postUraHander(61000);
                            }

                        }

                        // -----------------招标尿机--------------------
                        if (m2.contains("09")) {

                            String nowTime = "日"
                                    + Utils.twototen("" + chars[28]
                                    + chars[27] + chars[26] + chars[25]
                                    + chars[24])
                                    + "月"
                                    + Utils.twototen("" + chars[16]
                                    + chars[31] + chars[30] + chars[29])
                                    + "年"
                                    + Utils.twototen("" + chars[17]
                                    + chars[18] + chars[19] + chars[20]
                                    + chars[21] + chars[22] + chars[23]);

                            String res = "白细胞 LEU:"
                                    + LEU[Utils.twototen("" + chars[111]
                                    + chars[96] + chars[97])]
                                    + "\t\t\t\t";
                            res += "尿潜血 BLD:"
                                    + BLD[Utils.twototen("" + chars[85]
                                    + chars[86] + chars[87])] + "\n";
                            res += "亚硝酸盐 NIT:"
                                    + NIT[Utils.twototen("" + chars[98]
                                    + chars[99] + chars[100])]
                                    + "\t\t\t\t";
                            res += "酮体 KET:"
                                    + KET[Utils.twototen("" + chars[95]
                                    + chars[80] + chars[81])] + "\n";
                            res += "尿胆原 UBG:"
                                    + UBG[Utils.twototen("" + chars[74]
                                    + chars[75] + chars[76])]
                                    + "\t\t\t\t";
                            res += "胆红素 BIL:"
                                    + BIL[Utils.twototen("" + chars[82]
                                    + chars[83] + chars[84])] + "\n";
                            res += "尿蛋白质 PRO:"
                                    + PRO[Utils.twototen("" + chars[89]
                                    + chars[90] + chars[91])]
                                    + "\t\t\t\t";
                            res += "葡萄糖 GLU:"
                                    + GLU[Utils.twototen("" + chars[92]
                                    + chars[93] + chars[94])] + "\n";
                            res += "尿PH值 PH:"
                                    + PH[Utils.twototen("" + chars[101]
                                    + chars[102] + chars[103])]
                                    + "\t\t\t\t";
                            res += "维生素 C:"
                                    + VC[Utils.twototen("" + chars[105]
                                    + chars[106] + chars[107])] + "\n";
                            res += "尿比重 SG:"
                                    + SG[Utils.twototen("" + chars[108]
                                    + chars[109] + chars[110])];

                            String ressss = "白细胞 LEU:/\t\t\t\t";
                            ressss += "尿潜血 BLD:/\n";
                            ressss += "亚硝酸盐 NIT:/\t\t\t\t";
                            ressss += "酮体 KET:/\n";
                            ressss += "尿胆原 UBG:/\t\t\t\t";
                            ressss += "胆红素 BIL:/\n";
                            ressss += "尿蛋白质 PRO:/\t\t\t\t";
                            ressss += "葡萄糖 GLU:/\n";
                            ressss += "尿PH值 PH:/\t\t\t\t";
                            ressss += "维生素 C:/\n";
                            ressss += "尿比重 SG:/";

                            if (ressss.equals(res)) {
                                mDealDataListener.onFetch(type, 100, "数据为空");
                                return;
                            }


                            if (res.equals(allpvss) && nowTime.equals(pvsTime)) {
                                mDealDataListener.onFetch(type, 100, "数据重复");
                                // 清数据
                                CommenBlueUtils.getInstance().writeHexString("938e0400080612");

                            } else {
                                mDealDataListener.onFetch(type, 200, res);
                             /*
                                String json = "action=doura&data="
                                        + "{\"userid\":\""
                                        + UsersMod.get_id()
                                        + "\",\"leu\":\""
                                        + LEU[Conver.twototen("" + chars[50]
                                        + chars[51] + chars[52])]
                                        + "\",\"nit\":\""
                                        + NIT[Conver.twototen("" + chars[77]
                                        + chars[78] + chars[79])]
                                        + "\",\"ubg\":\""
                                        + UBG[Conver.twototen("" + chars[74]
                                        + chars[75] + chars[76])]
                                        + "\",\"pro\":\""
                                        + PRO[Conver.twototen("" + chars[71]
                                        + chars[72] + chars[73])]
                                        + "\",\"ph\":\""
                                        + PH[Conver.twototen("" + chars[68]
                                        + chars[69] + chars[70])]
                                        + "\",\"bld\":\""
                                        + BLD[Conver.twototen("" + chars[65]
                                        + chars[66] + chars[67])]
                                        + "\",\"sg\":\""
                                        + SG[Conver.twototen("" + chars[93]
                                        + chars[94] + chars[95])]
                                        + "\",\"ket\":\""
                                        + KET[Conver.twototen("" + chars[90]
                                        + chars[91] + chars[92])]
                                        + "\",\"bil\":\""
                                        + BIL[Conver.twototen("" + chars[87]
                                        + chars[88] + chars[89])]
                                        + "\",\"glu\":\""
                                        + GLU[Conver.twototen("" + chars[84]
                                        + chars[85] + chars[86])]
                                        + "\",\"vc\":\""
                                        + VC[Conver.twototen("" + chars[81]
                                        + chars[82] + chars[83])]
                                        + "\"}";

                                HttpPost(1, json);*/
                                // 删除
                                Log.e("发送", "938E0400090613");
                                CommenBlueUtils.getInstance().writeHexString("938E0400090613");
                                pvsTime = nowTime;
                                allpvss = res;

                                App.LeDevices.get(CommenBlueUtils.getInstance().getDeviceId()).Des += ",上次检测结果是："
                                        + allpvss;
                                CommenBlueUtils.getInstance().setBTrue();
                                CommenBlueUtils.getInstance().postUraHander(5000);
                            }

                        }

                    } else {
                        mDealDataListener.onFetch(type, 100, "数据有误");
                    }
                }
                str = "";
            }
        }
    }

    /**
     * 处理体温计的数据
     * 备注：这个体温计的数据是由设备发送了两段数据过来配对的，所以会有一个追加操作
     *
     * @param data
     */
    private StringBuilder sb = new StringBuilder();

    public void setTemString(){
        sb.setLength(0);
    }
    public void dealTemData(String data, DealDataListener listener) {
        type = BlueConstants.BLUE_EQUIP_TEM;
        this.mDealDataListener = listener;
        Log.i("sjkljklsjadkll", "数据---》" + data);
        sb = sb.append(data);
        if (data != null && !data.equals("")) {
            char[] chars = data.toCharArray();
            String hexStr = new String();
            // 体温
            if (data.substring(0, 2).equals("FF")) {
                if (data.length() > 7) {
                    hexStr = "" + chars[2] + chars[3] + chars[4] + chars[5];
                    Log.d("test", "parseInt=" + Integer.parseInt(hexStr, 16));
                    float myweight = Integer.parseInt(hexStr, 16);
                    if ((myweight / 10) >= 29 && (myweight / 10) <= 46) {
                        //pvss = String.format("%4.1f °C", myweight / 10);
                        String result = (myweight / 10) + "";
                        mDealDataListener.onFetch(type, 200, "体温：" + result);
                    } else {
                        mDealDataListener.onFetch(type, 100, "测量温度不正常,请重新测量!");
                    }
                }

                if (data.length() > 4 && data.substring(0, 4).equals("55AA")) {
                    if (data.length() > 7) {
                        hexStr = "" + chars[8] + chars[9] + chars[10]
                                + chars[11];
                        if (!hexStr.equals("9999") && !hexStr.equals("FFFF")) {
                            Log.d("test",
                                    "parseInt=" + Integer.parseInt(hexStr, 16));
                            float myweight = Integer.parseInt(hexStr, 16);
                            // pvss = String.format("%4.2f °C", myweight / 100);
                            if ((myweight / 100) >= 29 && (myweight / 100) <= 46) {
                                String result = (myweight / 10) + "";
                                mDealDataListener.onFetch(type, 200, "体温：" + result);
                            } else {
                                mDealDataListener.onFetch(type, 100, "测量温度不正常,请重新测量!");
                            }
                        } else {
                            if (hexStr.equals("9999")) {
                                mDealDataListener.onFetch(type, 100, "低于可测量范围");
                            } else {
                                mDealDataListener.onFetch(type, 100, "高于可测量范围");
                            }
                        }
                    }
                }
            }
            //家康体温枪
            String str = new String(sb);
            if (str.length() > 15 && str.trim().contains("AF6A")) {
                str = str.trim().substring(str.indexOf("AF6A"));
                if (str.length() > 15) {
                    chars = str.toCharArray();
                    hexStr = "" + chars[8] + chars[9] + chars[10] + chars[11];
                    Log.d("test", "parseInt=" + Integer.parseInt(hexStr, 16));
                    float myweight = Integer.parseInt(hexStr, 16);
                    DecimalFormat formater = new DecimalFormat("#0.#");
                    formater.setRoundingMode(RoundingMode.FLOOR);
                    if ((myweight / 100) >= 29 && (myweight / 100) <= 46) {
                        String result = formater.format(myweight / 100);
                        mDealDataListener.onFetch(type, 200, "体温：" + result);
                    } else {
                        mDealDataListener.onFetch(type, 100, "测量温度不正常,请重新测量!");
                    }
                    sb.delete(0, sb.length());
                }
            }

            // 福达康体温枪
            if (data.substring(0, 2).equals("FE") && data.contains("6A725A")
                    && data.length() > 15
                    && data.substring(12, 14).equals("00")) {
                hexStr = "" + chars[8] + chars[9] + chars[10] + chars[11];
                Log.e("test1", "parseInt=" + Integer.parseInt(hexStr, 16));
                float myweight = Integer.parseInt(hexStr, 16);
                if ((myweight / 100) >= 29 && (myweight / 100) <= 46) {
                    String result = (myweight / 100) + "";
                    mDealDataListener.onFetch(type, 200, "体温：" + result);
                } else {
                    mDealDataListener.onFetch(type, 100, "测量温度不正常,请重新测量!");
                }
            }
            //体达体温枪
            if (data.length() > 11) {
                if (data.substring(data.length() - 6).contains("000D0A")) {
                    if (data.substring(0, 4).contains("426F")) {//体温
                        data = Utils.convertHexToString(data);
                        if (data.contains("Body")) {
                            if (data.contains("C")) {
                                data = data.substring(data.indexOf("Body:") + 5, data.indexOf("C"));
                                mDealDataListener.onFetch(type, 200, "体温：" + data);
                            } else if (data.contains("F")) {
                                data = data.substring(data.indexOf("Body:") + 5,
                                        data.indexOf("F"));
                                float tem = Float.parseFloat(data);
                                tem = (tem - 32) * 5 / 9;
                                data = new DecimalFormat("0.0").format(tem);
                                mDealDataListener.onFetch(type, 200, "体温：" + data);
                            }
                            if (Double.parseDouble(data) >= 29 && Double.parseDouble(data) <= 46) {

                            } else {
                                mDealDataListener.onFetch(type, 100, "测量温度不正常,请重新测量!");
                            }
                        }
                    } else if (data.substring(0, 4).contains("526F")) {//室温
                        mDealDataListener.onFetch(type, 100, "你现在的模式是室温，请把模式调成体温！");
                    } else if (data.substring(0, 4).contains("5375")) {//表面
                        mDealDataListener.onFetch(type, 100, "你现在的模式是表面，请把模式调成体温！");
                    } else if (data.contains("45724C000D0A")) {
                        mDealDataListener.onFetch(type, 100, "测量温度过低，请重新测量！");
                    } else if (data.contains("457248000D0A")) {
                        mDealDataListener.onFetch(type, 100, "测量温度过高，请重新测量！");
                    }
                }
            }

        }

    }
}
