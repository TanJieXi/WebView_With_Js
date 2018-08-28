package com.healon.up20user.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.healon.up20user.Utils.UtilsDisplay;

import api.measure.HS_MeasureRemove;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ViewSuspend extends AppCompatImageButton {
    private float m_fDownX, m_fDownY;//按下时记录的x，y坐标
    private GestureDetector detector;
    private float m_fLeft = 0, m_fRight = 0, m_fTop = 0, m_fBottom = 0;
    private int m_nLeft,m_nRight,m_nTop,m_nBottom;
    private Context context;

    public ViewSuspend(Context context) {
        super(context);
        init(context);
    }

    public ViewSuspend(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewSuspend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setView(ViewSample view){
        m_nLeft = view.getLeft()+ UtilsDisplay.dip2px(context,10);
        m_nRight = view.getRight() - UtilsDisplay.dip2px(context,10);
        m_nTop = view.getTop()+ UtilsDisplay.dip2px(context,10);
        m_nBottom = view.getBottom() - UtilsDisplay.dip2px(context,10);
    }

    private void init(Context context) {
        this.context = context;
        detector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        detector.onTouchEvent(e);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (m_fLeft != 0) {
            setLeft((int) m_fLeft);
            setRight((int) m_fRight);
            setTop((int) m_fTop);
            setBottom((int) m_fBottom);
        }
        super.onLayout(changed, left, top, right, bottom);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            new HS_MeasureRemove().removeLast();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            m_fDownX = e1.getX();
            m_fDownY = e1.getY();
            float m_fMoveX = e2.getX() - m_fDownX;
            float m_fMoveY = e2.getY() - m_fDownY;
            m_fTop = getTop() + m_fMoveY;
            m_fBottom = getBottom() + m_fMoveY;
            m_fLeft = getLeft() + m_fMoveX;
            m_fRight = getRight() + m_fMoveX;
            if (m_fLeft < m_nLeft) {
                m_fLeft = m_nLeft;
                m_fRight = m_nLeft+getWidth();
            }
            if (m_fRight > m_nRight) {
                m_fLeft = m_nRight - getWidth();
                m_fRight = m_nRight;
            }
            if (m_fTop < m_nTop) {
                m_fTop = m_nTop;
                m_fBottom = m_nTop+getHeight();
            }
            if (m_fBottom > m_nBottom) {
                m_fTop = m_nBottom - getHeight();
                m_fBottom = m_nBottom;
            }
            //重新布局view
            setFrame((int) m_fLeft, (int) m_fTop, (int) m_fRight, (int) m_fBottom);
            return true;
        }
    }

}
