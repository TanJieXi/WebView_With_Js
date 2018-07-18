package com.example.newblue.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.newblue.App;
import com.example.newblue.interfaces.DealDataListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by TanJieXi on 2018/3/30.
 */

public class DealDataUtils {
    private DealDataListener mDealDataListener;
    private static volatile DealDataUtils instance = null;
    private DealDataUtils() {

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
        this.mDealDataListener = listener;
        if (data != null) {
            System.out.println("data:" + data);
            char[] chars = data.toCharArray();
            String hexStr = "";

            if (data.contains("55AA03")) {
                if (data.contains("55AA03B100B4")) {
                    mDealDataListener.onFetch(100, "血氧脉率测量中，请稍候...");
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
                        mDealDataListener.onFetch(200, result);
                        // 检测成功给界面组件赋值 袁明全加 20151001
                    }
                }
            }
        }
    }

    String str = "";
    /**
     * 处理尿机的数据
     * @param data
     * @param listener
     */
    public void dealUraData(String data, DealDataListener listener) {
        this.mDealDataListener = listener;

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
                    mDealDataListener.onFetch(100, "尿液数据测量中，请稍候...");
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
                mDealDataListener.onFetch(100, "等待新数据中，当前时间：" + DataChar);
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
                mDealDataListener.onFetch(100, "等待新数据中，当前时间：" + DataChar);
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
                                mDealDataListener.onFetch(100, "数据为空" );
                                return;
                            }

                            if (res.equals(allpvss) && nowTime.equals(pvsTime)) {
                                mDealDataListener.onFetch(100, "数据重复" );
                                // 清数据
                                CommenBlueUtils.getInstance().writeHexString("938e0400080612");
                            } else {
                                mDealDataListener.onFetch(200, res );

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
                                mDealDataListener.onFetch(100, "数据为空" );
                                return;
                            }


                            if (res.equals(allpvss) && nowTime.equals(pvsTime)) {
                                mDealDataListener.onFetch(100, "数据重复" );
                                // 清数据
                                CommenBlueUtils.getInstance().writeHexString("938e0400080612");

                            } else {
                                mDealDataListener.onFetch(200, res );
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
                        mDealDataListener.onFetch(100, "数据有误" );
                    }
                }
                str = "";
            }
        }
    }

    /**
     * 处理体温计的数据
     * 备注：这个体温计的数据是由设备发送了两段数据过来配对的，所以会有一个追加操作
     * @param data
     */
    private StringBuilder sb = new StringBuilder();
    public void dealTemData(String data, DealDataListener listener) {
        this.mDealDataListener = listener;
        Log.i("sjkljklsjadkll", "数据---》" + data);
        // 11-26 18:54:47.796: D/test(8170): data=AF6A725A0E440088
        sb = sb.append(data);
        if (data != null && !data.equals("")) {
            char[] chars = data.toCharArray();
            String hexStr = new String();
            // 体温
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
                        mDealDataListener.onFetch(200, "体温：" + result);
                    } else {
                        mDealDataListener.onFetch(100, "测量温度不正常,请重新测量!");
                    }
                    sb.delete(0,sb.length());
                }
            }
        }

    }



}
