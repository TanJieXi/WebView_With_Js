package api.hard;

/*! @brief 声功率控制
*/
public class HS_Power {

    /*! @brief  获取声功率可控制个数
     *  @return 声功率可控制个数
    */
	public native int getSize();

    /*! @brief  获取声功率对应的索引值
     *  @param  value : 声功率(%)
     *  @return 索引值
    */
	public native int getIndex(int value);

    /*! @brief  获取当前声功率对应的索引值
     *  @return 索引值
    */
	public native int getIndex();

    /*! @brief  获取声功率索引对应的声功率值
     *  @param  index : 索引值
     *  @return 声功率值(%)
    */
	public native int getValue(int index);

    /*! @brief  获取当前声功率值
     *  @return 声功率值(%)
    */
	public native int getValue();

    /*! @brief  设置声功率索引值
     *  @param  add  : HsTrue = 声功率加, HsFalse = 声功率减
     *  @param  loop : HsTrue = 循环加减,   HsFalse = 不循环
     *  @return 是否设置成功
    */
	public native boolean setLoop(boolean add, boolean loop);

    /*! @brief  设置声功率值
     *  @param  value : 声功率值(%)
     *  @return 是否设置成功
    */
    public native boolean setValue(int value);

    /*! @brief  设置声功率索引值
     *  @param  index : 索引值
     *  @return 是否设置成功
    */
    public native boolean setIndex(int index);
}
