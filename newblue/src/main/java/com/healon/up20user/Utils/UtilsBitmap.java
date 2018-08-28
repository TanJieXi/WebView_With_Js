package com.healon.up20user.Utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

/**
 * 操作Bitmap
 * Created by Administrator on 2017/7/19 0019.
 */

public class UtilsBitmap {
    // 截屏
    public static Bitmap capture(final View view) {
        Bitmap ok = null;
        if (view != null) {
            // 设置是否可以进行绘图缓存
            view.setDrawingCacheEnabled(true);
            // 如果视图缓存无法，强制构建绘图缓存
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            if (bitmap != null) ok = Bitmap.createBitmap(bitmap);
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
        }
        return ok;
    }

    // 截屏
    public static Bitmap capture(final Activity activity) {
        Bitmap ok = null;
        if (activity != null) {
            View view = activity.getWindow().getDecorView();
            if (view != null) {
                // 设置是否可以进行绘图缓存
                view.setDrawingCacheEnabled(true);
                // 如果视图缓存无法，强制构建绘图缓存
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                if (bitmap != null) ok = Bitmap.createBitmap(bitmap);
                view.setDrawingCacheEnabled(false);
                view.destroyDrawingCache();
            }
        }
        return ok;
    }

    public static Bitmap union(final Bitmap src, final Bitmap dst, int dx, int dy) {
        Bitmap ok = Bitmap.createBitmap(dst);
        if ((src != null) && (dst != null)) {
            ok = Bitmap.createBitmap(dst.getWidth(), dst.getHeight(), Bitmap.Config.ARGB_8888);
            if (ok != null) {
                Canvas canvas = new Canvas(ok);
                canvas.drawBitmap(dst, 0, 0, null);
                canvas.drawBitmap(src, dx, dy, null);
                canvas.save(Canvas.ALL_SAVE_FLAG);
                canvas.restore();
            }
        }
        return ok;
    }

    // 获取块图像
    public static Bitmap block(final Bitmap bitmap, final Rect rect) {
        return UtilsBitmap.block(bitmap, rect.left, rect.top, rect.width(), rect.height());
    }

    public static Bitmap block(final Bitmap bitmap, int left, int top, int width, int height) {
        Bitmap ok = bitmap;
        if (bitmap != null) {
            if (width <= 0) width = bitmap.getWidth();
            if (height <= 0) height = bitmap.getHeight();
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            if (src.intersect(left, top, width, height)) {
                ok = Bitmap.createBitmap(bitmap, src.left, src.top, src.width(), src.height()).copy(Bitmap.Config.ARGB_8888, true);
            }
        }
        return ok;
    }

