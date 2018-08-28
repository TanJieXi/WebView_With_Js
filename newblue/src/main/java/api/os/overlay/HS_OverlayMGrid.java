package api.os.overlay;

import api.type.HS_Pen;

public class HS_OverlayMGrid {

    /*! @brief  设置是否显示M网格线
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  设置M网格线画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
	public native void setPen(HS_Pen pen);
}
