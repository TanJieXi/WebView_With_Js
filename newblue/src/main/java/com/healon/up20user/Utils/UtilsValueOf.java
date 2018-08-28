package com.healon.up20user.Utils;


import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class UtilsValueOf {

    public static boolean stringToBoolean(final String str) {
        boolean ok = false;
        try {
            ok = Boolean.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static byte stringToByte(final String str) {
        byte ok = 0;
        try {
            ok = Byte.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static int stringToInt(final String str) {
        int ok = 0;
        try {
            ok = Integer.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static long stringToLong(final String str) {
        long ok = 0;
        try {
            ok = Long.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static float stringToFloat(final String str) {
        float ok = 0.0f;
        try {
            ok = Float.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static double stringToDouble(final String str) {
        double ok = 0.0;
        try {
            ok = Double.valueOf(str);
        } catch (Exception e) {
        }
        return ok;
    }

    public static String booleanToString(final boolean v) {
        String ok = null;
        try {
            ok = String.valueOf(v);
        } catch (Exception e) {
        }
        return ok;
    }

    public static String byteToString(final byte v) {
        String ok = null;
        try {
            ok = String.valueOf(v);
        } catch (Exception e) {
        }
        return ok;
    }

    public static String intToString(final int v) {
        String ok = null;
        try {
            ok = String.valueOf(v);
        } catch (Exception e) {
        }
        return ok;
    }

    public static String longToString(final long v) {
        String ok = null;
        try {
            ok = String.valueOf(v);
        } catch (Exception e) {
        }
        return ok;
    }

    public static String floatToString(final float v, int dec) {
        String ok = null;
        try {
            ok = String.valueOf(new DecimalFormat("###.##").format(v));
        } catch (Exception e) {
        }
        return ok;
    }

    public static String doubleToString(final double v, int dec) {
        String ok = null;
        try {
            ok = String.valueOf(new DecimalFormat("###.##").format(v));
        } catch (Exception e) {
        }
        return ok;
    }
}
