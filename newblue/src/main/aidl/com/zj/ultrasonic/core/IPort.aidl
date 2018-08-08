package com.zj.ultrasonic.core;

import java.util.List;


/**
 * 主界面回调消息接口
 */
oneway interface IPort{
    /**
     * 模式改变,通知需要更新信息
     */
	void NotifyGetCommonInfo();
	/**
     * 模式改变,通知需要更新快捷工具栏
     */
	void NotifyGetShortCutInfo();
	/**
     * 模式改变,通知需要更新菜单栏
     */
	void NotifyGetMenuInfo();
	/**
	 * 模式改变,通知新模式下,刻度尺信息
	 */
	void NotifyGetScaleInterval(int X,int Y);
	/**
     * 模式改变,通知新模式下,Y轴游标变化信息
     */
    void NotifyGetYCursor(int Count);
}
