package com.healon.up20user.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class UtilsResolution {

    private UtilsResolution() {

    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, double pxValue) {
        if (context != null) {
            final double scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5);
        }
        return 0;
    }

    /**
     * 将dip或dp值转换为px值
     */
    public static int dip2px(Context context, double dipValue) {
        if (context != null) {
            final double scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5);
        }
        return 0;
    }

    public static int getDPI(Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().densityDpi;
        }
        return 0;
    }

    public static int getDensity(Context context) {
        if (context != null) {
            return (int) context.getResources().getDisplayMetrics().density;
        }
        return 0;
    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(Context context, double pxValue) {
        if (context != null) {
            final double fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5);
        }
        return 0;
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, double spValue) {
        if (context != null) {
            final double fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5);
        }
        return 0;
    }

    public static int getScreenWidth(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;
        }
        return 0;
    }

    public static int getScreenHeight(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;
        }
        return 0;
    }

    public static double getXDPI(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.xdpi;
        }
        return 0;
    }

    public static double getYDPI(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.ydpi;
        }
        return 0;
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        if (context != null) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            int h_screen = dm.heightPixels;
            return new Point(w_screen, h_screen);
        }
        return null;
    }

    /**
     * 获取屏幕长宽比
     *
     * @param context
     * @return
     */
    public static double getScreenRate(Context context) {
        double ok = 0.0;
        if (context != null) {
            Point P = UtilsResolution.getScreenMetrics(context);
            double H = P.y;
            double W = P.x;
            ok = (H / W);
        }
        return ok;
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    private static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        if (activity != null) {
            Rect localRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
            statusHeight = localRect.top;
            if (0 == statusHeight) {
                Class<?> localClass;
                try {
                    localClass = Class.forName("com.android.internal.R$dimen");
                    Object localObject = localClass.newInstance();
                    int i5 = Integer.parseInt(localClass
                            .getField("status_bar_height").get(localObject)
                            .toString());
                    statusHeight = activity.getResources()
                            .getDimensionPixelSize(i5);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return statusHeight;
    }

    /**
     * 检测是否有虚拟按键
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        if (context != null) {
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
        }
        return hasNavigationBar;
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        if (context != null) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                vh = res.getDimensionPixelSize(resourceId);
            }
        }
        return vh;
    }

    /**
     * 根据构造函数获得当前手机的手机宽度
     */
    public static int getDensityWidth(Context context) {
        // 获取当前屏幕
        if (context != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dm = context.getApplicationContext().getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            return width;
        }
        return 0;
    }

    /**
     * 根据构造函数获得当前手机的手机高度
     */
    public static int getDensityHeight(Context context) {
        int height = 0;
        if (context != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dm = context.getApplicationContext().getResources().getDisplayMetrics();
            boolean hasNav = UtilsResolution.checkDeviceHasNavigationBar(context);
            if (hasNav) {
                height = dm.heightPixels - UtilsResolution.getVirtualBarHeigh(context);
            } else {
                height = dm.heightPixels;
            }
        }
        return height;
    }

    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean result = realSize.y != size.y;
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }
}
