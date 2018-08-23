package com.example.newblue.jumppart;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.creative.base.BLUReader;
import com.creative.base.BLUSender;
import com.creative.base.BaseDate.ECGData;
import com.creative.base.BaseDate.Wave;
import com.creative.ecg.ECG;
import com.creative.ecg.IECGCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StaticReceive {

    /**
     * PC80B系列协议解析
     */
    private static ECG ecg;

    /**
     * 通知上层各种数据消息
     */
    private static Handler mHandler;

//	protected  Context mContext;

    /**
     * 开始接收数据
     *
     * @param bluName
     */
    public static void startReceive(Context context, String bluName,
                                    BLUReader iReader, BLUSender iSender, Handler _handler) {
        if (bluName != null && !bluName.equals("")) {
            start = true;
            mHandler = _handler;
//			mContext = context;
            ecg = new ECG(iReader, iSender, new ECGCallBack());
            ecg.Start();
            ecg.QueryDeviceVer();
        }
    }

    /**
     * 停止接收数据
     */
    public static void StopReceive() {
        start = false;
        if (ecg != null) {
            ecg.Stop();
            ecg = null;
        }
        HWMajor = HWMinor = SWMajor = SWMinor = ALMajor = ALMinor = 0;
//		mHandler.removeMessages(MSG_DATA_ECG_STATUS_CH);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 暂停数据接收
     */
    public static void Pause() {
        pause = true;
        if (ecg != null) {
            ecg.Pause();
        }
    }

    /**
     * 恢复数据接收
     */
    public static void Continue() {
        pause = false;
        if (ecg != null) {
            ecg.Continue();
        }
    }

    public static boolean pause = false;

    public static boolean start = false;

    public static void setmHandler(Handler mHandler) {
        StaticReceive.mHandler = mHandler;
    }

    /**
     * 数据类型key——心电文件
     */
    public static final String DATATYPEKEY_ECG_FILE = "ecgFile";
    /**
     * 数据类型key——心电波形
     */
    public static final String DATATYPEKEY_ECG_WAVE = "ecgwave";

    /**
     * 设备数据消息——设备ID
     */
    public static final int MSG_DATA_DEVICE_ID = 0x201;

    /**
     * 设备数据消息——设备版本信息
     */
    public static final int MSG_DATA_DEVICE_VS = 0x202;

    /**
     * 设备数据消息——心电波形数据
     */
    public static final int MSG_DATA_ECG_WAVE = 0x20d;
    /**
     * 消息 电池电量为0
     */
    public static final int BATTERY_ZERO = 0x302;
    /**
     * 设备数据消息——电池电量
     */
    public static final int MSG_DATA_BATTERY = 0x20e;
    /**
     * 设备数据消息——心电测量状态改变
     */
    public static final int MSG_DATA_ECG_STATUS_CH = 0x209;

    /**
     * 设备数据消息——搏动标记
     */
    public static final int MSG_DATA_PULSE = 0x20f;

    /**
     * 设备数据消息——搏动标记
     */
    public static final int MSG_DATA_TIMEOUT = 0x210;
    /**
     * 取消搏动标记
     */
    public static final int RECEIVEMSG_PULSE_OFF = 0x115;
    /**
     * 设备版本信息
     */
    public static int HWMajor, HWMinor, SWMajor, SWMinor, ALMajor, ALMinor;

    /**
     * 保存波形数据
     */
    public static List<Wave> DRAWDATA = new ArrayList<Wave>();

    /**
     * 保存血氧波形数据 用于绘制血氧柱状图
     */
    public static List<Wave> SPOWAVE = new ArrayList<Wave>();

    /**
     * 心电测量结果
     */
    private static String[] measureResult = new String[]{"波形未见异常",
            "波形疑似心跳稍快,请注意休息",
            "波形疑似心跳过快,请注意休息",
            "波形疑似阵发性心跳过快,请咨询医生",
            "波形疑似心跳稍缓,请注意休息",
            "波形疑似心跳过缓,请注意休息",
            "波形疑似偶发心跳间期缩短,请咨询医生",
            "波形疑似心跳间期不规则,请咨询医生",
            "波形疑似心跳稍快伴有偶发心跳间期缩短,请咨询医生",
            "波形疑似心跳稍缓伴有偶发心跳间期缩短,请咨询医生",
            "波形疑似心跳稍缓伴有心跳间期不规则,请咨询医生",
            "波形有漂移,请重新测量",
            "波形疑似心跳过快伴有波形漂移,请咨询医生",
            "波形疑似心跳过缓伴有波形漂移,请咨询医生",
            "波形疑似偶发心跳间期缩短伴有波形漂移,请咨询医生",
            "波形疑似心跳间期不规则伴有波形漂移,请咨询医生",
            "信号较差请重新测量",
            "分析结果出现错误"};


    private static class ECGCallBack implements IECGCallBack {

        @Override
        public void OnConnectLose() {
        }

        @Override
        public void OnGetDeviceVer(int nHWMajor, int nHWMinor, int nSWMajor,
                                   int nSWMinor, int nALMajor, int nALMinor) {
            HWMajor = nHWMajor;
            HWMinor = nHWMinor;
            SWMajor = nSWMajor;
            SWMinor = nSWMinor;
            ALMajor = nALMajor;
            ALMinor = nALMinor;
        }

        @Override
        public void OnGetPower(int nPower) {
            mHandler.obtainMessage(MSG_DATA_BATTERY, nPower, 0).sendToTarget();
        }

        @Override
        public void OnGetRealTimeResult(String time, int nTransMode,
                                        int nResult, int arg3) {
            Message msg = mHandler.obtainMessage();
            msg.what = MSG_DATA_ECG_STATUS_CH;
            msg.arg1 = 6;
            Bundle data = new Bundle();
            data.putInt("nTransMode", nTransMode);
            data.putString("nResult", measureResult[nResult]);
            data.putInt("nHR", arg3);
            data.putString("time", time);
            msg.setData(data);
            mHandler.sendMessage(msg);
        }

        @Override
        public void OnGetRealTimeMeasure(boolean arg0, ECGData arg1, int arg2,
                                         int arg3, int arg4, int arg5) {
            Message msg = mHandler.obtainMessage();
            msg.what = MSG_DATA_ECG_STATUS_CH;
            msg.arg1 = 5;

            ArrayList s = new ArrayList();

            for (Wave w : arg1.data) {
                String f = String.format("%.2f", w.data / 100.0);
                s.add(Float.valueOf(f));
            }

            //Log.e("=====", ""+s);

            Bundle data = new Bundle();
            data.putBoolean("bLeadoff", arg0);
            data.putInt("nTransMode", arg2);
            data.putInt("nHR", arg3);
            data.putInt("nPower", arg4);
            data.putInt("nGain", arg5);

            data.putStringArrayList("nData", s);
            msg.setData(data);
            mHandler.sendMessage(msg);
            DRAWDATA.addAll(arg1.data);
        }

        @Override
        public void OnGetRealTimePrepare(boolean arg0, ECGData arg1, int arg2) {
            Message msg = mHandler.obtainMessage();
            msg.what = MSG_DATA_ECG_STATUS_CH;
            msg.arg1 = 4;
            Bundle data = new Bundle();
            data.putBoolean("bLeadoff", arg0);
            data.putInt("nGain", arg2);
            DRAWDATA.addAll(arg1.data);
            msg.setData(data);
            mHandler.sendMessage(msg);

            for (Wave w : arg1.data) {
                String f = String.format("%.2f", w.data / 100.0);
                Log.e("=====", "" + f);
            }

        }

        @Override
        public void OnGetRequest(String sDeviceID, String sProductID,
                                 int nSmoothingMode, int nTransMode) {
            mHandler.obtainMessage(MSG_DATA_ECG_STATUS_CH, 7, nSmoothingMode,
                    nTransMode).sendToTarget();
        }

        @Override
        public void OnGetFileTransmit(int arg0, Vector<Integer> arg1) {
            System.out.println("OnGetFileTransmit " + arg0);
            mHandler.obtainMessage(MSG_DATA_ECG_STATUS_CH, arg0, 0, arg1)
                    .sendToTarget();
        }

        @Override
        public void OnReceiveTimeOut(int arg0) {
            mHandler.obtainMessage(MSG_DATA_ECG_STATUS_CH, MSG_DATA_TIMEOUT, 0,
                    0).sendToTarget();
        }
    }

}
