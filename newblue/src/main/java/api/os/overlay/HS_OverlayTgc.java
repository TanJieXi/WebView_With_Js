package api.os.overlay;

import api.type.HS_Pen;

public class HS_OverlayTgc {
	
	/*! @brief 线样式  */
	public class Type {
		public static final int CURVE = 0;	//<! 平滑曲线
		public static final int POLY  = 1;	//<! 折线
	}
	

    /*! @brief  设置TGC曲线类型
     *  @param  type : CURVE = 平滑曲线, POLY = 折线
     *  @return 是否设置成功
     */
	public native boolean setType(int type);

    /*! @brief  获取TGC曲线类型
     *  @return TGC曲线类型
     */
	public native int getType();

    /*! @brief  设置TGC曲线显示范围宽度
     *  @param  width : 显示范围宽度
     *  @return 是否设置成功
     */
    public native boolean setWidth(int width);

    /*! @brief  获取TGC曲线显示范围宽度
     *  @return 显示范围宽度
     */
    public native int getWidth();

    /*! @brief  设置画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
    public native void setPen(HS_Pen pen);
}
