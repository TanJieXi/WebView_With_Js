package api.os.overlay;

import api.type.HS_Pen;

public class HS_OverlayGray {

    /*! @brief  设置灰阶曲线范围宽度
     *  @param  width : 灰阶曲线范围宽度
     *  @return 是否设置成功
     */
	public native boolean setWidth(int width);

    /*! @brief  设置灰阶曲线画笔类型
     *  @param  pen : 灰阶曲线画笔类型
     *  @return null
     */
	public native void setPen(HS_Pen pen);
}
