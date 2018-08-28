package com.healon.up20user.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newblue.R;
import com.healon.up20user.Adapter.DialogChildAdapter;
import com.healon.up20user.Adapter.DialogParentAdapter;
import com.healon.up20user.Utils.UtilsResolution;

import api.measure.HS_MeasureCreate;

/**
 * Created by Pretend on 2017/7/10 0010.
 */

public class DialogChooseItem extends Dialog {

    private Activity m_activity;
    private WindowManager.LayoutParams m_lp;
    private TextView m_tvCancel;
    private TextView m_tvConfirm;
    private TextView m_tvTitle;
    private ListView m_lvParent;
    private ListView m_lvChild;
    private DialogParentAdapter m_parentAdapter;
    private DialogChildAdapter m_childAdapter;

    public DialogChooseItem(Activity activity) {
        super(activity, R.style.follow_chooseDialogPromptDlg);
        this.initView(activity);
    }

    public DialogChooseItem(Activity activity, int themeResId) {
        super(activity, themeResId);
        this.initView(activity);
    }

    private void initView(Activity activity) {
        this.m_activity = activity;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.m_lp = getWindow().getAttributes();
        this.m_lp.alpha = 1.0f;
        this.m_lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(m_lp);
        setContent();
    }

    private void setContent() {
        View root = LayoutInflater.from(m_activity).inflate(R.layout.follow_view_choose_item_dlg,
                new LinearLayout(m_activity), false);
        m_tvCancel = (TextView) root.findViewById(R.id.tv_cancel);
        m_tvConfirm = (TextView) root.findViewById(R.id.tv_confirm);
        m_tvTitle = (TextView) root.findViewById(R.id.tv_title);
        m_lvParent = (ListView) root.findViewById(R.id.lv_parent);
        m_lvChild = (ListView) root.findViewById(R.id.lv_child);
        m_lvChild.setDivider(null);
        setContentView(root);
    }

    private void initData() {
        m_parentAdapter = new DialogParentAdapter(m_activity);
        m_childAdapter = new DialogChildAdapter(m_activity, m_parentAdapter.getParentArray());
        m_lvParent.setAdapter(m_parentAdapter);
        m_lvChild.setAdapter(m_childAdapter);
        this.updateHeight();
        m_lvParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_parentAdapter.setCurrentPosition(position);
                m_parentAdapter.notifyDataSetChanged();
                m_childAdapter.setChildData(m_parentAdapter.getParentArray());
                m_childAdapter.notifyDataSetChanged();
                updateHeight();
            }
        });

        m_lvChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateHeight();
                clickItem(position);
                dismiss();
            }
        });

    }

    public static void showDialog(Activity context, String title) {
        DialogChooseItem dialog = new DialogChooseItem(context);
        dialog.m_tvTitle.setText(title);
        dialog.initData();
        dialog.show();
    }

    private void updateHeight() {
        int m_nParentCount = m_lvParent.getAdapter().getCount();
        int m_nChildCount = m_lvChild.getAdapter().getCount();
        ViewGroup.LayoutParams m_lpParent = m_lvParent.getLayoutParams();
        ViewGroup.LayoutParams m_lpChild = m_lvChild.getLayoutParams();
        //手机的话  就自己去加
      //  if (HSEnum.Rotate.Landscape == HS_Setting.getValue(Constants.SETTINGS_TABLE, m_activity, HS_SettingKey.KEY_ROTATE, new HSVariant(HSEnum.Rotate.Portrait)).toInt()) {
            // 横屏
            m_lpParent.height = UtilsResolution.dip2px(m_activity,200);
            m_lpParent.width = UtilsResolution.dip2px(m_activity,200);
            m_lpChild.width = UtilsResolution.dip2px(m_activity,200);
            m_lpChild.height = UtilsResolution.dip2px(m_activity,200);
//        } else {
//            //竖屏
//            if (m_nParentCount >= m_nChildCount) {
//                m_lpParent.height = (m_nParentCount * Constants.g_resolution.dip2px(40.5));
//                m_lpChild.height = (m_nParentCount * Constants.g_resolution.dip2px(40.5));
//            } else {
//                m_lpParent.height = (m_nChildCount * Constants.g_resolution.dip2px(40));
//                m_lpChild.height = (m_nChildCount * Constants.g_resolution.dip2px(40));
//            }
//        }
        m_lvParent.setLayoutParams(m_lpParent);
        m_lvChild.setLayoutParams(m_lpChild);
    }

    private void clickItem(int position) {
        String idCode = m_childAdapter.getIdCode(position);
        String name = m_childAdapter.getClassName(position);
        new HS_MeasureCreate().createMeasure(name, idCode);
    }

}