package api.os.overlay;

import api.type.HS_Pen;

/*! @brief 覆盖层-PICC线样式
 */
public class HS_OverlayPicc {

    /*! @brief  设置一厘米内显示几个分隔点
     *  @param  count : 分隔点数
     *  @return 是否设置成功
     */
	public native void setShowCount(int count);

    /*! @brief  设置PICC线画笔类型
     *  @param  pen : 画笔类型
     */
	public native void setPen(HS_Pen pen);

    /*! @brief  设置PICC线厘米刻度点半径
     *  @param  pen : 厘米刻度点半径
     */
	public native void setRadiusLarge(float radius);

    /*! @brief  设置PICC线分隔刻度点半径
     *  @param  pen : 分隔刻度点半径
     */
	public native void setRadiusSmall(float radius);
}
