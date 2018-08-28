package com.healon.up20user.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.SeekBar;

import com.example.newblue.R;
import com.healon.up20user.Constants;
import com.healon.up20user.Utils.UtilsResolution;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import api.hard.HS_Freeze;
import api.hard.HS_Freq;
import api.hard.HS_MI;
import api.hard.HS_Probe;
import api.image.HS_Fps;
import api.image.HS_Gain;
import api.image.HS_Part;
import api.image.HS_Zoom;
import api.measure.HS_MeasureQuery;
import api.measure.math.HS_MathNormal;
import api.os.HS_Cine;
import api.os.HS_Connect;
import api.os.HS_Image;
import api.os.HS_MouseAction;
import api.type.HS_PointF;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class ViewSample extends SurfaceView implements Callback {

    private Context m_context;
    private SurfaceHolder m_holder;
    private HS_PointF m_click;
    private CallbackProgress m_callbackProgress = null;
    private SeekBar m_seekBar = null;
    //SDK相关类
    private HS_Fps m_hsFPS;
    private HS_Probe m_hsProbe;
    private HS_Freq m_hsFreq;
    private HS_Gain m_hsGain;
    private HS_Image m_hsImage;
    private HS_Cine m_hsCine;
    private HS_Freeze m_hsFreeze;
    private ShowThread m_thread;
    private Bitmap m_bitmap = null;
    private TextPaint m_paintText = null;
    private String m_part = "";
    private String m_info = "";
    private int m_infoTimes = 0;
    private int m_infoLength = 0;
    private boolean m_saving = false;
    private Bitmap m_bmpSave = null;

    public ViewSample(Context context) {
        super(context);
        init(context);
    }

    public ViewSample(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewSample(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化相关
    private void init(Context context) {
        m_click = HS_PointF.New();
        m_context = context;
        m_holder = getHolder();
        m_holder.addCallback(this);
        m_holder.setFormat(PixelFormat.RGBA_8888);
        m_hsImage = new HS_Image();
        m_hsProbe = new HS_Probe();
        m_hsFreq = new HS_Freq();
        m_hsFPS = new HS_Fps();
        m_hsGain = new HS_Gain();
        m_hsCine = new HS_Cine();
        m_hsFreeze = new HS_Freeze();
        m_paintText = new TextPaint();
        m_paintText.setColor(Color.WHITE);
        m_paintText.setTextSize(UtilsResolution.dip2px(m_context, 12));
    }

    //获取宽高
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_thread = new ShowThread();
        m_thread.start();
        m_thread.setRun(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        m_thread.setRun(false);
        m_thread.interrupt();
        m_thread = null;
        m_callbackProgress = null;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (new HS_Connect().isConnected()) {
            m_hsImage.setSize(getWidth(), getHeight());
        }
    }

    //回调刷新
    public void onCallbackRefresh() {
        if ((getWidth() != 0) && (getHeight() != 0)) {
            if ((m_bitmap == null) || m_bitmap.getWidth() != getWidth() || m_bitmap.getHeight() != getHeight()) {
                m_bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            }
            synchronized (this) {
                m_hsImage.getImage(m_bitmap);
            }
        }
    }

    //显示线程
    private class ShowThread extends Thread {
        boolean m_run = false;

        @Override
        public void run() {
            while (m_thread != null && !m_thread.isInterrupted()) {
                if (m_run) {
                    if (m_bitmap != null) {
                        Bitmap bitmap = null;
                        synchronized (this) {
                            bitmap = m_bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        }
                        if (m_saving) {
                            m_bmpSave = Bitmap.createBitmap(m_bitmap.getWidth(), m_bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(m_bmpSave);
                            drawBitmap(canvas, bitmap);
                            m_saving = false;
                        }
                        Canvas canvas = m_holder.lockCanvas();
                        if (canvas != null) {
                            drawBitmap(canvas, bitmap);
                            m_holder.unlockCanvasAndPost(canvas);
                        }
                        if (m_hsCine.isPlay()) {
                            m_seekBar.setProgress(m_hsCine.getIndex());
                        }
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (m_bitmap != null) {
                        m_bitmap.recycle();
                        m_bitmap = null;
                    }
                    if (m_bmpSave != null) {
                        m_bmpSave.recycle();
                        m_bmpSave = null;
                    }
                }
            }
        }

        public void setRun(boolean run) {
            m_run = run;
        }
    }

    //进度
    public interface CallbackProgress {
        void progresscollback();
    }

    //滑动屏幕监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ok = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                m_click = HS_PointF.New(event.getX(), event.getY());
                new HS_MouseAction().setMousePress(true, m_click);
                ok = true;
                break;
            case MotionEvent.ACTION_MOVE:
                new HS_MouseAction().setMouseMove(true, HS_PointF.New(event.getX(), event.getY()));
                ok = true;
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                float moveX = m_click.x() - upX;
                float moveY = m_click.y() - upY;
                if (Math.pow(moveX, 2) + Math.pow(moveY, 2) < 100) {
                    new HS_MouseAction().setMouseRelease(true, m_click);
                    if (!new HS_Zoom().isOpened()) {
                        if (m_callbackProgress != null) {
                            m_callbackProgress.progresscollback();
                            ok = true;
                        }
                    }
                } else {
                    new HS_MouseAction().setMouseRelease(true, HS_PointF.New(upX, upY));
                    ok = true;
                }
                break;
        }
        return ok;
    }

    //视频播放View
    public void setSeekBar(final SeekBar seekBar) {
        m_seekBar = seekBar;
    }

    //设置进度回调
    public void setCallbackProgress(CallbackProgress callbackProgress) {
        this.m_callbackProgress = callbackProgress;
    }

    //解析检查部位
    public List<String> getParts() {
        final List<String> ok = new ArrayList<>();
        final HS_Part hs_part = new HS_Part();
        String[] m_arrayPart = getResources().getStringArray(R.array.part);
        String[] m_listPart = hs_part.getNames();
        for (int i = 0; i < m_listPart.length; i++) {
            String value = m_listPart[i];
            for (int j = 0; j < m_arrayPart.length; j++) {
                if (m_arrayPart[j].contains(value)) {
                    ok.add(m_arrayPart[j]);
                    break;
                }
            }
        }
        return ok;
    }

    //刷新显示当前部位
    public void setPart() {
        //中英文切换
        //int language = HS_Setting.getValue(Constants.SETTINGS_TABLE, (Activity) m_context, HS_SettingKey.KEY_LANGUAGE, new HSVariant(0)).toInt();
        List<String> list = this.getParts();
        int index = new HS_Part().getIndex();
        if (index < list.size()) {
            m_part = list.get(index).split(";")[0];
        }
    }

    //保存图像
    public Bitmap getSaveBitmap() {
        if (m_bmpSave != null)
            m_bmpSave.recycle();
        if (!m_saving) m_saving = true;
        while (m_saving) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (m_saving) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        return m_bmpSave;
    }

    //画图
    private void drawBitmap(Canvas canvas, final Bitmap bitmap) {
        if (bitmap != null) {
            canvas.save();
            canvas.drawBitmap(bitmap, 0, 0, null);
            int dw = bitmap.getWidth();
            StaticLayout layout = new StaticLayout(getInfo(), m_paintText, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.25f, 0.0f, false);
            canvas.translate(dw - m_infoLength, UtilsResolution.dip2px(m_context, 10));
            layout.draw(canvas);
            canvas.restore();

            if (m_hsFreeze.isFreeze()) {
                String text = getMeasure();
                if (!text.isEmpty()) {
                    StaticLayout layout2 = new StaticLayout(getMeasure(), m_paintText, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.25f, 0.0f, false);
                    int count = layout2.getLineCount();
                    int top = layout2.getLineTop(0);
                    int bottom = layout2.getLineBottom(count - 1);
                    canvas.translate(50, this.getHeight() - (bottom - top) - UtilsResolution.dip2px(m_context, 35));
                    layout2.draw(canvas);
                }
            }
        }
    }

    //画显示信息
    private String getInfo() {
        if (m_infoTimes == 0) {
            String probe = m_hsProbe.getInfo().name;
            String freq = String.format("%.2fMHz", m_hsFreq.getValue());
            String part = m_part;
            String gain = "Gn " + (m_hsGain.getValue());
            String mi = "MI " + new DecimalFormat("0.0").format(new HS_MI().getMI());

            // 根据最长的字符串计算长度
            int length = 0;
            length = Math.max(length, this.getTextWidth(probe));
            length = Math.max(length, this.getTextWidth(freq));
            length = Math.max(length, this.getTextWidth(part));
            length = Math.max(length, this.getTextWidth(gain));
            length = Math.max(length, this.getTextWidth(mi));
            m_infoLength = (length + UtilsResolution.dip2px(m_context, 10));

            m_info = "";
            m_info += probe + "\n";
            m_info += freq + "\n";
            m_info += part + "\n";
            m_info += gain + "\n";
            if (Constants.IS_SHOW_FPS) {
                m_info += "FPS " + (int) (m_hsFPS.getFpsSoft() + 0.5) + "\n";
            }
            if (new HS_MI().getMI() != 0.0) {
                m_info += mi;
            }
        }
        m_infoTimes = (m_infoTimes + 1) % 10;

        return m_info;
    }

    //显示测量结果
    private String getMeasure() {
        String result = "";
        HS_MathNormal normal = new HS_MathNormal();
        HS_MeasureQuery query = new HS_MeasureQuery();
        int lineCount = 1;
        int count = query.getCount();
        for (int n = 0; n < count; n++) {
            String idCode = query.getIdCode(n);
            if (!idCode.equals("o_annotation") && !idCode.equals("o_arrows")) {
                if (idCode.equals("b_distance"))
                    result += lineCount + ": " + normal.getAutoDistanceString(n, 0, true);
                else if (idCode.equals("b_area_girth"))
                    result += lineCount + ": " + normal.getAutoAreaString(n, 0, true) + ",  " + normal.getAutoGirthString(n, 0, true);
                else if (idCode.equals("b_volume"))
                    result += lineCount + ": " + normal.getAutoAreaString(n, 0, true);
                else if (idCode.equals("b_angle"))
                    result += lineCount + ": " + normal.getAngleString(n, 0, true, 0.0f, 0.0f, true);
                else if (idCode.equals("b_left_coxae_angle"))
                    result += lineCount + ":α=" + normal.getAngleString(n, 0, true, 0.0f, 0.0f, true) + ", β=" + normal.getAngleString(n, 1, true, 0.0f, 0.0f, true);
                else if (idCode.equals("b_right_coxae_angle"))
                    result += lineCount + ": α=" + normal.getAngleString(n, 0, true, 0.0f, 0.0f, true) + ", β=" + normal.getAngleString(n, 1, true, 0.0f, 0.0f, true);
                else if (idCode.equals("b_line_ratio"))
                    result += lineCount + ": " + normal.getAutoDistanceString(n, 0, true) + ",  " + (lineCount + 1) + ": " + normal.getAutoDistanceString(n, 1, true) + ",  " + normal.getRatioDistanceString(n, true);
                else if (idCode.equals("b_area_ratio"))
                    result += lineCount + ": " + normal.getAutoAreaString(n, 0, true) + ",  " + (lineCount + 1) + ": " + normal.getAutoAreaString(n, 1, true) + ",  " + normal.getRatioAreaString(n, true);
                else if (idCode.equals("b_line_narrow"))
                    result += lineCount + ": " + normal.getAutoDistanceString(n, 0, true) + ",  " + (lineCount + 1) + ": " + normal.getAutoDistanceString(n, 1, true) + ",  " + normal.getNarrowDistanceString(n, true);
                else if (idCode.equals("b_area_narrow"))
                    result += lineCount + ": " + normal.getAutoAreaString(n, 0, true) + ",  " + normal.getAutoAreaString(n, 1, true) + ",  " + normal.getNarrowAreaString(n, true);
                else if (idCode.equals("m_distance"))
                    result += lineCount + ": " + normal.getMDistanceString(n, 0, true);
                else if (idCode.equals("m_time"))
                    result += lineCount + ": " + normal.getMTimeString(n, 0, true);
                else if (idCode.equals("m_slope"))
                    result += lineCount + ": " + normal.getSlopeString(n, 0, true);
                else if (idCode.equals("m_heart_rate"))
                    result += lineCount + ": " + normal.getHeartRateString(n, 0, true);
                result += ";   ";
                lineCount += query.getLineCount(n);
            }
        }
        return result;
    }

    private int getTextWidth(final String text) {
        Rect rect = new Rect();
        m_paintText.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    public void stopThreed() {
        if (m_thread != null)
            m_thread.setRun(false);
        //m_thread = null;
    }
}
