package com.example.newblue.event;

/**
 * Created by TanJieXi on 2018/8/7.
 */
public class FirstTextEvent {
    private String mMsg;
    private String tag;
    private int count;

    /**
     *
     * @param msg 消息内容
     * @param tag 标记
     * @param count 数字
     */
    public FirstTextEvent(String msg, String tag,int count) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
        this.tag = tag;
        this.count=count;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
