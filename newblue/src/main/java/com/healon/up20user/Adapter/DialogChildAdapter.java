package com.healon.up20user.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newblue.R;

/**
 * Created by Pretend on 2017/7/12 0012.
 */

public class DialogChildAdapter extends BaseAdapter {
    private String[] m_arrayChild;
    private Context m_context;

    public DialogChildAdapter(Context context, String[] arrayChild) {
        m_context = context;
        m_arrayChild = arrayChild;
    }

    @Override
    public int getCount() {
        return (m_arrayChild.length - 1);
    }

    @Override
    public Object getItem(int position) {
        return this.getText(position);
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
        holder.m_tvValue.setText(getItem(position).toString());
        return convertView;
    }

    class ViewHolder {
        TextView m_tvValue;
    }

    public String getText(int position) {
        String[] data = m_arrayChild[position + 1].split(";");
        return data[1];
    }

    public String getClassName(int position) {
        String[] data = m_arrayChild[position + 1].split(";");
        return data[2];
    }

    public String getIdCode(int position) {
        String[] data = m_arrayChild[position + 1].split(";");
        return data[0];
    }

    public void setChildData(String[] data) {
        m_arrayChild = data;
    }
}
