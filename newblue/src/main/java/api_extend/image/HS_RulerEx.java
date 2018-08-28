package api_extend.image;

import android.app.Activity;

import com.example.newblue.R;
import com.healon.up20user.Constants;
import com.healon.up20user.Utils.UtilsResolution;
import com.healon.up20user.Utils.UtilsSDCard;

import api.hard.HS_Focus;
import api.os.overlay.HS_OverlayFocus;
import api.os.overlay.HS_OverlayRuler;
import api.os.overlay.HS_OverlayZoom;
import api.type.HS_Font;
import api.type.HS_Pen;
import api.type.HS_Rgba;


/**
 * Created by Pretend on 2017/8/28 0028.
 */

public class HS_RulerEx {
    private Activity m_activity;
    //标尺长短刻度
    private int m_nHorzLenLong = 7;
    private int m_nHorzLenShort = 4;
    private int m_nVertLenLong = 7;
    private int m_nVertLenShort = 4;
    //标尺画笔参数
    private double m_dRulerPenWidth = 1.0;
    //标尺字体参数
    private double m_dRulerFontSize = 12.0;

    private int m_nOverlayFocusSize = 6;

    private int m_nZoomLocalWidth = 192;

    public HS_RulerEx(Activity activity) {
        m_activity = activity;
    }

    public void setRulerStyle() {
        HS_OverlayRuler ruler = new HS_OverlayRuler();
        ruler.setHorzLength(UtilsResolution.dip2px(m_activity, m_nHorzLenLong),
                UtilsResolution.dip2px(m_activity, m_nHorzLenShort));
        ruler.setVertLength(UtilsResolution.dip2px(m_activity, m_nVertLenLong),
                UtilsResolution.dip2px(m_activity, m_nVertLenShort));
        ruler.setHorzPen(HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorHorz)),
                UtilsResolution.dip2px(m_activity, m_dRulerPenWidth), 0.0, 0.0, 0.0, 0.0));
        ruler.setVertPen(HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorVert)),
                UtilsResolution.dip2px(m_activity, m_dRulerPenWidth), 0.0, 0.0, 0.0, 0.0));
        ruler.setHorzFont(HS_Font.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorFontHorz)),
                UtilsSDCard.root() + Constants.PATH_SYSFILE + "/fonts/DroidSansFallback.ttf",
                UtilsResolution.dip2px(m_activity, m_dRulerFontSize), false, 72));
        ruler.setVertFont(HS_Font.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorFontVert)),
                UtilsSDCard.root() + Constants.PATH_SYSFILE + "/fonts/DroidSansFallback.ttf",
                UtilsResolution.dip2px(m_activity, m_dRulerFontSize), false, 72));
        new HS_OverlayFocus().setSize(UtilsResolution.dip2px(m_activity, m_nOverlayFocusSize));

        HS_OverlayZoom ozoom = new HS_OverlayZoom();
        ozoom.setPenOrg(HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorZoomOrg)),
                UtilsResolution.dip2px(m_activity, 1.0),
                UtilsResolution.dip2px(m_activity, 1.0),
                UtilsResolution.dip2px(m_activity, 2.0),
                0.0, 0.0));
        ozoom.setPenDst(HS_Pen.New(HS_Rgba.New(m_activity.getResources().getColor(R.color.ColorZoomDst)),
                UtilsResolution.dip2px(m_activity, 1.0),
                UtilsResolution.dip2px(m_activity, 1.0),
                UtilsResolution.dip2px(m_activity, 2.0),
                0.0, 0.0));
        ozoom.setWidthLocal(UtilsResolution.dip2px(m_activity, m_nZoomLocalWidth));

        new HS_Focus().setShow(false);
    }
}
