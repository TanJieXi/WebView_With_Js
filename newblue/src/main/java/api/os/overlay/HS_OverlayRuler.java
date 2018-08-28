package api.os.overlay;

import api.type.HS_Font;
import api.type.HS_Pen;

public class HS_OverlayRuler {
	
	/*! @brief 标尺刻度形状  */
	public class Sharp {
		public static final int LINE          = 0;	//<! 线
		public static final int BLOCK         = 1;	//<! 方块
		public static final int CIRCLE        = 2;	//<! 实心圆
	}
	
	/*! @brief 深度显示方式  */
	public class Depth {
		public static final int SHOW_NONE     = 0;	//<! 不显示深度
		public static final int SHOW_CURRENT  = 1;	//<! 普通方式
		public static final int SHOW_5CM      = 2;	//<! 每隔5cm显示深度
	}
	

    /*! @brief  设置是否显示横向刻度尺线条
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setHorzLine(boolean show);

    /*! @brief  设置横向标尺形状
     *  @param  pen : 形状
     *  @return 是否设置成功
     */
    public native boolean setHorzShape(int sharp);

    /*! @brief  设置横向标尺刻度长度
     *  @param  lenLong  : 长刻度长度
     *  @param  lenShort : 短刻度长度
     *  @return 是否设置成功
     */
    public native boolean setHorzLength(int lenLong, int lenShort);

    /*! @brief  设置横向标尺画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
    public native void setHorzPen(HS_Pen pen);

    /*! @brief  设置横向标尺字体类型
     *  @param  pen : 字体类型
     *  @return null
     */
    public native void setHorzFont(HS_Font font);

    /*! @brief  设置是否显示横向刻度尺线条
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
    public native boolean setVertLine(boolean show);

    /*! @brief  设置纵向标尺形状
     *  @param  pen : 形状
     *  @return 是否设置成功
     */
    public native boolean setVertShape(int sharp);

    /*! @brief  设置纵向标尺刻度长度
     *  @param  lenLong  : 长刻度长度
     *  @param  lenShort : 短刻度长度
     *  @return 是否设置成功
     */
    public native boolean setVertLength(int lenLong, int lenShort);

    /*! @brief  设置纵向标尺画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
    public native void setVertPen(HS_Pen pen);

    /*! @brief  设置纵向标尺字体类型
     *  @param  pen : 字体类型
     *  @return null
     */
    public native void setVertFont(HS_Font font);

    /*! @brief  设置纵向标尺深度显示方式
     *  @param  show : 显示方式
     *  @return 是否设置成功
     */
    public native boolean setVertDepth(int depth);

    /*! @brief  设置纵向标尺是否绘制在DSC图像附近
     *  @param  nearDsc : 是否绘制在DSC图像附近
     *  @return 是否设置成功
     */
    public native boolean setVertNearDSC(boolean nearDsc);
}
