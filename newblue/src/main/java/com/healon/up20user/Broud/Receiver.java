package com.healon.up20user.Broud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;

import com.healon.up20user.Constants;


/**
 * Created by Pretend on 2017/9/13 0013.
 */

public class Receiver extends BroadcastReceiver {
    private Handler m_handler;
    public Receiver(Handler handler) {
        this.m_handler = handler;
    }

    public void clean(){
        if (m_handler != null) m_handler = null;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        switch (intent.getAction()) {
            case Constants.ACTION_UDISK_PERMISSION:
                boolean uDiskPermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                if (uDiskPermission) {
                    m_handler.sendEmptyMessage(Constants.UDISK_PERMISSION_GRANTED_SUCCESS);
                } else {
                    m_handler.sendEmptyMessage(Constants.UDISK_PERMISSION_GRANTED_FAIL);//9
                }
                break;
            case Constants.ACTION_DEVICE_PERMISSION:
                boolean devicePermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                if (Constants.FRIST_PERMISSION) {
                    return;
                }
                if (devicePermission) {
                    m_handler.sendEmptyMessage(Constants.DEVICE_PERMISSION_GRANTED_SUCCESS);
                    Constants.FRIST_PERMISSION=true;
                } else {
                    m_handler.sendEmptyMessage(Constants.DEVICE_PERMISSION_GRANTED_FAIL);
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED://插入
                if (usbDevice.getVendorId() == Constants.VENDOR_ID
                        && usbDevice.getProductId() == Constants.PRODUCT_ID) {
                    m_handler.sendEmptyMessage(Constants.RECONNECT_DEVICE);
                } else {
                    m_handler.sendEmptyMessage(Constants.CONNECT_UDISK);//8
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED://拔出
                if (usbDevice.getVendorId() == Constants.VENDOR_ID
                        && usbDevice.getProductId() == Constants.PRODUCT_ID) {
                    m_handler.sendEmptyMessage(Constants.DISCONNECT_DEVICE);
                } else {
                    m_handler.sendEmptyMessage(Constants.DISCONNECT_UDISK);
                }
                break;
        }
    }
}
