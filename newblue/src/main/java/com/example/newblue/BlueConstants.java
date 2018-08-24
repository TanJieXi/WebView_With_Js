package com.example.newblue;

/**
 * Created by TanJieXi on 2018/7/26.
 * 常量
 */
public class BlueConstants {
    /**
     *  体温枪
     */
    public static final String BLUE_EQUIP_TEM = "tem";
    /**
     *  血氧
     */
    public static final String BLUE_EQUIP_OXI = "oxi";
    /**
     *  尿机
     */
    public static final String BLUE_EQUIP_URA = "ura";
    /**
     *  血压计
     */
    public static final String BLUE_EQUIP_BPM = "bpm";
    /**
     *  体重体脂秤
     */
    public static final String BLUE_EQUIP_BMI = "bmi";
    /**
     *  血糖计
     */
    public static final String BLUE_EQUIP_BGM = "bgm";
    /**
     *  血红蛋白检测
     */
    public static final String BLUE_EQUIP_HGB = "hgb";
    /**
     *  糖化血红蛋白检测
     */
    public static final String BLUE_EQUIP_GLHGB = "glhgb";
    /**
     *  血脂
     */
    public static final String BLUE_EQUIP_BFT = "bft";
    /**
     *  血糖尿酸总胆固醇三合一
     */
    public static final String BLUE_EQUIP_BUA = "bua";
    /**
     *  第二种血压计，使用广播找设备的
     */
    public static final String BLUE_EQUIP_BPM_TWO = "NIBP";
    /**
     *  身份证阅读器
     */
    public static final String BLUE_EQUIP_IDCARD_READER = "idr";

    public static final String BULEPRITER_SETTING = "bulepritersetting";//蓝牙打印机设置sp表名
    public static final String BULEPRITER_OPEN = "bulepriterseopen";//蓝牙打印机开关设置
    public static final String BULEPRITER_NUMBER = "bulepriternumber";//蓝牙打印机条码
    public static final String BULEPRITER_PAGE = "bulepriterpage";//蓝牙打印机一次打印张数，默认1张
    /**
     * 蓝牙打印机需要打印传的参数
     */
    public static final String BLUE_PRINT_NAME = "BLUE_PRINT_NAME";  //姓名
    public static final String BLUE_PRINT_BAR_CODE = "BLUE_PRINT_BAR_CODE"; //条码
    public static final String BLUE_PRINT_SEX = "BLUE_PRINT_SEX";  //性别
    public static final String BLUE_PRINT_PRINTNUM = "BLUE_PRINT_PRINTNUM";  //打印数量

    /**
     * 心电的结果
     */
    public static final int ECM_JUMP_CODE = 1111;
    public static final int ECMII_JUMP_CODE = 1112;
    public static final String ECM_HEART_RATE = "ECM_HEART_RATE"; //心率
    public static final String ECM_RESULT = "ECM_RESULT"; //心电结果
    public static final String ECM_XML_DATA = "ECM_XML_DATA"; //心电xml数据
    public static final String ECM_XML_URL = "ECM_XML_URL"; //十二导心电是地址
    public static final String ECM_XML_LOOK_URL = "ECM_XML_LOOK_URL"; //十二导心电查看的地址
    public static final String ECMII_STRING_URL = "ECMII_STRING_URL"; //查看十二导的图

    /**
     * 胎心仪
     */
    public static final int BAB_JUMP_CODE = 1113;
    public static final String BAB_END_RESULT = "BAB_END_RESULT"; //胎心仪的结果
    public static final String BAB_END_XMLDATA = "BAB_END_XMLDATA"; //胎心仪的xml数据
}
