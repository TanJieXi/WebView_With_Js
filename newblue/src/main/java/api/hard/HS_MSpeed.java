package api.hard;

/*! @brief M超速度控制
*/
public class HS_MSpeed {

    /*! @brief  获取M超速度可控制个数
     *  @return M超速度可控制个数
    */
	public native int getSize();

    /*! @brief  获取M超速度对应的索引值
     *  @param  value : M超速度值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前M超速度对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的M超速度值
     *  @param  index : 索引值
     *  @return M超速度值
    */
	public native int getValue(int index);

    /*! @brief  获取当前M超速度值
     *  @return M超速度值
    */
	public native int getValue();

    /*! @brief  设置M超速度
     *  @param  add  : HsTrue = 位置加,   HsFalse = 位置减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置M超速度值
     *  @param  value : M超速度值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置M超速度索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
