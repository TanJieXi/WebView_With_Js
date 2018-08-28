package api.os.overlay;
import api.type.HS_Pen;

public class HS_OverlayFocus {

    /*! @brief  设置焦点画笔类型
     *  @param  pen : 焦点画笔类型
     *  @return null
     */
	public native void setPen(HS_Pen pen);

    /*! @brief  设置焦点三角形尺寸
     *  @param  size : 焦点三角形尺寸
     *  @return 是否设置成功
     */
	public native boolean setSize(int size);
}
