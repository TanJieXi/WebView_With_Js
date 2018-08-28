package api.hard;

/*! @brief 频率控制
*/
public class HS_Freq {

    /*! @brief  获取频率可控制个数
     *  @return 频率可控制个数
	*/
	public native int getSize();

    /*! @brief  获取频率值对应的索引值
     *  @param  value : 频率值(MHz)
     *  @return 索引值
	*/
	public native int getIndex(double value);

    /*! @brief  获取当前频率对应的索引值
     *  @return 索引值
	*/
	public native int getIndex();

    /*! @brief  获取索引值对应的频率值
     *  @param  index : 索引值
     *  @return 频率值(MHz)
	*/
	public native double getValue(int index);

    /*! @brief  获取当前频率值
     *  @return 频率值(MHz)
	*/
	public native double getValue();

    /*! @brief  设置频率
     *  @param  add  : HsTrue = 频率加,   HsFalse = 频率减
     *  @param  loop : HsTrue = 循环加减, HsFalse = 不循环
     *  @return 是否设置成功
	*/
    public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置频率值
     *  @param  value : 频率值(MHz)
     *  @return 是否设置成功
	*/
    public native boolean setValue(double value);

    /*! @brief  设置频率索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
	*/
    public native boolean setIndex(int index);
}
