package com.healon.up20user.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;

import com.yanzhenjie.permission.AndPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 操作SD卡
 * Created by Administrator on 2017/7/19 0019.
 */

public class UtilsSDCard {
    public static void requestPermission(final Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .send();
        }
    }

    // 判断允许读写
    public static boolean isRWEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // 获取SDCard文件路径
    public static String root() {
        String ok = null;
        if (UtilsSDCard.isRWEnable()) {
            ok = Environment.getExternalStorageDirectory().getPath();
        }
        return ok;
    }

    // 获取SDCard总容量大小(bytes)
    public static double total() {
        double ok = 0.0;
        if (!UtilsSDCard.root().isEmpty()) {
            StatFs statfs = new StatFs(UtilsSDCard.root());
            ok = statfs.getBlockCount() * statfs.getBlockSize();
        }
        return ok;
    }

    // 获取SDCard总容量大小(bytes)
    public static String totalString() {
        String ok = null;
        {
            double free = UtilsSDCard.total();
            if (free >= 1024.0 * 1024 * 1024) {
                free /= 1024.0 * 1024 * 1024;
                ok = UtilsValueOf.doubleToString(free, 2) + "GB";
            } else {
                free /= 1024.0 * 1024;
                ok = free + "MB";
            }
        }
        return ok;
    }

    // 获取SDCard可用容量大小(bytes)
    public static double free() {
        double ok = 0.0;
        if (!UtilsSDCard.root().isEmpty()) {
            StatFs statfs = new StatFs(UtilsSDCard.root());
            ok = (double) statfs.getAvailableBlocks() * (double) statfs.getBlockSize();
        }
        return ok;
    }

    // 获取SDCard可用容量大
    public static String freeString() {
        String ok = null;
        {
            double free = UtilsSDCard.free();
            if (free >= 1024.0 * 1024 * 1024) {
                free /= 1024.0 * 1024 * 1024;
                ok = UtilsValueOf.doubleToString(free, 2) + "GB";
            } else {
                free /= 1024.0 * 1024;
                ok = free + "MB";
            }
        }
        return ok;
    }

    public static String createFile(String _path, String imgname, Bitmap mSignBitmap) {
        ByteArrayOutputStream baos = null;
        try {
            File dirFile = new File(_path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            _path = _path + imgname + ".png";
            baos = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }
}
