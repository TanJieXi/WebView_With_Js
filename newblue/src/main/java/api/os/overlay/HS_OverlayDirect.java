package api.os.overlay;

import android.graphics.Bitmap;

import api.type.HS_Size;

public class HS_OverlayDirect {
	
	public class Status {
		public static final int UnfreezeActive   = 0; 
		public static final int UnfreezeUnactive = 1; 
		public static final int FreezeActive     = 2; 
		public static final int FreezeUnactive   = 3;
	}
	

    /*! @brief  设置探头方向是否绘制在DSC图像附近
     *  @param  nearDsc : 是否绘制在DSC图像附近
     *  @return 是否设置成功
     */
	public native boolean setNearDSC(boolean nearDsc);

    /*! @brief 设置探头方向显示尺寸
     *  @param size : 探头方向显示尺寸
     *  @return null
     */
	public native void setSize(HS_Size size);

    /*! @brief 设置探头方向图像
     *  @param image : 探头方向图像
     *  @return null
     */
	public native void setImage(int status, Bitmap image);

    /*! @brief 设置探头方向图像(可识别图像文件类型=bmp; png; jpg; tif)
     *  @param image : 探头方向图像
     *  @return null
     */
	public native void setImage(int status, String image);
}
