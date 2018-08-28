package com.healon.up20user.Dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.newblue.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class PopWindowSetting extends PopupWindow implements View.OnClickListener {
    private final int MAXCOUNT = 5;
    private View m_view;
    private SetTextCollBcak m_callbackSetText;
    private TextView[] m_tvSet = new TextView[MAXCOUNT];
    private Activity m_activity;
    private int m_arrayID;
    private TextView m_bindView;
    private String m_bindKey;
    private int m_itemID;

    public void setTextCollBcak(SetTextCollBcak textCollBcak) {
        this.m_callbackSetText = textCollBcak;
    }

    public PopWindowSetting(final Activity activity, TextView bindView, String bindKey, int arrayID, int itemID) {
        this.m_activity = activity;
        this.m_arrayID = arrayID;
        this.m_bindView = bindView;
        this.m_bindKey = bindKey;
        this.m_itemID = itemID;

        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_view = inflater.inflate(R.layout.follow_view_set_pop, null);

        {// 需根据实际使用情况修改的
            m_tvSet[0] = (TextView) m_view.findViewById(R.id.tv_set01);
            m_tvSet[1] = (TextView) m_view.findViewById(R.id.tv_set02);
            m_tvSet[2] = (TextView) m_view.findViewById(R.id.tv_set03);
            m_tvSet[3] = (TextView) m_view.findViewById(R.id.tv_set04);
            m_tvSet[4] = (TextView) m_view.findViewById(R.id.tv_set05);
        }
        String[] values = activity.getResources().getStringArray(m_arrayID);
        for (int n = 0; n < MAXCOUNT; n++) {
            m_tvSet[n].setOnClickListener(this);
            m_tvSet[n].setTag(n);
            if (m_itemID == n) {
                m_tvSet[n].setTextColor(m_activity.getResources().getColor(R.color.SpinnerTextColor));
            }
            if (n < values.length) {
                m_tvSet[n].setText(values[n]);
            } else {
                m_tvSet[n].setVisibility(View.GONE);
            }
        }

        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(m_view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.follow_popset);
    }

    public PopWindowSetting(@NonNull final Activity activity, @NonNull List<String> list, @NonNull int itemID) {
        this.m_activity = activity;
        this.m_itemID = itemID;

        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_view = inflater.inflate(R.layout.follow_view_set_pop, null);

        {// 需根据实际使用情况修改的
            m_tvSet[0] = (TextView) m_view.findViewById(R.id.tv_set01);
            m_tvSet[1] = (TextView) m_view.findViewById(R.id.tv_set02);
            m_tvSet[2] = (TextView) m_view.findViewById(R.id.tv_set03);
            m_tvSet[3] = (TextView) m_view.findViewById(R.id.tv_set04);
            m_tvSet[4] = (TextView) m_view.findViewById(R.id.tv_set05);
        }
        for (int n = 0; n < MAXCOUNT; n++) {
            m_tvSet[n].setOnClickListener(this);
            m_tvSet[n].setTag(n);
            if (m_itemID == n) {
                m_tvSet[n].setTextColor(m_activity.getResources().getColor(R.color.SpinnerTextColor));
            }
            if (n < list.size()) {
                m_tvSet[n].setText(list.get(n).split(";")[1]);
            } else {
                m_tvSet[n].setVisibility(View.GONE);
            }
        }

        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(m_view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.follow_popset);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            WindowManager.LayoutParams lp = m_activity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            m_activity.getWindow().setAttributes(lp);

            // 以下拉方式显示popupwindow
            //   this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        if (m_callbackSetText != null) {
            m_callbackSetText.callbackSetText(tag, m_tvSet[tag].getText().toString(), this.m_bindView, this.m_bindKey);
            dismiss();
        }
    }

    public interface SetTextCollBcak {
        void callbackSetText(int index, String text, TextView bindView, String bindKey);
    }
}
