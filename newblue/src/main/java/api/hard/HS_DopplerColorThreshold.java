package api.hard;

/*! @brief 多普勒控制-颜色阈值
*/
public class HS_DopplerColorThreshold {

    /*! @brief  获取颜色阈值可控制个数
     *  @return 颜色阈值可控制个数
    */
	public native int getSize();

    /*! @brief  获取颜色阈值对应的索引值
     *  @param  value : 颜色阈值值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前颜色阈值对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的颜色阈值值
     *  @param  index : 索引值
     *  @return 颜色阈值值
    */
	public native int getValue(int index);

    /*! @brief  获取当前颜色阈值值
     *  @return 颜色阈值值
    */
	public native int getValue();

    /*! @brief  设置颜色阈值
     *  @param  add  : HSTrue = 颜色阈值加, HSFalse = 颜色阈值减
     *  @param  loop : HSTrue = 循环加减,   HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置颜色阈值值
     *  @param  value : 颜色阈值值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
