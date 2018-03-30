package com.example.bluetoothdemo.anothers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Conver {
    public Context context;
    private static SQLiteDatabase db;
    public static String SBH = "";
    public static String STIME = "";
    public static String ETIME = "";
    public static String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static String EXTRAS_DEVICE_INDEX = "DEVICE_INDEX";
    public static int IsAutoCheck = 5000;
    // 扫描到的LE设备
    public static ArrayList<deviceBox> LeDevices;

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber1(String str) {// 判断整型
        return str.matches("^\\d+$$");
    }

    public static boolean isNumber2(String str) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
        return str.matches("\\d+\\.\\d+$");
    }

    public static boolean checkDate(String date) {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (Exception e) {
            // 如果不能转换,肯定是错误格式
            return false;
        }
        String s1 = df.format(d);
        return date.equals(s1);
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static String LongToDate(Long millSec) {

        return LongToDate(millSec, "yyyy-MM-dd");
    }

    public static String LongToDate(Long millSec, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    // 把日期转为字符串
    public static String ConverToString(Date date, String type) {
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date);
    }

    // 把日期转为字符串
    public static String ConverToString(Date date) {
        return ConverToString(date, "yyyy-MM-dd");
    }

    // 把字符串转为日期
    public static Date ConverToDate(String strDate, String type) {


        try {
            DateFormat df = new SimpleDateFormat(type);
            return df.parse(strDate);
        } catch (ParseException e) {
            Log.e("Conver", "ConverToDate出错：strDate=" + strDate + ";type=" + type);
            return null;
        }

    }

    // 把字符串转为日期
    public static Date ConverToDate(String strDate) {
        return ConverToDate(strDate, "yyyy-MM-dd");
    }

    /**
     * 获得指定日期的前后天数
     *
     * @return
     */
    public static Date getDayadd(Date date, int t) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + t);
        return c.getTime();
    }

    /**
     * 加载本地图片 http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从服务器取图片 http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getImageThumbnail(String uri, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri, options);
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth / width;
        int beHeight = options.outHeight / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        try {
            bitmap = BitmapFactory.decodeFile(uri, options);
        } catch (Exception e) {
            // TODO: handle exception
        }

        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    //存储进SD卡
    public static void saveFile(Bitmap bm, String fileName) throws Exception {
        File dirFile = new File(fileName);
        //检测图片是否存在
        if (dirFile.exists()) {
            dirFile.delete();  //删除原图片
        }
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    public static void convertWeekByDate(Date time) {

        Calendar cal = Calendar.getInstance();

        STIME = LongToDate(time.getTime() - (long) (7 * 24 * 60 * 60)
                * (long) 1000);
        ETIME = ConverToString(time, "yyyy-MM-dd");
    }

    public static void convertMonthByDate(Date time) {
        STIME = LongToDate(time.getTime()
                - (long) (Conver.getMonthLastDay(0) * 24 * 60 * 60)
                * (long) 1000);
        ETIME = ConverToString(time, "yyyy-MM-dd");
    }

    /**
     * 获取某年第一天日期
     *
     * @return Date
     */
    public static void getYearFirst(Date time) {

        STIME = LongToDate(time.getTime() - (long) (366 * 24 * 60 * 60)
                * (long) 1000);
        ETIME = ConverToString(time, "yyyy-MM-dd");
    }


    public static int twototen(String str) {
        return Integer.valueOf(str, 2);
    }

    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    public static byte[] reverseBytes(byte[] a) {
        int len = a.length;
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = a[a.length - 1 - k];
        }
        return b;
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {

            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {

        }

        return "";
    }

    public static int getCurrentMonthLastDay() {
        return getMonthLastDay(0);
    }

    public static int getMonthLastDay(int monthplus) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.MONTH, monthplus);// 日期回滚一天，也就是最后一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 16进制转字符串
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        return sb.toString();
    }

    /**
     * 累加和校验码
     */
    public static String Checksum(String hex) {
        int chekint = 0;
        String output = "";
        for (int i = 0; i < hex.length() - 1; i = i + 2) {
            output = hex.substring(i, i + 2);
            if (output.length() > 1) {
                int decimal = Integer.parseInt(output, 16);
                chekint = chekint + decimal;
            }
        }
        chekint = chekint % 256;
        return intToHex(chekint);
    }

    /**
     * 256以下10进制数字转16进制2位字符
     */
    public static String intToHex(int intNum) {
        String output = "";
        output = Integer.toHexString(intNum);
        if (output.length() == 1) {
            output = "0" + output;
        }
        return output;
    }
//		// byte转十六进制字符串
//		public static String bytes2HexString(byte[] bytes) {
//			String ret = "";
//			for (int i = 0; i < bytes.length; i++) {
//				String hex = Integer.toHexString(bytes[i] & 0xFF);
//				if (hex.length() == 1) {
//					hex = '0' + hex;
//				}
//				ret += hex.toUpperCase();
//			}
//			return ret;
//		}

    /**
     * byte[]转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytes2HexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将byte[2]转换成short
     *
     * @param b
     * @return
     */
    public static short byte2Short(byte[] b) {
        return (short) (((b[0] & 0xff) << 8) | (b[1] & 0xff));
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
            /*
		    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		    联通：130、131、132、152、155、156、185、186 
		    电信：133、153、180、189、（1349卫通） 
		    总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9 
		    */
//		    String telRegex = "((0\\d{2,3}-\\d{7,8})|())";
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return true;
        else return mobiles.matches(telRegex);
    }

    /**
     * 计算年龄
     *
     * @param birth yyyy-MM-dd
     * @return
     */
    public static String calcAge(String birth) {
        String str = "";


        Date birthDate = null;

        try {
            birthDate = Conver.ConverToDate(birth);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Calendar flightCal = Calendar.getInstance();
        flightCal.setTime(new Date());
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);

        int y = flightCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        int m = flightCal.get(Calendar.MONTH)
                - birthCal.get(Calendar.MONTH);
        int d = flightCal.get(Calendar.DATE) - birthCal.get(Calendar.DATE);
        if (y < 0) {

        }

        if (m < 0) {
            y--;
        }
        if (m >= 0 && d < 0) {
            y--;
        }
        str = String.valueOf(y);

        return str;
    }

    /**
     * 获取加密后的字符串
     *
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
