package api.image;

/*! @brief 锐化控制
*/
public class HS_Sharp {

    /*! @brief  获取锐化可控制个数
     *  @return 锐化可控制个数
    */
	public native int getSize();

    /*! @brief  获取锐化对应的索引值
     *  @param  value : 锐化值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前锐化对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的锐化值
     *  @param  index : 索引值
     *  @return 锐化值
    */
	public native int getValue(int index);

    /*! @brief  获取当前锐化值
     *  @return 锐化值
    */
	public native int getValue();

    /*! @brief  设置锐化
     *  @param  add  : HSTrue = 锐化加,   HSFalse = 锐化减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置锐化值
     *  @param  value : 锐化值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
