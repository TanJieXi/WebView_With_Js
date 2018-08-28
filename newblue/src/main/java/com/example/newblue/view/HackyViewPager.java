package com.example.newblue.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ================================================
 * 作者: leilei
 * 版本: 1.0
 * 创建日期: 2017/12/15 16:11
 * 描述:
 * 修订历史:
 * ================================================
*/
public class HackyViewPager extends ViewPager {
	
    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			return false;
		}
    }
}
