package com.healon.up20user.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import api.image.HS_ColorMap;
import api.type.HS_Rgba;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ViewGrayBar extends View {
    private int m_width = 0;
    private int m_height = 0;
    private Paint m_paint;

    public ViewGrayBar(Context context) {
        super(context);
        m_paint = new Paint();
    }

    public ViewGrayBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paint = new Paint();
    }

    public ViewGrayBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m_paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        m_width = w - 10;
        m_height = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HS_ColorMap map = new HS_ColorMap();
        HS_Rgba[] rgbList = map.getValue();
        double step = 255.0 / (m_height - 1);
        for (int y = 0; y < m_height; y++) {
            HS_Rgba rgba = rgbList[255-(int) (y * step)];
            m_paint.setColor(Color.argb(255, rgba.r(), rgba.g(), rgba.b()));
            canvas.drawLine(10, y, 10 + m_width, y, m_paint);
        }
    }
}
