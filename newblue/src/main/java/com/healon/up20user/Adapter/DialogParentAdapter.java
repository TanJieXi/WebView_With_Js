package com.healon.up20user.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newblue.R;

import java.util.ArrayList;
import java.util.List;

import api.os.HS_Mode;

/**
 * Created by Pretend on 2017/7/12 0012.
 */

public class DialogParentAdapter extends BaseAdapter {
    private List<String[]> m_listParent = new ArrayList<>();
    private static int m_nPosition_BMode = 0;
    private static int m_nPosition_BMMode = 0;
    private Context m_context;

    public DialogParentAdapter(Context context) {
        m_context = context;
        if (new HS_Mode().isModeB()) {
            m_listParent.add(m_context.getResources().getStringArray(R.array.BMode));
        }
        if (new HS_Mode().isModeBM()) {
            m_listParent.add(m_context.getResources().getStringArray(R.array.BMode));
            m_listParent.add(m_context.getResources().getStringArray(R.array.BMMode));
        }
        m_listParent.add(m_context.getResources().getStringArray(R.array.other));
    }

    @Override
    public int getCount() {
        return m_listParent.size();
    }

    @Override
    public Object getItem(int position) {
        return m_listParent.get(position)[0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(m_context, R.layout.follow_view_choose_item_tv, null);
            holder.m_tvValue = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.getCurrentPosition() == position) {
            holder.m_tvValue.setBackgroundResource(R.color.White);
        } else {
            holder.m_tvValue.setBackgroundResource(R.color.Background);
        }
        holder.m_tvValue.setText(getItem(position).toString());
        return convertView;
    }

    public String[] getParentArray() {
        return m_listParent.isEmpty() ? null : m_listParent.get(this.getCurrentPosition());
    }

    public void setCurrentPosition(int position) {
        if (new HS_Mode().isModeB()) {
            m_nPosition_BMode = position;
        } else {
            m_nPosition_BMMode = position;
        }
    }

    public int getCurrentPosition() {
        if (new HS_Mode().isModeB()) {
            return m_nPosition_BMode;
        } else {
            return m_nPosition_BMMode;
        }
    }

    private class ViewHolder {
        TextView m_tvValue;
    }
}
