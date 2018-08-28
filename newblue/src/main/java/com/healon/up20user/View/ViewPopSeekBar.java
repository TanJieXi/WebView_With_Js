package com.healon.up20user.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.newblue.R;
import com.healon.up20user.Constants;

import api.hard.HS_Freeze;


/**
 * Created by Administrator on 2017/9/26.
 */

public class ViewPopSeekBar extends AppCompatSeekBar {
    //定义变量
    private PopupWindow pupWindow;
    private View mView;
    //用来表示该组件在整个屏幕内的绝对坐标，其中 mPosition[0] 代表X坐标,mPosition[1] 代表Y坐标。
    private int[] mPosition;
    //SeekBar上的Thumb的宽度，即那个托动的小黄点的宽度
    private final int mThumbWidth = 25;
    private TextView mTvProgress;

    //构造函数，初始化操作
    public ViewPopSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得父视图对象
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //获得插入父节点的view对象
        mView = layoutInflater.inflate(R.layout.follow_view_seekbar_pop, null);
        mTvProgress = (TextView) mView.findViewById(R.id.myPop);
        pupWindow = new PopupWindow(mView, mView.getWidth(), mView.getHeight(), true);
        mPosition = new int[2];
    }

    public void setSeekBarText(String str) {
        mTvProgress.setText(str);
    }

    //重写触发按下监听事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.getLocationOnScreen(mPosition);
                //控件显示位置
                if (new HS_Freeze().isFreeze() && Constants.INIT_SEEKBAR) {
                    pupWindow.showAsDropDown(this, (int) event.getX(), -300);
                }
                break;
            case MotionEvent.ACTION_UP:
                pupWindow.dismiss();
                break;
        }
        return super.onTouchEvent(event);
    }

    //获得控件的宽度
    private int getViewWidth(View v) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredWidth();
    }

    //获得控件的高度
    private int getViewHeight(View v) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredHeight();
    }

    //重写draw方法
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int thumb_x = 0;
        if (this.getMax() != 0) {
            thumb_x = this.getProgress() * (this.getWidth() - mThumbWidth) / this.getMax();
        }
        //表示popupwindow在进度条所在y坐标减去popupwindow的高度，再减去他们直接的距离，我设置为5个dip；
        int middle = mPosition[1] - getViewHeight(mView);
        super.onDraw(canvas);
        if (pupWindow != null) {
            try {
                this.getLocationOnScreen(mPosition);
                pupWindow.update(thumb_x + mPosition[0] - getViewWidth(mView) / 2 + mThumbWidth / 2,
                        middle, getViewWidth(mView), getViewHeight(mView));

            } catch (Exception e) {

            }
        }

    }
}
