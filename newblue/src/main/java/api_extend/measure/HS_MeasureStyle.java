package api_extend.measure;

import android.app.Activity;

import com.example.newblue.R;
import com.healon.up20user.Constants;
import com.healon.up20user.Utils.UtilsSDCard;

import api.measure.HS_MeasureAttrib;
import api.measure.HS_MeasureCallBack;
import api.type.HS_Font;
import api.type.HS_Pen;
import api.type.HS_Rgba;

/**
 * Created by Pretend on 2017/8/28 0028.
 */

public class HS_MeasureStyle {

    private Activity m_activity;
    //测量线帽参数
    private float m_fCapRadiusDraw = 15.0f;
    private float m_fCapRadiusModify = 17.0f;
    private float m_fLineWidthError = 17.0f;
    //手指触摸参数
    private float m_fFingerArrowWidth = 3.0f;
    private float m_fFingerArrowLen = 12.0f;
    private float m_fFingerCirleRadius = 5.0f;
    private float m_fFingerScale = 12.0f;
    //注释字体参数
    private int m_fCaretFontSize = 45;
    //测量索引号字体参数
    private int m_fMeasureFontSize = 45;
    //测量画笔参数
    private double m_fMeasurePenWidth = 3.0;
    private double m_fMeasurePenDashLen = 16.0;
    private double m_fMeasurePenGapLen = 8.0;
    private double m_fMeasurePenDashStart = 0.0;
    private double m_fMeasurePenGapEnd = 0.0;
    private HS_MeasureCallBack m_callBack;

    public HS_MeasureStyle(Activity activity) {
        m_activity = activity;
    }

    public HS_MeasureCallBack getCallBack() {
        return m_callBack != null ? m_callBack : null;
    }


    public void setMeasureStyle() {
        m_callBack = new HS_MeasureCallBack();
        m_callBack.setAttachCallbackCaret(m_activity, "onCallBackCaret");
        HS_MeasureAttrib attrib = new HS_MeasureAttrib();
        attrib.setFingerSupport(true);
        attrib.setFinger(m_fFingerArrowWidth, m_fFingerArrowLen,
                m_fFingerCirleRadius, m_fFingerScale,
                HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorFinger)));
        attrib.setDefaultCap(m_fCapRadiusDraw, m_fCapRadiusModify, m_fLineWidthError);
        attrib.setDefaultFontOrder(HS_Font.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorCaret)),
                UtilsSDCard.root() + Constants.PATH_SYSFILE + "/fonts/DroidSansFallback.ttf", m_fCaretFontSize, false, 72));
        attrib.setDefaultFontCaret(HS_Font.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorCaret)),
                UtilsSDCard.root() + Constants.PATH_SYSFILE + "/fonts/DroidSansFallback.ttf", m_fCaretFontSize, false, 72));
        attrib.setDefaultPen
                (
                        HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorMeasuring)),
                                m_fMeasurePenWidth, m_fMeasurePenDashLen, m_fMeasurePenGapLen,
                                m_fMeasurePenDashStart, m_fMeasurePenGapEnd),
                        HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorFinish)),
                                m_fMeasurePenWidth, m_fMeasurePenDashLen, m_fMeasurePenGapLen,
                                m_fMeasurePenDashStart, m_fMeasurePenGapEnd),
                        HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorModify)),
                                m_fMeasurePenWidth, m_fMeasurePenDashLen, m_fMeasurePenGapLen,
                                m_fMeasurePenDashStart, m_fMeasurePenGapEnd),
                        HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorSelected)),
                                m_fMeasurePenWidth, m_fMeasurePenDashLen, m_fMeasurePenGapLen,
                                m_fMeasurePenDashStart, m_fMeasurePenGapEnd)
                );
        attrib.setDefaultArrowScale(3.0f);
        attrib.setDefaultAngleScale(3.0f);
    }
}
