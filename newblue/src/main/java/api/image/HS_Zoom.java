package api.image;

import api.type.HS_Point;

/*! @brief 缩放
*/
public class HS_Zoom {

	// 缩放模式
	public class Mode {
		public static final int LOCAL = 0;
		public static final int FULL  = 1;
	}
	
    /*! @brief  设置是否打开缩放功能
     *  @param  open : 是否打开
     *  @return 是否设置成功
     */
	public native boolean setOpen(boolean open);

    /*! @brief  获取缩放功能是否打开
     *  @return 是否打开
     */
    public native boolean isOpened();

    /*! @brief  设置缩放模式
     *  @param  mode : 缩放模式
     *  @return 是否设置成功
     */
    public native boolean setMode(int mode);

    /*! @brief  获取缩放模式
     *  @return 缩放模式
     */
    public native int getMode();


    /*! @brief  设置是否显示全屏小地图
     *  @param  show : 是否显示
     *  @return 是否设置成功
     */
    public native boolean setShowMap(boolean show);

    /*! @brief  获取全屏小地图是否显示
     *  @return 是否显示
     */
    public native boolean isShowMap();

    /*! @brief  设置全屏小地图显示位置
     *  @param  rb = HSTrue,  右下角
     *  @param  rb = HSFalse, 右上角
     *  @return 是否设置成功
     */
    public native boolean setMapRB(boolean rb);

    /*! @brief  获取全屏小地图显示位置
     *  @retval val = HSTrue,  右下角
     *  @retval val = HSFlase, 右上角
     */
    public native boolean isMapRB();


    /*! @brief  获取放大倍率可控制个数
     *  @return 放大倍率可控制个数
    */
    public native int getSize();

    /*! @brief  获取放大倍率对应的索引值
     *  @param  value : 放大倍率值
     *  @return 索引值
    */
    public native int getIndex(double value);

    /*! @brief  获取当前放大倍率对应的索引值
     *  @return 索引值
    */
    public native int getIndex();

    /*! @brief  获取索引值对应的放大倍率值
     *  @param  index : 索引值
     *  @return 放大倍率值
    */
    public native double getValue(int index);

    /*! @brief  获取当前放大倍率值
     *  @return 放大倍率值
    */
    public native double getValue();

    /*! @brief  设置放大倍率
     *  @param  add  : HSTrue = 放大倍率加, HSFalse = 放大倍率减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置放大倍率值
     *  @param  value : 放大倍率值
     *  @return 是否设置成功
    */
    public native boolean setValue(double value);

    /*! @brief  设置放大倍率索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);


    /*! @brief  设置放大后的移动位置
     *  @param  point : 放大后的移动位置(绝对位置)
     *  @return 是否设置成功
    */
    public native boolean setPoint(HS_Point point);

    /*! @brief  移动放大后的移动位置
     *  @param  point : 放大后的移动位置(相对移动量)
     *  @return 是否设置成功
    */
    public native boolean setMovePoint(HS_Point point);

    /*! @brief  获取放大后的移动位置
     *  @return 放大后的移动位置
    */
    public native HS_Point getPoint();
}
