package api.os;

import api.type.HS_PointF;

public class HS_MouseAction {
	public class ActionMask {
		public static final int MASK_NONE     = 0x0000; 	//<! 无动作
		public static final int MASK_MEASURE  = 0x0001; 	//<! 测量
		public static final int MASK_MSAMPLE  = 0x0002; 	//<! M采样线
		public static final int MASK_ZOOM     = 0x0004; 	//<! 缩放
		public static final int MASK_CFM      = 0x0008; 	//<! CFM取景框
		public static final int MASK_DEPTH    = 0x0010; 	//<! 深度
	}
	

    /*! @brief  获取是否可以操作给定的掩码的操作功能
     *  @param  mask : 鼠标控制操作掩码
     *  @return 是否可以操作mask功能
     */
    public native boolean isMask(int mask);

    /*! @brief  设置鼠标控制操作掩码
     *  @param  mask : 鼠标控制操作掩码
     */
    public native void setMask(int mask);

    /*! @brief  鼠标左键按下
     *  @param  leftButton : 是否为鼠标左键
     *  @param  point      : 坐标点
     *  @return 操作成功的功能掩码
     */
    public native int setMousePress(boolean leftButton, HS_PointF point);

    /*! @brief  鼠标左键移动
     *  @param  leftButton : 鼠标左键是否按下
     *  @param  point : 坐标点
     *  @return 操作成功的功能掩码
     */
    public native int setMouseMove(boolean leftPress, HS_PointF point);

    /*! @brief  鼠标左键弹起
     *  @param  leftButton : 是否为鼠标左键
     *  @param  point      : 坐标点
     *  @return 操作成功的功能掩码
     */
    public native int setMouseRelease(boolean leftButton, HS_PointF point);
}
