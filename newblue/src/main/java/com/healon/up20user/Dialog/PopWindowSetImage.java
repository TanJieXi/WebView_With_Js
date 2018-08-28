package com.healon.up20user.Dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.newblue.R;
import com.healon.up20user.Adapter.PopSetImageAdapter;
import com.healon.up20user.Utils.UtilsDisplay;
import com.healon.up20user.View.ViewSample;

import java.util.ArrayList;
import java.util.List;

import api.os.HS_Mode;
import api_extend.image.HS_DenoiseEx;
import api_extend.image.HS_GainEx;
import api_extend.image.HS_ImageHeight;
import api_extend.image.HS_ImageWidth;
import api_extend.image.HS_PseColorEx;
import api_extend.image.HS_ZoomEx;


/**
 * Created by Pretend on 2017/10/10 0010.
 */

public class PopWindowSetImage extends PopupWindow {


    private Activity m_context;
    private ListView m_lvContent;
    private List<DataBean> list;
    private PopSetImageAdapter adapter;
    private View m_viewGray;
    private ViewSample m_viewSurface;
    // private ViewText m_viewSurface;
    private LinearLayout m_mainContent;
    public static final int TYPE_SEEKBAR = 1;
    public static final int TYPE_BUTTON = 2;
    public  ZoomCollback zoomCollback;

    public  PopWindowSetImage(Activity context, View view, LinearLayout mainContent, ViewSample viewSurface) {
        this.m_context = context;
        this.m_viewGray = view;
        this.m_mainContent = mainContent;
        this.m_viewSurface = viewSurface;
        this.initView();
    }
    public PopWindowSetImage(Activity context, View view, LinearLayout mainContent, ViewSample viewSurface, ZoomCollback zoomCollback) {
        this.m_context = context;
        this.m_viewGray = view;
        this.m_mainContent = mainContent;
        this.m_viewSurface = viewSurface;
        this.zoomCollback=zoomCollback;
        this.initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(m_context).inflate(R.layout.follow_dialog_set_image_pop, null);
        m_lvContent = (ListView) rootView.findViewById(R.id.lv_content);
        this.setContentView(rootView);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setWidth(UtilsDisplay.dip2px(m_context, 250));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        list = new ArrayList<>();
        list.add(new DataBean("伪彩色", new HS_PseColorEx(m_viewGray), TYPE_BUTTON));
        if(!new HS_Mode().isModeBM()) {
            list.add(new DataBean("缩放", new HS_ZoomEx(), TYPE_BUTTON));
        }
        list.add(new DataBean("降噪", new HS_DenoiseEx(), TYPE_BUTTON));
        list.add(new DataBean("增益", new HS_GainEx(), TYPE_SEEKBAR));
        list.add(new DataBean("宽度", new HS_ImageWidth(m_mainContent,m_context), TYPE_SEEKBAR));
        list.add(new DataBean("高度", new HS_ImageHeight(m_mainContent,m_context), TYPE_SEEKBAR));
        adapter = new PopSetImageAdapter(list,new PopSetImageAdapter.CallBack() {
            @Override
            public void callBack() {
                adapter.notifyDataSetChanged();
            }
        });
        m_lvContent.setAdapter(adapter);
    }


    public void showPoP(View parent, boolean isTab) {
        int height = this.m_viewSurface.getHeight();
        int width = this.m_viewSurface.getWidth();
        int m_nXOff;
       // int habbit = HS_Setting.getValue(Constants.SETTINGS_TABLE, m_context, HS_SettingKey.KEY_HABBIT, new HSVariant(HSEnum.Habbit.Left)).toInt();
       // if (isTab && (habbit == HSEnum.Habbit.Left)) {
            m_nXOff = width / 2 - UtilsDisplay.dip2px(m_context, 250) / 2 + UtilsDisplay.dip2px(m_context, 127);
//        }else {
//            m_nXOff = width / 2 - UtilsDisplay.dip2px(m_context, 250) / 2;
//        }
        int m_nYOff = height / 2 - (list.size() * UtilsDisplay.dip2px(m_context, 48)) / 2;
        this.showAsDropDown(parent, m_nXOff, m_nYOff);
    }

    public void setZoomCollback(ZoomCollback zoomCollback) {
        this.zoomCollback = zoomCollback;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(zoomCollback !=null){
            zoomCollback.zoomcollback();
        }
        m_context = null;
        m_lvContent = null;
        list.clear();
        adapter = null;
    }

    public class DataBean {
        public String name;
        public Object obj;
        public int type;

        public DataBean(String name, Object obj, int type) {
            this.name = name;
            this.obj = obj;
            this.type = type;
        }
    }

    public interface ZoomCollback{
        void zoomcollback();
    }
}
