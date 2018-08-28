package api.os.overlay;

import api.type.HS_Pen;

public class HS_OverlayMSample {
    /*! @brief  设置是否显示M采样线线条
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  是否显示M采样线线条
     *  @return 是否显示
     */
    public native boolean isShow();

    /*! @brief  设置M采样线画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
    public native void setPen(HS_Pen pen);
}
