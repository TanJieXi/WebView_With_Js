package com.healon.up20user.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.newblue.R;
import com.healon.up20user.Dialog.PopWindowSetImage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Pretend on 2017/10/10 0010.
 */

public class PopSetImageAdapter extends BaseAdapter {

    private List<PopWindowSetImage.DataBean> m_listData;
    private CallBack callBack;



    public PopSetImageAdapter(List<PopWindowSetImage.DataBean> list,CallBack callBack) {
        this.m_listData = list;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return this.m_listData.size() != 0 ? this.m_listData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return m_listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = m_listData.get(position).type;
        ViewHolder holder = null;
        ViewHolderSeekbar holderSeekbar = null;
        if (null == convertView) {
            switch (type) {
                case PopWindowSetImage.TYPE_BUTTON:
                    holder = new ViewHolder();
                    convertView = View.inflate(parent.getContext(), R.layout.view_set_image_item, null);
                    holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                    holder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
                    holder.ivredues = (ImageView) convertView.findViewById(R.id.iv_redues);
                    convertView.setTag(holder);
                    break;
                case PopWindowSetImage.TYPE_SEEKBAR:
                    holderSeekbar = new ViewHolderSeekbar();
                    convertView = View.inflate(parent.getContext(), R.layout.follow_view_set_image_item_seekbar, null);
                    holderSeekbar.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    holderSeekbar.skbChangeValue = (SeekBar) convertView.findViewById(R.id.skb_changeValue);
                    convertView.setTag(holderSeekbar);
                    break;
            }

        } else {
            switch (type) {
                case PopWindowSetImage.TYPE_BUTTON:
                    if (convertView.getTag() instanceof ViewHolder) {
                        holder = (ViewHolder) convertView.getTag();
                    } else {
                        holder = new ViewHolder();
                        convertView = View.inflate(parent.getContext(), R.layout.view_set_image_item, null);
                        holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                        holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                        holder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
                        holder.ivredues = (ImageView) convertView.findViewById(R.id.iv_redues);
                        convertView.setTag(holder);
                    }
                    break;
                case PopWindowSetImage.TYPE_SEEKBAR:
                    if (convertView.getTag() instanceof ViewHolderSeekbar) {
                        holderSeekbar = (ViewHolderSeekbar) convertView.getTag();
                    } else {
                        holderSeekbar = new ViewHolderSeekbar();
                        convertView = View.inflate(parent.getContext(), R.layout.follow_view_set_image_item_seekbar, null);
                        holderSeekbar.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                        holderSeekbar.skbChangeValue = (SeekBar) convertView.findViewById(R.id.skb_changeValue);
                        convertView.setTag(holderSeekbar);
                    }
                    break;
            }
        }

        switch (type) {
            case PopWindowSetImage.TYPE_BUTTON:
                holder.tvName.setText(m_listData.get(position).name);
                final Object obj = m_listData.get(position).obj;
                Class c = obj.getClass();
                Method value = null;
                Method set = null;
                try {
                    value = c.getMethod("value", new Class[0]);
                    set = c.getMethod("set", new Class[]{boolean.class, boolean.class});
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                final Method mSet = set;
                try {
                    holder.tvValue.setText(String.valueOf(value.invoke(obj)));
                    holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (callBack != null) {
                                    mSet.invoke(obj, new Object[]{new Boolean(true), new Boolean(true)});
                                    callBack.callBack();
                                }

                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    holder.ivredues.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (callBack != null) {
                                    mSet.invoke(obj, new Object[]{new Boolean(false), new Boolean(true)});
                                    callBack.callBack();
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case PopWindowSetImage.TYPE_SEEKBAR:
                holderSeekbar.tvName.setText(m_listData.get(position).name);
                final Object o = m_listData.get(position).obj;
                final Class cl = o.getClass();
                Method index = null;
                Method setIndex = null;
                Method curentValue = null;
                try {
                    index = cl.getMethod("index", new Class[0]);
                    setIndex = cl.getMethod("set", int.class);
                    curentValue = cl.getMethod("value", new Class[0]);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                final Method setIn = setIndex;
                try {
                    holderSeekbar.skbChangeValue.setMax(Integer.valueOf(index.invoke(o).toString()) - 1);
                    holderSeekbar.skbChangeValue.setProgress(Integer.valueOf(curentValue.invoke(o).toString()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                holderSeekbar.skbChangeValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int currentProgress;
                        if (!m_listData.get(position).name.contains("增益")) {
                            currentProgress = seekBar.getMax() - progress;
                        } else {
                            currentProgress = progress;
                        }
                        if (fromUser) {
                            try {
                                setIn.invoke(o, currentProgress);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        if (!m_listData.get(position).name.contains("增益")) {
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (!m_listData.get(position).name.contains("增益")) {
                        }
                    }
                });
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvValue;
        ImageView ivAdd;
        ImageView ivredues;
    }

    static class ViewHolderSeekbar {
        TextView tvName;
        SeekBar skbChangeValue;
    }

    public interface CallBack {
        void callBack();
    }
}
