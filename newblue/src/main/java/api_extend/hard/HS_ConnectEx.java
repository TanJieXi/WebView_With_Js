package api_extend.hard;

import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.healon.up20user.Constants;
import com.healon.up20user.Share.SharePrefreceHelper;
import com.healon.up20user.Utils.UtilsFile;
import com.healon.up20user.Utils.UtilsProgress;
import com.healon.up20user.Utils.UtilsSDCard;
import com.healon.up20user.Utils.UtilsUsbAuthority;
import com.healon.up20user.View.ViewGrayBar;
import com.healon.up20user.View.ViewSample;

import api.hard.HS_Freeze;
import api.os.HS_Connect;
import api_extend.image.HS_OverlayDirectEx;
import api_extend.image.HS_RulerEx;
import api_extend.measure.HS_MeasureStyle;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class HS_ConnectEx {
    private ViewGrayBar m_viewGrayBar;
    private ViewSample m_viewSample;
    //private ViewText m_viewSample;
    private Activity m_activity;
    private Handler m_handler;
    private HS_Connect m_connect;
    private HS_MeasureStyle m_measureStyle;
    private int ok = 0;
    private int no = 0;
    private boolean m_isFinished = true;


    public HS_ConnectEx(@NonNull Activity activity, @NonNull Handler handler, @NonNull ViewGrayBar viewGrayBar,
                        @NonNull ViewSample viewSample) {
        this.m_activity = activity;
        m_measureStyle = new HS_MeasureStyle(m_activity);
        this.m_viewGrayBar = viewGrayBar;
        this.m_viewSample = viewSample;
        this.m_handler = handler;
        this.m_connect = new HS_Connect();
    }

    // 连接初始化
    public void connect() {
        beforeConnect();
        final UsbManager manager = UtilsUsbAuthority.getManager(m_activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                m_connect.attachUsbManager(manager);
                if(SharePrefreceHelper.getInstence(m_activity,Constants.MANUFACTURER_NAME).isFirst()){
                    UtilsFile.copyResource(m_activity, "sysFile", UtilsSDCard.root() + Constants.PATH_SYSFILE);
                }
                boolean connectfinish = m_connect.setConnect(
                        Constants.DEVICE_REAL, "HEALSON",
                        UtilsSDCard.root() + Constants.PATH_SYSFILE + "/config",
                        UtilsSDCard.root() + Constants.PATH_SYSFILE + "/obTable",
                        UtilsSDCard.root() + Constants.PATH_SYSFILE + "/palettes",
                        UtilsSDCard.root() + Constants.PATH_SYSFILE + "/templates",
                        "", "");
                if (connectfinish) {
                    setSDKSkin();
                    new HS_Freeze().setRun();
                }
                m_handler.sendEmptyMessageDelayed(Constants.CONNECT_FINISH, Constants.DEVICE_REAL ? 0 : 2000);
            }
        }).start();
    }

    private void beforeConnect() {
        m_isFinished = false;
        UtilsProgress.showWithStatus(m_activity, "正在加载中 ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!m_isFinished) {
                    m_handler.sendEmptyMessage(Constants.SHOW_HARD_CONFIG_PROGRESS);
                    SystemClock.sleep(10);
                }
            }
        }).start();
    }

    //是否连接成功
    public void onFinish() {
        UtilsProgress.dismiss();
        if (m_connect.isConnected()) {
            m_viewGrayBar.setVisibility(View.VISIBLE);
            m_viewSample.setVisibility(View.VISIBLE);
            m_viewSample.setPart();
            new HS_Freeze().setRun();
            m_handler.sendEmptyMessage(Constants.UPDATE_BUTTON_STATE);
        } else {
            m_handler.sendEmptyMessage(Constants.INIT_NO);
            Toast.makeText(m_activity, "连接失败", Toast.LENGTH_SHORT).show();
        }
        m_isFinished = true;
    }

    private void setSDKSkin() {
        //new HS_Switch().setDscOptimizeMode(HS_Switch.DscOptimizeMode.ModeMulThread, 8);
        new HS_OverlayDirectEx(m_activity).setOverlayDirect();
        new HS_RulerEx(m_activity).setRulerStyle();
        m_measureStyle.setMeasureStyle();
    }

    public HS_Connect getConnect() {
        return m_connect;
    }

    public void clean() {
        if (m_measureStyle.getCallBack() != null)
            m_measureStyle.getCallBack().setDetachCallbackCaret();
        m_handler = null;
    }

    public boolean isFinished() {
        return m_isFinished;
    }

}
