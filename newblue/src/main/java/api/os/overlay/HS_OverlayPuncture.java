package api.os.overlay;

import api.type.HS_Font;
import api.type.HS_Pen;

public class HS_OverlayPuncture {
    /*! @brief  设置刻度字体类型
     *  @param  pen : 字体类型
     */
	public native void setFont(HS_Font font);

    /*! @brief  设置穿刺线画笔类型
     *  @param  pen : 画笔类型
     */
    public native void setPen(HS_Pen pen);
}