    // 保存到文件
    public static boolean save(final Bitmap bitmap, final String file, int quality) {
        boolean ok = false;
        if ((bitmap != null) && !file.isEmpty()) {
            File hFile = new File(file);
            if (!new File(hFile.getParent()).exists()) {
                new File(hFile.getParent()).mkdirs();
            }
            try {
                FileOutputStream os = new FileOutputStream(hFile);
                if (os != null) {
                    if (UtilsFile.isJpgFile(file)) {
                        ok = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
                    } else if (UtilsFile.isPngFile(file)) {
                        ok = bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    } else if (UtilsFile.isBmpFile(file)) {
                        ok = UtilsBitmap.saveBmp(bitmap, os);
                    }
                    os.flush();
                    os.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return ok;
    }

    // 缩放图像
    public static Bitmap scale(final String bmpFile, int width, int height, boolean ratio) {
        return UtilsBitmap.scale(BitmapFactory.decodeFile(bmpFile), width, height, ratio);
    }

    // 缩放图像
    public static Bitmap scale(final String bmpFile, float scale) {
        return UtilsBitmap.scale(BitmapFactory.decodeFile(bmpFile), scale);
    }

    // 缩放图像
    public static Bitmap scale(final Bitmap src, int width, int height, boolean ratio) {
        Bitmap ok = src;
        if ((src != null) && (src.getWidth() > 0) && (src.getHeight() > 0) && (width > 0) && (height > 0)) {
            if (ratio) {
                float scale = min(width / (float) src.getWidth(), height / (float) src.getHeight());
                ok = UtilsBitmap.scale(src, scale);
            } else {
                Matrix matrix = new Matrix();
                matrix.postScale((float) width / src.getWidth(), (float) height / src.getHeight());
                ok = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            }
        }
        return ok;
    }

    // 缩放图像
    public static Bitmap scale(final Bitmap src, float scale) {
        Bitmap ok = null;
        if ((src.getWidth() > 0) && (src.getHeight() > 0)) {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            ok = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }
        return ok;
    }

    private static boolean saveBmp(final Bitmap bitmap, FileOutputStream os) {
        boolean ok = false;
        if ((bitmap != null) && (os != null)) {
            Bitmap bmp32 = Bitmap.createBitmap(bitmap);
            if (bmp32 != null) {
                try {
                    //bmp32.setConfig(Bitmap.Config.ARGB_8888);
                    os.write(UtilsBitmap.getFileHeader(bmp32));
                    os.write(UtilsBitmap.getInfoHeader(bmp32));
                    os.write(UtilsBitmap.getBitmapBits(bmp32));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ok;
    }

    private static byte[] getFileHeader(final Bitmap bitmap) {
        byte[] ok = null;
        if (bitmap != null) {
            ok = new byte[14];
            if (ok != null) {
                int size = bitmap.getWidth() * bitmap.getHeight() * 4;
                ok[0] = 0x42;
                ok[1] = 0x4D;
                ok[2] = (byte) (size >> 0);
                ok[3] = (byte) (size >> 8);
                ok[4] = (byte) (size >> 16);
                ok[5] = (byte) (size >> 24);
                ok[6] = 0x00;
                ok[7] = 0x00;
                ok[8] = 0x00;
                ok[9] = 0x00;
                ok[10] = 0x36;
                ok[11] = 0x00;
                ok[12] = 0x00;
                ok[13] = 0x00;
            }
        }
        return ok;
    }

    private static byte[] getInfoHeader(final Bitmap bitmap) {
        byte[] ok = null;
        if (bitmap != null) {
            ok = new byte[40];
            if (ok != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                ok[0] = 0x28;
                ok[1] = 0x00;
                ok[2] = 0x00;
                ok[3] = 0x00;
                ok[4] = (byte) (width >> 0);
                ok[5] = (byte) (width >> 8);
                ok[6] = (byte) (width >> 16);
                ok[7] = (byte) (width >> 24);
                ok[8] = (byte) (height >> 0);
                ok[9] = (byte) (height >> 8);
                ok[10] = (byte) (height >> 16);
                ok[11] = (byte) (height >> 24);
                ok[12] = 0x01;
                ok[13] = 0x00;
                ok[14] = 0x20;
                ok[15] = 0x00;
                ok[16] = 0x00;
                ok[17] = 0x00;
                ok[18] = 0x00;
                ok[19] = 0x00;
                ok[20] = (byte) (width * height >> 0);
                ok[21] = (byte) (width * height >> 8);
                ok[22] = (byte) (width * height >> 16);
                ok[23] = (byte) (width * height >> 24);
                ok[24] = 0x00;
                ok[25] = 0x00;
                ok[26] = 0x00;
                ok[27] = 0x00;
                ok[28] = 0x00;
                ok[29] = 0x00;
                ok[30] = 0x00;
                ok[31] = 0x00;
                ok[32] = 0x00;
                ok[33] = 0x00;
                ok[34] = 0x00;
                ok[35] = 0x00;
                ok[36] = 0x00;
                ok[37] = 0x00;
                ok[38] = 0x00;
                ok[39] = 0x00;
            }
        }
        return ok;
    }

    private static byte[] getBitmapBits(final Bitmap bitmap) {
        byte[] ok = null;
        if (bitmap != null) {
            int[] rgba = new int[bitmap.getWidth() * bitmap.getHeight()];
            if (rgba != null) {
                bitmap.getPixels(rgba, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                ok = new byte[rgba.length * 4];
                if (ok != null) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int pos = 0;
                    for (int y = 0; y < height; y++) {
                        int offset = width * (height - 1 - y);
                        for (int x = 0; x < width; x++) {
                            byte[] temp = UtilsBitmap.intToBytes(rgba[offset++]);
                            for (int n = 0; n < 4; n++) {
                                ok[pos++] = temp[n];
                            }
                        }
                    }
                }
            }
        }
        return ok;
    }

    private static byte[] intToBytes(int value) {
        byte[] buf = new byte[4];
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (value & 0x000000ff);
                value >>= 8;
            }
        } else {
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (value & 0x000000ff);
                value >>= 8;
            }
        }
        return buf;
    }

    public static List<Bitmap> getBlock(final String file, int columns, int rows, int row_pos, int size_w, int size_h) {
        return getBlock(BitmapFactory.decodeFile(file), columns, rows, row_pos, size_w, size_h);
    }

    public static List<Bitmap> getBlock(Resources resource, int id, int columns, int rows, int row_pos, int size_w, int size_h) {
        return getBlock(BitmapFactory.decodeResource(resource, id), columns, rows, row_pos, size_w, size_h);
    }

    public static List<Bitmap> getBlock(final Bitmap bitmap, int columns, int rows, int row_pos, int size_w, int size_h) {
        List<Bitmap> ok = new ArrayList<>();
        if (bitmap != null) {
            if (((bitmap.getWidth() % columns) == 0) && ((bitmap.getHeight() % rows) == 0)) {
                int dw = bitmap.getWidth() / columns;
                int dh = bitmap.getHeight() / rows;
                if ((size_w <= dw) && (size_h <= dh)) {
                    for (int n = 0; n < columns; n++) {
                        Bitmap bmp = Bitmap.createBitmap(bitmap, dw * n, dh * (row_pos - 1), size_w, size_h, null, false);
                        ok.add(bmp);
                    }
                }
            }
        }
        return ok;
    }

}
