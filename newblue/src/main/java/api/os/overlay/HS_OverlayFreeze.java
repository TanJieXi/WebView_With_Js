package api.os.overlay;

import android.graphics.Bitmap;

public class HS_OverlayFreeze {

    /*! @brief 设置冻结标识图像
     *  @param image : 冻结标识图像
     *  @return null
     */
	public native void setImage(Bitmap image);

    /*! @brief 设置冻结标识图像(可识别图像文件类型=bmp; png; jpg; tif)
     *  @param image : 冻结标识图像
     *  @return null
     */
    public native void setImage(String image);
}
