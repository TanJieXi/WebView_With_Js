package com.healon.up20user.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;

import com.healon.up20user.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 */

public class UtilsUsbAuthority {

    public static UsbManager getManager(Context context) {
        return (UsbManager) context.getSystemService(Context.USB_SERVICE);
    }

    public static UsbDevice getDevice(Context context) {
        UsbDevice device = null;
        UsbManager usbManager = getManager(context);
        HashMap<String, UsbDevice> map = usbManager.getDeviceList();
        if (map.size() != 0 && map != null) {
            for (Map.Entry<String, UsbDevice> entry : map.entrySet()) {
                UsbDevice usbDevice = entry.getValue();
                if (usbDevice.getVendorId() == Constants.VENDOR_ID
                        && usbDevice.getProductId() == Constants.PRODUCT_ID) {
                    device = usbDevice;
                }
            }
        }
        return device;
    }

    public static boolean hasPermission(@NonNull UsbDevice usbDevice, Context context) {
        boolean ok = false;
        UsbManager usbManager = getManager(context);
        ok = usbManager.hasPermission(usbDevice);
        return ok;
    }

    public static void requestPermission(@NonNull UsbDevice usbDevice, Context context) {
        if (usbDevice != null) {
            UsbManager usbManager = getManager(context);
            PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(Constants.ACTION_DEVICE_PERMISSION), 0);
            usbManager.requestPermission(usbDevice, permissionIntent);
        }
    }
}
