package api.os.overlay;

import api.type.HS_Pen;

public class HS_OverlayZoom {

    /*! @brief  设置采集框画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
	public native void setPenOrg(HS_Pen pen);

    /*! @brief  设置缩放框画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
	public native void setPenDst(HS_Pen pen);

    /*! @brief  设置局部缩放框目标框宽度
     *  @param  width : 宽度
     */
	public native void setWidthLocal(int width);

    /*! @brief  设置全屏缩放小地图宽度
     *  @param  width : 宽度
     */
	public native void setWidthMap(int width);

    /*! @brief  设置缩放移动误差
     *  @param  error : 误差
     */
	public native void setMoveError(int error);

    /*! @brief  获取局部缩放框目标框宽度
     *  @return 宽度
     */
	public native int getWidthLocal();

    /*! @brief  获取全屏缩放小地图宽度
     *  @return 宽度
     */
    public native int getWidthMap();

    /*! @brief  获取缩放移动误差
     *  @return 误差
     */
    public native int getMoveError();
}
