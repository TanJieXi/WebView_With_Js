package api.os.overlay;

import api.type.HS_Pen;

/*! @brief 覆盖层-彩超样式
 */
public class HS_OverlayDoppler {

    /*! @brief  设置是否显示彩超取景框
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
	public native boolean setShow(boolean show);

    /*! @brief  是否显示彩超取景框
     *  @return 是否显示
     */
	public native boolean isShow();

    /*! @brief  设置取景框画笔类型
     *  @param  pen : 画笔类型
     *  @return null
     */
	public native void setPen(HS_Pen pen);

    /*! @brief  设置取景框改变尺寸时的选取半径
     *  @param  radius : 选取半径
     *  @return 是否成功
     */
    public native boolean setSampleBoxRadius(double radius);

    /*! @brief  获取取景框改变尺寸时的选取半径
     *  @return 选取半径
     */
    public native double getSampleBoxRadius();
}
