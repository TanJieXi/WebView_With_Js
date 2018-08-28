package api.image;

/*! @brief 总增益控制
*/
public class HS_Gain {

    /*! @brief  获取总增益可控制个数
     *  @return 总增益可控制个数
    */
	public native int getSize();

    /*! @brief  获取总增益对应的索引值
     *  @param  value : 总增益值
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前总增益对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取索引值对应的总增益值
     *  @param  index : 索引值
     *  @return 总增益值
    */
	public native int getValue(int index);

    /*! @brief  获取当前总增益值
     *  @return 总增益值
    */
	public native int getValue();

    /*! @brief  设置总增益
     *  @param  add  : HSTrue = 总增益加, HSFalse = 总增益减
     *  @param  loop : HSTrue = 循环加减, HSFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置总增益值
     *  @param  value : 总增益值
     *  @return 是否设置成功
    */
	public native boolean setValue(int value);

    /*! @brief  设置索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
	public native boolean setIndex(int index);
}
