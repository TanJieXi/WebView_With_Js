package com.healon.up20user.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 操作日期和时间
 * Created by Administrator on 2017/5/20.
 */

public class UtilsDateTime {

    public static String getYear() {
        return new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
    }

    public static String getDateForID() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
    }

    public static String getFileName() {
        return new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date(System.currentTimeMillis()));
    }

    public static String getFileName(final String ext) {
        String ok = new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date(System.currentTimeMillis()));
        ok += ext;
        return ok;
    }
}
