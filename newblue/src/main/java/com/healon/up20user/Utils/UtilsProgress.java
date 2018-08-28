package com.healon.up20user.Utils;

import android.content.Context;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bigkoo.svprogresshud.view.SVCircleProgressBar;

/**
 * Created by Administrator on 2017/8/31.
 */


public class UtilsProgress {
    private static SVProgressHUD m_progress = null;

    public static void showWithStatus(Context context, String string) {
        UtilsProgress.init(context);
        m_progress.showWithStatus(string);
    }

    public static void clean() {
        if (m_progress != null)
            m_progress = null;
    }

    public static SVProgressHUD getPanel() {
        return m_progress;
    }

    public static void showWithStatus(Context context, String string, SVProgressHUD.SVProgressHUDMaskType maskType) {
        UtilsProgress.init(context);
        m_progress.showWithStatus(string, maskType);
    }

    public static void showInfoWithStatus(Context context, String string) {
        UtilsProgress.init(context);
        m_progress.showInfoWithStatus(string);
    }

    public static void showInfoWithStatus(Context context, String string, SVProgressHUD.SVProgressHUDMaskType maskType) {
        UtilsProgress.init(context);
        m_progress.showInfoWithStatus(string, maskType);
    }

    public static void showSuccessWithStatus(Context context, String string) {
        UtilsProgress.init(context);
        m_progress.showSuccessWithStatus(string);
    }

    public static void showSuccessWithStatus(Context context, String string, SVProgressHUD.SVProgressHUDMaskType maskType) {
        UtilsProgress.init(context);
        m_progress.showSuccessWithStatus(string, maskType);
    }

    public static void showErrorWithStatus(Context context, String string) {
        UtilsProgress.init(context);
        m_progress.showErrorWithStatus(string);
    }

    public static void showErrorWithStatus(Context context, String string, SVProgressHUD.SVProgressHUDMaskType maskType) {
        UtilsProgress.init(context);
        m_progress.showErrorWithStatus(string, maskType);
    }

    public static void showWithProgress(Context context, String string, SVProgressHUD.SVProgressHUDMaskType maskType) {
        UtilsProgress.init(context);
        m_progress.showWithProgress(string, maskType);
    }

    public static SVCircleProgressBar getProgressBar() {
        return m_progress.getProgressBar();
    }

    public static void dismiss() {
        if (m_progress != null) {
            m_progress.dismiss();
        }
    }

    private static void init(Context context) {
        if (m_progress == null) {
            m_progress = new SVProgressHUD(context);
        }
    }

}