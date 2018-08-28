package com.healon.up20user;

import android.os.Environment;

public class Constants {
    //厂商名字
    public static final String MANUFACTURER_NAME = "HealSon";
    //供应商ID
    public static final int VENDOR_ID = 1204;
    //产品ID
    public static final int PRODUCT_ID = 34323;
    // USB_DEVICE
    public static final String ACTION_DEVICE_PERMISSION = "com.Ultrasound.UP20.DEVICE_PERMISSION";
    // UDISK
    public static final String ACTION_UDISK_PERMISSION = "com.Ultrasound.UP20.UDISK_PERMISSION";
    // 预置文件路径
    public static final String PATH_SYSFILE = "/Ultrasound/UP20/sysFile";
    // 病历路径
    public static final String PATH_PATIENT = "/Ultrasound/UP20/patient";
    //B超图片存放路径
    public final static String FINGERPRINT_ALBUM_PATH = Environment.getExternalStorageDirectory() + "/com.lianyi.app/BC_IMAGE/";
    //是否连接探头
    public static final boolean DEVICE_REAL = false;
    //是否显示帧频
    public static final boolean IS_SHOW_FPS = true;
    public static boolean INIT_SEEKBAR = false;

    public static final int UPDATE_BUTTON_STATE = 1;
    public static final int UPDATE_PATIENT = 2;
    public static final int DEVICE_PERMISSION_GRANTED_SUCCESS = 4;//点击了确定
    public static final int DEVICE_PERMISSION_GRANTED_FAIL = 5;
    public static final int RECONNECT_DEVICE = 6;
    public static final int DISCONNECT_DEVICE = 7;
    public static final int CONNECT_UDISK = 8;
    public static final int DISCONNECT_UDISK = 9;//9
    public static final int UDISK_PERMISSION_GRANTED_FAIL = 10;//
    public static final int UDISK_PERMISSION_GRANTED_SUCCESS = 11;
    public static final int SHOW_HARD_CONFIG_PROGRESS = 12;

    //广播只进入一次
    public static boolean FRIST_PERMISSION=false;
    public static final int INIT_NO = 100;
    //连接设备完成（失败/成功）
    public static final int CONNECT_FINISH = 0x00008001;

}
